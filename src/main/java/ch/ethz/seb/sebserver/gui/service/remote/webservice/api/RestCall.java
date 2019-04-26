/*
 * Copyright (c) 2019 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.gui.service.remote.webservice.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import ch.ethz.seb.sebserver.gbl.api.APIMessage;
import ch.ethz.seb.sebserver.gbl.api.EntityType;
import ch.ethz.seb.sebserver.gbl.api.JSONMapper;
import ch.ethz.seb.sebserver.gbl.model.Entity;
import ch.ethz.seb.sebserver.gbl.model.Page;
import ch.ethz.seb.sebserver.gbl.model.PageSortOrder;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.gbl.util.Utils;

public abstract class RestCall<T> {

    private static final Logger log = LoggerFactory.getLogger(RestCall.class);

    public enum CallType {
        UNDEFINED,
        GET_SINGLE,
        GET_PAGE,
        GET_NAMES,
        GET_DEPENDENCIES,
        GET_LIST,
        NEW,
        SAVE,
        DELETE,
        ACTIVATION_ACTIVATE,
        ACTIVATION_DEACTIVATE
    }

    protected RestService restService;
    protected JSONMapper jsonMapper;
    protected TypeKey<T> typeKey;
    protected final HttpMethod httpMethod;
    protected final MediaType contentType;
    protected final String path;

    protected RestCall(
            final TypeKey<T> typeKey,
            final HttpMethod httpMethod,
            final MediaType contentType,
            final String path) {

        this.typeKey = typeKey;
        this.httpMethod = httpMethod;
        this.contentType = contentType;
        this.path = path;

    }

    protected RestCall<T> init(final RestService restService, final JSONMapper jsonMapper) {
        this.restService = restService;
        this.jsonMapper = jsonMapper;
        return this;
    }

    protected Result<T> exchange(final RestCallBuilder builder) {

        log.debug("Call webservice API on {} for {}", this.path, builder);

        try {
            final ResponseEntity<String> responseEntity = this.restService
                    .getWebserviceAPIRestTemplate()
                    .exchange(
                            builder.buildURI(),
                            this.httpMethod,
                            builder.buildRequestEntity(),
                            String.class,
                            builder.uriVariables);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                return Result.of(RestCall.this.jsonMapper.readValue(
                        responseEntity.getBody(),
                        RestCall.this.typeKey.typeRef));

            } else {
                return handleRestCallError(responseEntity);
            }

        } catch (final Throwable t) {
            final RestCallError restCallError = new RestCallError("Unexpected error while rest call", t);
            try {

                final String responseBody = ((RestClientResponseException) t).getResponseBodyAsString();

                restCallError.errors.addAll(RestCall.this.jsonMapper.readValue(
                        responseBody,
                        new TypeReference<List<APIMessage>>() {
                        }));

            } catch (final ClassCastException cce) {
                log.error("Unexpected error-response while webservice API call for: {}", builder, cce);
                log.error("Unexpected error-response cause: ", t);
                restCallError.errors.add(APIMessage.ErrorMessage.UNEXPECTED.of(cce));
            } catch (final RuntimeException re) {
                log.error("Unexpected runtime error while webservice API call for: {}", builder, re);
                log.error("Unexpected runtime error cause: ", t);
                restCallError.errors.add(APIMessage.ErrorMessage.UNEXPECTED.of(re));
            } catch (final Exception e) {
                log.error("Unexpected error while webservice API call for: {}", builder, e);
                log.error("Unexpected error cause: ", t);
                restCallError.errors.add(APIMessage.ErrorMessage.UNEXPECTED.of(e));
            }

            return Result.ofError(restCallError);
        }
    }

    public RestCallBuilder newBuilder() {
        return new RestCallBuilder();
    }

    private Result<T> handleRestCallError(final ResponseEntity<String> responseEntity)
            throws IOException, JsonParseException, JsonMappingException {

        final RestCallError restCallError =
                new RestCallError("Response Entity: " + responseEntity.toString());

        restCallError.errors.addAll(RestCall.this.jsonMapper.readValue(
                responseEntity.getBody(),
                new TypeReference<List<APIMessage>>() {
                }));

        log.debug(
                "Webservice answered with well defined error- or validation-failure-response: ",
                restCallError);

        return Result.ofError(restCallError);
    }

    public class RestCallBuilder {

        private final HttpHeaders httpHeaders;
        private String body = null;
        private final MultiValueMap<String, String> queryParams;
        private final Map<String, String> uriVariables;

        protected RestCallBuilder() {
            this.httpHeaders = new HttpHeaders();
            this.queryParams = new LinkedMultiValueMap<>();
            this.uriVariables = new HashMap<>();
            this.httpHeaders.set(
                    HttpHeaders.CONTENT_TYPE,
                    RestCall.this.contentType.toString());
        }

        public RestCallBuilder(final RestCall<?>.RestCallBuilder builder) {
            this.httpHeaders = builder.httpHeaders;
            this.body = builder.body;
            this.queryParams = new LinkedMultiValueMap<>(builder.queryParams);
            this.uriVariables = new HashMap<>(builder.uriVariables);
        }

        public RestCallBuilder withHeaders(final HttpHeaders headers) {
            this.httpHeaders.addAll(headers);
            return this;
        }

        public RestCallBuilder withHeader(final String name, final String value) {
            this.httpHeaders.set(name, value);
            return this;
        }

        public RestCallBuilder withHeaders(final MultiValueMap<String, String> params) {
            this.httpHeaders.addAll(params);
            return this;
        }

        public RestCallBuilder apply(final Function<RestCallBuilder, RestCallBuilder> f) {
            return f.apply(this);
        }

        public RestCallBuilder withBody(final Object body) {
            if (body instanceof String) {
                this.body = String.valueOf(body);
                return this;
            }

            try {
                this.body = RestCall.this.jsonMapper.writeValueAsString(body);
            } catch (final JsonProcessingException e) {
                log.error("Error while trying to parse body json object: " + body);
            }

            return this;
        }

        public RestCallBuilder withURIVariable(final String name, final String value) {
            this.uriVariables.put(name, value);
            return this;
        }

        public RestCallBuilder withQueryParam(final String name, final String value) {
            this.queryParams.put(name, Arrays.asList(value));
            return this;
        }

        public RestCallBuilder withQueryParams(final MultiValueMap<String, String> params) {
            if (params != null) {
                this.queryParams.putAll(params);
            }
            return this;
        }

        public RestCallBuilder withPaging(final int pageNumber, final int pageSize) {
            this.queryParams.put(Page.ATTR_PAGE_NUMBER, Arrays.asList(String.valueOf(pageNumber)));
            this.queryParams.put(Page.ATTR_PAGE_SIZE, Arrays.asList(String.valueOf(pageSize)));
            return this;
        }

        public RestCallBuilder withSorting(final String column, final PageSortOrder order) {
            if (column != null) {
                this.queryParams.put(Page.ATTR_SORT, Arrays.asList(order.encode(column)));
            }
            return this;
        }

        public RestCallBuilder withFormBinding(final FormBinding formBinding) {
            if (RestCall.this.httpMethod == HttpMethod.PUT) {
                return withBody(formBinding.getFormAsJson());
            } else {
                this.body = formBinding.getFormUrlEncoded();
                return this;
            }
        }

        public RestCallBuilder onlyActive(final boolean active) {
            this.queryParams.put(Entity.FILTER_ATTR_ACTIVE, Arrays.asList(String.valueOf(active)));
            return this;
        }

        public final Result<T> call() {
            return RestCall.this.exchange(this);
        }

        public String buildURI() {
            return RestCall.this.restService.getWebserviceURIBuilder()
                    .path(RestCall.this.path)
                    .queryParams(this.queryParams)
                    .toUriString();
        }

        public HttpEntity<?> buildRequestEntity() {
            if (this.body != null) {
                return new HttpEntity<>(this.body, this.httpHeaders);
            } else {
                return new HttpEntity<>(this.httpHeaders);
            }
        }

        public Map<String, String> getURIVariables() {
            return Utils.immutableMapOf(this.uriVariables);
        }

        @Override
        public String toString() {
            return "RestCallBuilder [httpHeaders=" + this.httpHeaders + ", body=" + this.body + ", queryParams="
                    + this.queryParams
                    + ", uriVariables=" + this.uriVariables + "]";
        }

    }

    public static final class TypeKey<T> {
        final CallType callType;
        final EntityType entityType;
        private final TypeReference<T> typeRef;

        public TypeKey(
                final CallType callType,
                final EntityType entityType,
                final TypeReference<T> typeRef) {

            this.callType = callType;
            this.entityType = entityType;
            this.typeRef = typeRef;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.callType == null) ? 0 : this.callType.hashCode());
            result = prime * result + ((this.entityType == null) ? 0 : this.entityType.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final TypeKey<?> other = (TypeKey<?>) obj;
            if (this.callType != other.callType)
                return false;
            if (this.entityType != other.entityType)
                return false;
            return true;
        }
    }

}
