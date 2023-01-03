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
/* Generated By:JJTree: Do not edit this line. OCollection.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Record;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class PCollection extends SimpleNode {
  protected List<Expression> expressions = new ArrayList<Expression>();

  public PCollection(final int id) {
    super(id);
  }

  public PCollection(final SqlParser p, final int id) {
    super(p, id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("[");
    boolean first = true;
    for (final Expression expr : expressions) {
      if (!first) {
        builder.append(", ");
      }
      expr.toString(params, builder);
      first = false;
    }
    builder.append("]");
  }

  public void add(final Expression exp) {
    this.expressions.add(exp);
  }

  public Object execute(final Record iCurrentRecord, final CommandContext ctx) {
    final List<Object> result = new ArrayList<Object>();
    for (final Expression exp : expressions) {
      result.add(exp.execute(iCurrentRecord, ctx));
    }
    return result;
  }

  public Object execute(final Result iCurrentRecord, final CommandContext ctx) {
    final List<Object> result = new ArrayList<Object>();
    for (final Expression exp : expressions) {
      result.add(exp.execute(iCurrentRecord, ctx));
    }
    return result;
  }

  public boolean needsAliases(final Set<String> aliases) {
    for (final Expression expr : this.expressions) {
      if (expr.needsAliases(aliases)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAggregate() {
    for (final Expression exp : this.expressions) {
      if (exp.isAggregate()) {
        return true;
      }
    }
    return false;
  }

  public PCollection splitForAggregation(final AggregateProjectionSplit aggregateProj) {
    if (isAggregate()) {
      final PCollection result = new PCollection(-1);
      for (final Expression exp : this.expressions) {
        if (exp.isAggregate() || exp.isEarlyCalculated()) {
          result.expressions.add(exp.splitForAggregation(aggregateProj));
        } else {
          throw new CommandExecutionException("Cannot mix aggregate and non-aggregate operations in a collection: " + this);
        }
      }
      return result;
    } else {
      return this;
    }
  }

  public boolean isEarlyCalculated() {
    for (final Expression exp : expressions) {
      if (!exp.isEarlyCalculated()) {
        return false;
      }
    }
    return true;
  }

  public PCollection copy() {
    final PCollection result = new PCollection(-1);
    result.expressions = expressions == null ? null : expressions.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final PCollection that = (PCollection) o;

    return Objects.equals(expressions, that.expressions);
  }

  @Override
  public int hashCode() {
    return expressions != null ? expressions.hashCode() : 0;
  }

  public boolean refersToParent() {
    if (expressions != null) {
      for (final Expression exp : expressions) {
        if (exp != null && exp.refersToParent()) {
          return true;
        }
      }
    }
    return false;
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    if (expressions != null) {
      result.setProperty("expressions", expressions.stream().map(x -> x.serialize()).collect(Collectors.toList()));
    }
    return result;
  }

  public void deserialize(final Result fromResult) {
    if (fromResult.getProperty("expressions") != null) {
      expressions = new ArrayList<>();
      final List<Result> ser = fromResult.getProperty("expressions");
      for (final Result item : ser) {
        final Expression exp = new Expression(-1);
        exp.deserialize(item);
        expressions.add(exp);
      }
    }
  }

  public boolean isCacheable() {
    for (final Expression exp : expressions) {
      if (!exp.isCacheable()) {
        return false;
      }
    }
    return true;
  }

  public List<Expression> getExpressions() {
    return expressions;
  }
}
/* JavaCC - OriginalChecksum=c93b20138b2ae58c5f76e458c34b5946 (do not edit this line) */
