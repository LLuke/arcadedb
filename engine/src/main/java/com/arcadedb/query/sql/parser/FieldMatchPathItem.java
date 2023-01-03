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
/* Generated by: JJTree: Do not edit this line. FieldMatchPathItem.java Version 1.1 */
/* ParserGeneratorCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Document;
import com.arcadedb.database.Identifiable;
import com.arcadedb.exception.ArcadeDBException;
import com.arcadedb.query.sql.executor.CommandContext;

import java.util.*;

public class FieldMatchPathItem extends MatchPathItem {

  protected Identifier field;

  private SuffixIdentifier exp;

  public FieldMatchPathItem(final int id) {
    super(id);
  }

  public FieldMatchPathItem(final SqlParser p, final int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   */
  public boolean isBidirectional() {
    return false;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append(".");
    field.toString(params, builder);
    if (filter != null) {
      filter.toString(params, builder);
    }
  }

  protected Iterable<Identifiable> traversePatternEdge(final MatchStatement.MatchContext matchContext, final Identifiable startingPoint, final CommandContext iCommandContext) {

    //    Iterable possibleResults = null;
    //    if (filter != null) {
    //      OIdentifiable matchedNode = matchContext.matched.get(filter.getAlias());
    //      if (matchedNode != null) {
    //        possibleResults = Collections.singleton(matchedNode);
    //      } else if (matchContext.matched.containsKey(filter.getAlias())) {
    //        possibleResults = Collections.emptySet();//optional node, the matched element is a
    // null value
    //      } else {
    //        possibleResults = matchContext.candidates == null ? null :
    // matchContext.candidates.get(filter.getAlias());
    //      }
    //    }

    if (exp == null) {
      exp = new SuffixIdentifier(field);
    }
    // TODO check possible results!
    final Object qR = this.exp.execute(startingPoint, iCommandContext);
    return (qR instanceof Iterable && !(qR instanceof Document)) ? (Iterable) qR : Collections.singleton((Identifiable) qR);
  }

  @Override
  public boolean equals( final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    final  FieldMatchPathItem that = (FieldMatchPathItem) o;
    return Objects.equals(field, that.field) && Objects.equals(exp, that.exp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), field, exp);
  }

  @Override
  public MatchPathItem copy() {
    FieldMatchPathItem result = null;
    try {
      result = getClass().getConstructor(Integer.TYPE).newInstance(-1);
    } catch (final Exception e) {
      throw new ArcadeDBException(e);
    }
    result.field = field == null ? null : field.copy();
    result.method = method == null ? null : method.copy();
    result.filter = filter == null ? null : filter.copy();
    return result;
  }

  public Identifier getField() {
    return field;
  }

  public SuffixIdentifier getExp() {
    if (exp == null) {
      exp = new SuffixIdentifier(field);
    }
    return exp;
  }
}
/* ParserGeneratorCC - OriginalChecksum=6af5afb400b4cc27055de525bc06ca68 (do not edit this line) */
