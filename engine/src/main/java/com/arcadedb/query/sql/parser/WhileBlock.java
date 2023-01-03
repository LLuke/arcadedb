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
/* Generated By:JJTree: Do not edit this line. WhileBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.BasicCommandContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.ForEachExecutionPlan;
import com.arcadedb.query.sql.executor.ResultSet;
import com.arcadedb.query.sql.executor.UpdateExecutionPlan;

import java.util.*;
import java.util.stream.*;

public class WhileBlock extends Statement {

  protected BooleanExpression condition;
  protected List<Statement>   statements = new ArrayList<>();

  public WhileBlock(final int id) {
    super(id);
  }

  public WhileBlock(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public ResultSet execute(final Database db, final Object[] args, final CommandContext parentCtx,final  boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null)
      ctx.setParentWithoutOverridingChild(parentCtx);

    ctx.setDatabase(db);
    ctx.setInputParameters(args);
    final UpdateExecutionPlan executionPlan;
    if (usePlanCache)
      executionPlan = createExecutionPlan(ctx, false);
     else
      executionPlan = (UpdateExecutionPlan) createExecutionPlanNoCache(ctx, false);

    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  @Override
  public ResultSet execute(final Database db, final Map params, final CommandContext parentCtx, final boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(params);

    final UpdateExecutionPlan executionPlan;
    if (usePlanCache) {
      executionPlan = createExecutionPlan(ctx, false);
    } else {
      executionPlan = (UpdateExecutionPlan) createExecutionPlanNoCache(ctx, false);
    }

    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  public UpdateExecutionPlan createExecutionPlan(final CommandContext ctx,final  boolean enableProfiling) {
    final ForEachExecutionPlan plan = new ForEachExecutionPlan(ctx);
    plan.chain(new WhileStep(condition, statements, ctx, enableProfiling));
    return plan;
  }

  @Override
  public Statement copy() {
    final WhileBlock result = new WhileBlock(-1);
    result.condition = condition.copy();
    result.statements = statements.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  public boolean containsReturn() {
    for (final Statement stm : this.statements) {
      if (stm instanceof ReturnStatement) {
        return true;
      }
      if (stm instanceof ForEachBlock && ((ForEachBlock) stm).containsReturn()) {
        return true;
      }
      if (stm instanceof IfStatement && ((IfStatement) stm).containsReturn()) {
        return true;
      }
      if (stm instanceof WhileBlock && ((WhileBlock) stm).containsReturn()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final WhileBlock that = (WhileBlock) o;

    if (!Objects.equals(condition, that.condition))
      return false;
    return Objects.equals(statements, that.statements);
  }

  @Override
  public int hashCode() {
    int result = condition != null ? condition.hashCode() : 0;
    result = 31 * result + (statements != null ? statements.hashCode() : 0);
    return result;
  }

  public void toString(final Map<String, Object> params,final  StringBuilder builder) {
    builder.append("WHILE (");
    condition.toString(params, builder);
    builder.append(") {\n");
    for (final Statement stm : statements) {
      stm.toString(params, builder);
      builder.append("\n");
    }
    builder.append("}");
  }
}
/* JavaCC - OriginalChecksum=1b38ee666f89790d0f54cc5823b99286 (do not edit this line) */
