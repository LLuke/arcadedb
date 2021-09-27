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
 */
/* Generated By:JJTree: Do not edit this line. ODeleteVertexStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.BasicCommandContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.DeleteExecutionPlan;
import com.arcadedb.query.sql.executor.DeleteVertexExecutionPlanner;
import com.arcadedb.query.sql.executor.ResultSet;

import java.util.*;

public class DeleteVertexStatement extends Statement {

  protected boolean     from         = false;
  protected FromClause  fromClause;
  protected WhereClause whereClause;
  protected boolean     returnBefore = false;
  protected Batch       batch        = null;

  public DeleteVertexStatement(int id) {
    super(id);
  }

  public DeleteVertexStatement(SqlParser p, int id) {
    super(p, id);
  }

  @Override
  public ResultSet execute(Database db, Map params, CommandContext parentCtx, boolean usePlanCache) {
    BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(params);
    DeleteExecutionPlan executionPlan = createExecutionPlan(ctx, false);
    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  @Override
  public ResultSet execute(Database db, Object[] args, CommandContext parentCtx, boolean usePlanCache) {
    BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(args);
    DeleteExecutionPlan executionPlan = createExecutionPlan(ctx, false);
    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  public DeleteExecutionPlan createExecutionPlan(CommandContext ctx, boolean enableProfiling) {
    DeleteVertexExecutionPlanner planner = new DeleteVertexExecutionPlanner(this);
    return planner.createExecutionPlan(ctx, enableProfiling);
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    builder.append("DELETE VERTEX ");
    if (from) {
      builder.append("FROM ");
    }
    fromClause.toString(params, builder);
    if (returnBefore) {
      builder.append(" RETURN BEFORE");
    }
    if (whereClause != null) {
      builder.append(" WHERE ");
      whereClause.toString(params, builder);
    }
    if (limit != null) {
      limit.toString(params, builder);
    }
    if (batch != null) {
      batch.toString(params, builder);
    }
  }

  @Override
  public DeleteVertexStatement copy() {
    DeleteVertexStatement result = new DeleteVertexStatement(-1);
    result.from = from;
    result.fromClause = fromClause == null ? null : fromClause.copy();
    result.whereClause = whereClause == null ? null : whereClause.copy();
    result.returnBefore = returnBefore;
    result.limit = limit == null ? null : limit.copy();
    result.batch = batch == null ? null : batch.copy();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DeleteVertexStatement that = (DeleteVertexStatement) o;

    if (from != that.from)
      return false;
    if (returnBefore != that.returnBefore)
      return false;
    if (fromClause != null ? !fromClause.equals(that.fromClause) : that.fromClause != null)
      return false;
    if (whereClause != null ? !whereClause.equals(that.whereClause) : that.whereClause != null)
      return false;
    if (limit != null ? !limit.equals(that.limit) : that.limit != null)
      return false;
    return batch != null ? batch.equals(that.batch) : that.batch == null;
  }

  @Override
  public int hashCode() {
    int result = (from ? 1 : 0);
    result = 31 * result + (fromClause != null ? fromClause.hashCode() : 0);
    result = 31 * result + (whereClause != null ? whereClause.hashCode() : 0);
    result = 31 * result + (returnBefore ? 1 : 0);
    result = 31 * result + (limit != null ? limit.hashCode() : 0);
    result = 31 * result + (batch != null ? batch.hashCode() : 0);
    return result;
  }

  public boolean isFrom() {
    return from;
  }

  public void setFrom(boolean from) {
    this.from = from;
  }

  public FromClause getFromClause() {
    return fromClause;
  }

  public void setFromClause(FromClause fromClause) {
    this.fromClause = fromClause;
  }

  public WhereClause getWhereClause() {
    return whereClause;
  }

  public void setWhereClause(WhereClause whereClause) {
    this.whereClause = whereClause;
  }

  public boolean isReturnBefore() {
    return returnBefore;
  }

  public void setReturnBefore(boolean returnBefore) {
    this.returnBefore = returnBefore;
  }

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }
}
/* JavaCC - OriginalChecksum=b62d3046f4bd1b9c1f78ed4f125b06d3 (do not edit this line) */
