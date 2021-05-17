/*
 * Copyright (c) 2020 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice.servicelayer.lms.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.async.AsyncService;
import ch.ethz.seb.sebserver.gbl.async.CircuitBreaker;
import ch.ethz.seb.sebserver.gbl.async.CircuitBreaker.State;
import ch.ethz.seb.sebserver.gbl.model.exam.Chapters;
import ch.ethz.seb.sebserver.gbl.model.exam.QuizData;
import ch.ethz.seb.sebserver.gbl.model.user.ExamineeAccountDetails;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.webservice.servicelayer.dao.FilterMap;

/** A partial course access API implementation that uses CircuitBreaker to apply LMS
 * API requests in a protected environment.
 *
 * Extend this to implement a concrete course access API for a given type of LMS. */
public abstract class AbstractCourseAccess {

    private static final Logger log = LoggerFactory.getLogger(AbstractCourseAccess.class);

    /** Fetch status that indicates an asynchronous quiz data fetch status if the
     * concrete implementation has such. */
    public enum FetchStatus {
        ALL_FETCHED,
        ASYNC_FETCH_RUNNING,
        FETCH_ERROR
    }

    /** CircuitBreaker for protected quiz and course data requests */
    protected final CircuitBreaker<List<QuizData>> quizzesRequest;
    /** CircuitBreaker for protected chapter data requests */
    protected final CircuitBreaker<Chapters> chaptersRequest;
    /** CircuitBreaker for protected examinee account details requests */
    protected final CircuitBreaker<ExamineeAccountDetails> accountDetailRequest;

    protected AbstractCourseAccess(
            final AsyncService asyncService,
            final Environment environment) {

        this.quizzesRequest = asyncService.createCircuitBreaker(
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.quizzesRequest.attempts",
                        Integer.class,
                        3),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.quizzesRequest.blockingTime",
                        Long.class,
                        Constants.MINUTE_IN_MILLIS),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.quizzesRequest.timeToRecover",
                        Long.class,
                        Constants.MINUTE_IN_MILLIS));

        this.chaptersRequest = asyncService.createCircuitBreaker(
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.chaptersRequest.attempts",
                        Integer.class,
                        3),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.chaptersRequest.blockingTime",
                        Long.class,
                        Constants.SECOND_IN_MILLIS * 10),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.chaptersRequest.timeToRecover",
                        Long.class,
                        Constants.MINUTE_IN_MILLIS));

        this.accountDetailRequest = asyncService.createCircuitBreaker(
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.accountDetailRequest.attempts",
                        Integer.class,
                        2),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.accountDetailRequest.blockingTime",
                        Long.class,
                        Constants.SECOND_IN_MILLIS * 10),
                environment.getProperty(
                        "sebserver.webservice.circuitbreaker.accountDetailRequest.timeToRecover",
                        Long.class,
                        Constants.SECOND_IN_MILLIS * 10));
    }

    public Result<List<QuizData>> getQuizzes(final FilterMap filterMap) {
        return this.quizzesRequest.protectedRun(allQuizzesSupplier(filterMap));
    }

    public Result<ExamineeAccountDetails> getExamineeAccountDetails(final String examineeSessionId) {
        return this.accountDetailRequest.protectedRun(accountDetailsSupplier(examineeSessionId));
    }

    /** Default implementation that uses getExamineeAccountDetails to geht the examinee name
     *
     * @param examineeSessionId
     * @return The examinee account name for the given examineeSessionId */
    public String getExamineeName(final String examineeSessionId) {
        return getExamineeAccountDetails(examineeSessionId)
                .map(ExamineeAccountDetails::getDisplayName)
                .onError(error -> log.warn("Failed to request user-name for ID: {}", error.getMessage(), error))
                .getOr(examineeSessionId);
    }

    protected Result<Chapters> getCourseChapters(final String courseId) {
        return this.chaptersRequest.protectedRun(getCourseChaptersSupplier(courseId));
    }

    /** NOTE: this returns a ExamineeAccountDetails with given examineeSessionId for default.
     * Override this if requesting account details is supported for specified LMS access.
     *
     * @param examineeSessionId
     * @return this returns a ExamineeAccountDetails with given examineeSessionId for default */
    protected Supplier<ExamineeAccountDetails> accountDetailsSupplier(final String examineeSessionId) {
        return () -> new ExamineeAccountDetails(
                examineeSessionId,
                examineeSessionId,
                examineeSessionId,
                examineeSessionId,
                Collections.emptyMap());
    }

    /** This abstraction has no cache implementation and therefore this returns a Result
     * with an "No cache supported error.
     * </p>
     * To implement and use caching, this must be overridden and implemented
     *
     * @param id The identifier of the QuizData to get from cache
     * @return Result with an "No cache supported error */
    public Result<QuizData> getQuizFromCache(final String id) {
        return Result.ofRuntimeError("No cache supported");
    }

    /** This abstraction has no cache implementation and therefore this returns a Result
     * with an "No cache supported error.
     * </p>
     * To implement and use caching, this must be overridden and implemented
     *
     * @param ids Collection of quiz data identifier to get from the cache
     * @return Result with an "No cache supported error */
    public Result<Collection<Result<QuizData>>> getQuizzesFromCache(final Set<String> ids) {
        return Result.ofRuntimeError("No cache supported");
    }

    /** Provides a supplier for the quiz data request to use within the circuit breaker */
    protected abstract Supplier<List<QuizData>> quizzesSupplier(final Set<String> ids);

    /** Provides a supplier to supply request to use within the circuit breaker */
    protected abstract Supplier<List<QuizData>> allQuizzesSupplier(final FilterMap filterMap);

    /** Provides a supplier for the course chapter data request to use within the circuit breaker */
    protected abstract Supplier<Chapters> getCourseChaptersSupplier(final String courseId);

    protected FetchStatus getFetchStatus() {
        if (this.quizzesRequest.getState() != State.CLOSED) {
            return FetchStatus.FETCH_ERROR;
        }
        return FetchStatus.ALL_FETCHED;
    }
}