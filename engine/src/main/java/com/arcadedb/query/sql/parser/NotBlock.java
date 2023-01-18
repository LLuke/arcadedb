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
/* Generated By:JJTree: Do not edit this line. ONotBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.schema.DocumentType;

import java.util.*;

public class NotBlock extends BooleanExpression {
  protected BooleanExpression sub;

  protected boolean negate = false;

  public NotBlock(final int id) {
    super(id);
  }

  public NotBlock(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public boolean evaluate(final Identifiable currentRecord, final CommandContext ctx) {
    if (sub == null) {
      return true;
    }
    final boolean result = sub.evaluate(currentRecord, ctx);
    if (negate) {
      return !result;
    }
    return result;
  }

  @Override
  public boolean evaluate(final Result currentRecord, final CommandContext ctx) {
    if (sub == null) {
      return true;
    }
    final boolean result = sub.evaluate(currentRecord, ctx);
    if (negate) {
      return !result;
    }
    return result;
  }

  public BooleanExpression getSub() {
    return sub;
  }

  public void setSub(final BooleanExpression sub) {
    this.sub = sub;
  }

  public boolean isNegate() {
    return negate;
  }

  public void setNegate(final boolean negate) {
    this.negate = negate;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (negate) {
      builder.append("NOT ");
    }
    sub.toString(params, builder);
  }

  public List<BinaryCondition> getIndexedFunctionConditions(final DocumentType iSchemaClass, final Database database) {
    if (sub == null) {
      return null;
    }
    if (negate) {
      return null;
    }
    return sub.getIndexedFunctionConditions(iSchemaClass, database);
  }

  @Override
  public List<AndBlock> flatten() {
    if (!negate) {
      return sub.flatten();
    }
    return super.flatten();
  }

  @Override
  public boolean needsAliases(final Set<String> aliases) {
    return sub.needsAliases(aliases);
  }

  @Override
  public NotBlock copy() {
    final NotBlock result = new NotBlock(-1);
    result.sub = sub.copy();
    result.negate = negate;
    return result;
  }

  @Override
  public void extractSubQueries(final SubQueryCollector collector) {
    sub.extractSubQueries(collector);
  }

  protected Object[] getIdentityElements() {
    return new Object[] { sub };
  }

  @Override
  public List<String> getMatchPatternInvolvedAliases() {
    return sub.getMatchPatternInvolvedAliases();
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { sub };
  }

  @Override
  public boolean isAlwaysTrue() {
    if (negate)
      return false;

    return sub.isAlwaysTrue();
  }
}
/* JavaCC - OriginalChecksum=1926313b3f854235aaa20811c22d583b (do not edit this line) */
