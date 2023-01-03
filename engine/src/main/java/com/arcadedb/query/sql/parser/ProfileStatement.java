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
/* Generated By:JJTree: Do not edit this line. OExplainStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.BasicCommandContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.ExecutionPlan;
import com.arcadedb.query.sql.executor.InternalExecutionPlan;
import com.arcadedb.query.sql.executor.ResultSet;
import com.arcadedb.query.sql.executor.UpdateExecutionPlan;

import java.util.*;

public class ProfileStatement extends Statement {

  protected Statement statement;

  public ProfileStatement(final int id) {
    super(id);
  }

  public ProfileStatement(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("EXPLAIN ");
    statement.toString(params, builder);
  }

  @Override
  public ResultSet execute(final Database db, final Object[] args, final CommandContext parentCtx, final boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(args);
    final ExecutionPlan executionPlan = statement.createExecutionPlan(ctx, true);
    if (executionPlan instanceof UpdateExecutionPlan) {
      ((UpdateExecutionPlan) executionPlan).executeInternal();
    }

    final LocalResultSet rs = new LocalResultSet((InternalExecutionPlan) executionPlan);

    while (rs.hasNext()) {
      rs.next();
    }

    final ExplainResultSet result = new ExplainResultSet(
        rs.getExecutionPlan().orElseThrow(() -> new CommandExecutionException("Cannot profile command: " + statement)));
    rs.close();
    return result;

  }

  @Override
  public ResultSet execute(final Database db, final Map args, final CommandContext parentCtx, final boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(args);

    final ExecutionPlan executionPlan = statement.createExecutionPlan(ctx, true);

    final LocalResultSet rs = new LocalResultSet((InternalExecutionPlan) executionPlan);

    while (rs.hasNext()) {
      rs.next();
    }

    final ExplainResultSet result = new ExplainResultSet(
        rs.getExecutionPlan().orElseThrow(() -> new CommandExecutionException("Cannot profile command: " + statement)));
    rs.close();
    return result;
  }

  @Override
  public InternalExecutionPlan createExecutionPlan(final CommandContext ctx, final boolean profile) {
    return statement.createExecutionPlan(ctx, profile);
  }

  @Override
  public ProfileStatement copy() {
    final ProfileStatement result = new ProfileStatement(-1);
    result.statement = statement == null ? null : statement.copy();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final ProfileStatement that = (ProfileStatement) o;

    return Objects.equals(statement, that.statement);
  }

  @Override
  public int hashCode() {
    return statement != null ? statement.hashCode() : 0;
  }

  @Override
  public boolean isIdempotent() {
    return true;
  }
}
/* JavaCC - OriginalChecksum=9fdd24510993cbee32e38a51c838bdb4 (do not edit this line) */
