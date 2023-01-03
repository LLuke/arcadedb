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
/* Generated By:JJTree: Do not edit this line. ODropClassStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.graph.Edge;
import com.arcadedb.graph.Vertex;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InternalResultSet;
import com.arcadedb.query.sql.executor.ResultInternal;
import com.arcadedb.query.sql.executor.ResultSet;
import com.arcadedb.schema.DocumentType;
import com.arcadedb.schema.Schema;

import java.util.*;

public class DropTypeStatement extends DDLStatement {

  public Identifier     name;
  public InputParameter nameParam;
  public boolean        ifExists = false;
  public boolean        unsafe   = false;

  public DropTypeStatement(final int id) {
    super(id);
  }

  public DropTypeStatement(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public ResultSet executeDDL(final CommandContext ctx) {
    final Schema schema = ctx.getDatabase().getSchema();

    final String typeName;
    if (name != null) {
      typeName = name.getStringValue();
    } else {
      typeName = String.valueOf(nameParam.getValue(ctx.getInputParameters()));
    }

    if (ifExists && !schema.existsType(typeName))
      return new InternalResultSet();

    final DocumentType typez = schema.getType(typeName);
    if (typez == null) {
      throw new CommandExecutionException("Type '" + typeName + "' does not exist");
    }

    if (!unsafe && ctx.getDatabase().countType(typez.getName(), false) > 0) {
      //check vertex or edge
      if (typez.getType() == Vertex.RECORD_TYPE) {
        throw new CommandExecutionException("'DROP TYPE' command cannot drop type '" + typeName
            + "' because it contains Vertices. Use 'DELETE VERTEX' command first to avoid broken edges in a database, or apply the 'UNSAFE' keyword to force it");
      } else if (typez.getType() == Edge.RECORD_TYPE) {
        // FOUND EDGE TYPE
        throw new CommandExecutionException("'DROP TYPE' command cannot drop type '" + typeName
            + "' because it contains Edges. Use 'DELETE EDGE' command first to avoid broken vertices in a database, or apply the 'UNSAFE' keyword to force it");
      }
    }

    schema.dropType(typeName);

    final InternalResultSet rs = new InternalResultSet();
    final ResultInternal result = new ResultInternal();
    result.setProperty("operation", "drop type");
    result.setProperty("typeName", typeName);
    rs.add(result);
    return rs;
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("DROP TYPE ");
    if (name != null) {
      name.toString(params, builder);
    } else {
      nameParam.toString(params, builder);
    }
    if (ifExists) {
      builder.append(" IF EXISTS");
    }
    if (unsafe) {
      builder.append(" UNSAFE");
    }
  }

  @Override
  public DropTypeStatement copy() {
    final DropTypeStatement result = new DropTypeStatement(-1);
    result.name = name == null ? null : name.copy();
    result.nameParam = nameParam == null ? null : nameParam.copy();
    result.ifExists = ifExists;
    result.unsafe = unsafe;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final DropTypeStatement that = (DropTypeStatement) o;

    if (unsafe != that.unsafe)
      return false;
    if (ifExists != that.ifExists)
      return false;
    return ifExists == that.ifExists && unsafe == that.unsafe && Objects.equals(name, that.name) && Objects.equals(nameParam, that.nameParam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, nameParam, ifExists, unsafe);
  }
}
/* JavaCC - OriginalChecksum=8c475e1225074f68be37fce610987d54 (do not edit this line) */
