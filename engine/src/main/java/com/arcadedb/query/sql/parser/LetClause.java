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
/* Generated By:JJTree: Do not edit this line. OLetClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class LetClause extends SimpleNode {

  protected List<LetItem> items = new ArrayList<LetItem>();

  public LetClause(final int id) {
    super(id);
  }

  public LetClause(final SqlParser p, final int id) {
    super(p, id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("LET ");
    boolean first = true;
    for (final LetItem item : items) {
      if (!first) {
        builder.append(", ");
      }
      item.toString(params, builder);
      first = false;
    }
  }

  public void addItem(final LetItem item) {
    this.items.add(item);
  }

  public LetClause copy() {
    final LetClause result = new LetClause(-1);
    result.items = items.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  public List<LetItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final LetClause that = (LetClause) o;

    return Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  public boolean refersToParent() {
    for (final LetItem item : items) {
      if (item.refersToParent()) {
        return true;
      }
    }
    return false;
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    for (final LetItem item : items) {
      item.extractSubQueries(collector);
    }
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    if (items != null) {
      result.setProperty("items", items.stream().map(x -> x.serialize()).collect(Collectors.toList()));
    }
    return result;
  }

  public void deserialize(final Result fromResult) {
    if (fromResult.getProperty("items") != null) {
      final List<Result> ser = fromResult.getProperty("items");
      items = new ArrayList<>();
      for (final Result r : ser) {
        final LetItem exp = new LetItem(-1);
        exp.deserialize(r);
        items.add(exp);
      }
    }
  }

  public boolean isCacheable() {
    for (final LetItem item : items) {
      if (!item.isCacheable()) {
        return false;
      }
    }
    return true;
  }
}

/* JavaCC - OriginalChecksum=201a864b5ed7f1fbe0533843a7acd03d (do not edit this line) */
