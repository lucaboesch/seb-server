/*
 * Copyright (c) 2020 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.gui.service.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.util.buf.StringUtils;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.api.API;
import ch.ethz.seb.sebserver.gbl.model.exam.SEBProctoringConnectionData;
import ch.ethz.seb.sebserver.gbl.util.Result;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.RestService;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.session.SendJoinRemoteProctoringRoom;
import ch.ethz.seb.sebserver.gui.service.remote.webservice.api.session.SendRejoinExamCollectionRoom;

public class ProctoringGUIService {

    private static final Logger log = LoggerFactory.getLogger(ProctoringGUIService.class);

    public static final String SESSION_ATTR_PROCTORING_DATA = "SESSION_ATTR_PROCTORING_DATA";
    private static final String CLOSE_ROOM_SCRIPT = "var existingWin = window.open('', '%s'); existingWin.close()";

    private final RestService restService;

    private final AtomicInteger counter = new AtomicInteger(1);
    final Map<String, RoomConnectionData> rooms = new HashMap<>();

    final Set<String> openWindows = new HashSet<>();

    public ProctoringGUIService(final RestService restService) {
        this.restService = restService;
    }

    public String createNewRoomName() {
        return "Room-" + this.counter.getAndIncrement();
    }

    public void registerProctoringWindow(final String window) {
        this.openWindows.add(window);
    }

    public Set<String> roomNames() {
        return this.rooms.keySet();
    }

    public String getRoomConnectionTokens(final String roomName) {
        if (this.rooms.containsKey(roomName)) {
            return StringUtils.join(this.rooms.get(roomName).connections, Constants.COMMA);
        } else {
            return null;
        }
    }

    public static ProctoringWindowData getCurrentProctoringWindowData() {
        return (ProctoringWindowData) RWT.getUISession()
                .getHttpSession()
                .getAttribute(SESSION_ATTR_PROCTORING_DATA);
    }

    public static void setCurrentProctoringWindowData(
            final String examId,
            final SEBProctoringConnectionData data) {

        RWT.getUISession().getHttpSession().setAttribute(
                SESSION_ATTR_PROCTORING_DATA,
                new ProctoringWindowData(examId, data));
    }

    public Result<SEBProctoringConnectionData> registerNewSingleProcotringRoom(
            final String examId,
            final String roomName,
            final String subject,
            final String connectionToken) {

        return Result.tryCatch(() -> {
            final SEBProctoringConnectionData connection =
                    this.restService.getBuilder(SendJoinRemoteProctoringRoom.class)
                            .withURIVariable(API.PARAM_MODEL_ID, examId)
                            .withFormParam(SEBProctoringConnectionData.ATTR_ROOM_NAME, roomName)
                            .withFormParam(SEBProctoringConnectionData.ATTR_SUBJECT, subject)
                            .withFormParam(API.EXAM_API_SEB_CONNECTION_TOKEN, connectionToken)
                            .call()
                            .getOrThrow();

            this.rooms.put(roomName, new RoomConnectionData(roomName, examId, connectionToken));
            this.openWindows.add(roomName);
            return connection;
        });
    }

    public Result<SEBProctoringConnectionData> registerNewProcotringRoom(
            final String examId,
            final String roomName,
            final String subject,
            final Collection<String> connectionTokens) {

        return Result.tryCatch(() -> {
            final SEBProctoringConnectionData connection =
                    this.restService.getBuilder(SendJoinRemoteProctoringRoom.class)
                            .withURIVariable(API.PARAM_MODEL_ID, examId)
                            .withFormParam(SEBProctoringConnectionData.ATTR_ROOM_NAME, roomName)
                            .withFormParam(SEBProctoringConnectionData.ATTR_SUBJECT, subject)
                            .withFormParam(
                                    API.EXAM_API_SEB_CONNECTION_TOKEN,
                                    StringUtils.join(connectionTokens, Constants.LIST_SEPARATOR_CHAR))
                            .call()
                            .getOrThrow();

            this.rooms.put(roomName, new RoomConnectionData(roomName, examId, connectionTokens));
            this.openWindows.add(roomName);
            return connection;
        });
    }

    public void addConnectionsToRoom(
            final String examId,
            final String room,
            final Collection<String> connectionTokens) {

        if (this.rooms.containsKey(room)) {
            final RoomConnectionData roomConnectionData = this.rooms.get(room);
            if (!roomConnectionData.examId.equals(examId) || !roomConnectionData.roomName.equals(room)) {
                throw new IllegalArgumentException("Exam identifier mismatch");
            }
            this.restService.getBuilder(SendJoinRemoteProctoringRoom.class)
                    .withURIVariable(API.PARAM_MODEL_ID, examId)
                    .withFormParam(SEBProctoringConnectionData.ATTR_ROOM_NAME, room)
                    .withFormParam(
                            API.EXAM_API_SEB_CONNECTION_TOKEN,
                            StringUtils.join(connectionTokens, Constants.LIST_SEPARATOR_CHAR))
                    .call()
                    .getOrThrow();
            roomConnectionData.connections.addAll(connectionTokens);
        }
    }

    public void removeConnectionsFromRoom(
            final String examId,
            final String room,
            final Collection<String> connectionTokens) {

    }

    public void closeRoom(final String name) {
        closeWindow(name);
        final RoomConnectionData roomConnectionData = this.rooms.remove(name);
        if (roomConnectionData != null) {
            // send instruction to leave this room and join the own exam collection room

            this.restService.getBuilder(SendRejoinExamCollectionRoom.class)
                    .withURIVariable(API.PARAM_MODEL_ID, roomConnectionData.examId)
                    .withFormParam(
                            API.EXAM_API_SEB_CONNECTION_TOKEN,
                            StringUtils.join(roomConnectionData.connections, Constants.LIST_SEPARATOR_CHAR))
                    .call()
                    .getOrThrow();

        }
    }

    public void clear() {

        if (!this.rooms.isEmpty()) {
            this.rooms.keySet().stream().forEach(this::closeRoom);
            this.rooms.clear();
        }

        if (!this.openWindows.isEmpty()) {

            this.openWindows.stream()
                    .forEach(room -> closeWindow(room));
            this.openWindows.clear();
        }

    }

    private void closeWindow(final String room) {
        try {
            RWT.getClient().getService(JavaScriptExecutor.class)
                    .execute(String.format(CLOSE_ROOM_SCRIPT, room));
        } catch (final Exception e) {
            log.info("Failed to close opened proctoring window: {}", room);
        }
    }

    private static final class RoomConnectionData {
        final String roomName;
        final String examId;
        final Collection<String> connections;

        protected RoomConnectionData(
                final String roomName,
                final String examId,
                final String... connections) {

            this.roomName = roomName;
            this.examId = examId;
            this.connections = connections != null ? Arrays.asList(connections) : new ArrayList<>();
        }

        protected RoomConnectionData(
                final String roomName,
                final String examId,
                final Collection<String> connections) {

            this.roomName = roomName;
            this.examId = examId;
            this.connections = connections != null ? new ArrayList<>(connections) : new ArrayList<>();
        }
    }

    public static class ProctoringWindowData {
        public final String examId;
        public final SEBProctoringConnectionData connectionData;

        protected ProctoringWindowData(
                final String examId,
                final SEBProctoringConnectionData connectionData) {
            this.examId = examId;
            this.connectionData = connectionData;
        }
    }

}