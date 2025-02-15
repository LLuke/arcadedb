/*
 * Copyright © 2021-present Arcade Data Ltd (info@arcadedata.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-FileCopyrightText: 2021-present Arcade Data Ltd (info@arcadedata.com)
 * SPDX-License-Identifier: Apache-2.0
 */
package com.arcadedb.remote;

import com.arcadedb.ContextConfiguration;
import com.arcadedb.exception.DatabaseOperationException;
import com.arcadedb.serializer.json.JSONObject;

import java.net.*;
import java.util.*;

/**
 * Remote Database implementation. It's not thread safe. For multi-thread usage create one instance of RemoteDatabase per thread.
 *
 * @author Luca Garulli (l.garulli@arcadedata.com)
 */
public class RemoteServer extends RemoteHttpComponent {
  public RemoteServer(final String server, final int port, final String userName, final String userPassword) {
    this(server, port, userName, userPassword, new ContextConfiguration());
  }

  public RemoteServer(final String server, final int port, final String userName, final String userPassword,
      final ContextConfiguration configuration) {
    super(server, port, userName, userPassword, configuration);
  }

  public void create(final String databaseName) {
    serverCommand("POST", "create database " + databaseName, true, true, null);
  }

  public List<String> databases() {
    return (List<String>) serverCommand("POST", "list databases", true, true,
        (connection, response) -> response.getJSONArray("result").toList());
  }

  public boolean exists(final String databaseName) {
    return (boolean) httpCommand("GET", databaseName, "exists", "SQL", null, null, false, true,
        (connection, response) -> response.getBoolean("result"));
  }

  public void drop(final String databaseName) {
    try {
      final HttpURLConnection connection = createConnection("POST", getUrl("server"));
      setRequestPayload(connection, new JSONObject().put("command", "drop database " + databaseName));
      connection.connect();
      if (connection.getResponseCode() != 200) {
        final Exception detail = manageException(connection, "drop database");
        throw new RemoteException("Error on deleting database: " + connection.getResponseMessage(), detail);
      }

    } catch (final Exception e) {
      throw new DatabaseOperationException("Error on deleting database", e);
    }
  }

  @Override
  public String toString() {
    return protocol + "://" + currentServer + ":" + currentPort;
  }

  public void createUser(final String userName, final String password, final List<String> databases) {
    try {
      final HttpURLConnection connection = createConnection("POST", getUrl("server"));

      final JSONObject jsonUser = new JSONObject();
      jsonUser.put("name", userName);
      jsonUser.put("password", password);
      if (databases != null && !databases.isEmpty()) {
        final JSONObject databasesJson = new JSONObject();
        for (final String dbName : databases)
          databasesJson.put(dbName, new String[] { "admin" });
        jsonUser.put("databases", databasesJson);
      }

      setRequestPayload(connection, new JSONObject().put("command", "create user " + jsonUser));

      connection.connect();
      if (connection.getResponseCode() != 200) {
        final Exception detail = manageException(connection, "create user");
        throw new SecurityException("Error on creating user: " + connection.getResponseMessage(), detail);
      }

    } catch (final Exception e) {
      throw new DatabaseOperationException("Error on creating user", e);
    }
  }

  public void dropUser(final String userName) {
    try {
      final HttpURLConnection connection = createConnection("POST", getUrl("server"));
      setRequestPayload(connection, new JSONObject().put("command", "drop user " + userName));
      connection.connect();
      if (connection.getResponseCode() != 200) {
        final Exception detail = manageException(connection, "drop user");
        throw new RemoteException("Error on deleting user: " + connection.getResponseMessage(), detail);
      }

    } catch (final Exception e) {
      throw new RemoteException("Error on deleting user", e);
    }
  }

  private Object serverCommand(final String method, final String command, final boolean leaderIsPreferable,
      final boolean autoReconnect, final Callback callback) {
    return httpCommand(method, null, "server", null, command, null, leaderIsPreferable, autoReconnect, callback);
  }
}
