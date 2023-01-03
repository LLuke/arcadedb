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
/* Generated By:JJTree: Do not edit this line. OProjection.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Document;
import com.arcadedb.exception.CommandSQLParsingException;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class Projection extends SimpleNode {

  protected boolean distinct = false;

  List<ProjectionItem> items;
  // runtime
  private Set<String> excludes;

  public Projection(final List<ProjectionItem> items, final boolean distinct) {
    super(-1);
    this.items = items;
    this.distinct = distinct;
    //TODO make the whole class immutable!
  }

  public Projection(final int id) {
    super(id);
  }

  public Projection(final SqlParser p, final int id) {
    super(p, id);
  }

  public List<ProjectionItem> getItems() {
    return items;
  }

  public void setItems(final List<ProjectionItem> items) {
    this.items = items;
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (items == null) {
      return;
    }
    boolean first = true;

    if (distinct) {
      builder.append("DISTINCT ");
    }
    // print * before
    for (final ProjectionItem item : items) {
      if (item.isAll()) {
        if (!first) {
          builder.append(", ");
        }

        item.toString(params, builder);
        first = false;
      }
    }

    // and then the rest of the projections
    for (final ProjectionItem item : items) {
      if (!item.isAll()) {
        if (!first) {
          builder.append(", ");
        }

        item.toString(params, builder);
        first = false;
      }
    }
  }

  public Result calculateSingle(final CommandContext iContext, final Result iRecord) {
    initExcludes(iContext);
    if (isExpand()) {
      throw new IllegalStateException("This is an expand projection, it cannot be calculated as a single result" + this);
    }

    if (items.size() == 0 || (items.size() == 1 && items.get(0).isAll()) && items.get(0).nestedProjection == null) {
      return iRecord;
    }

    final ResultInternal result = new ResultInternal();
    for (final ProjectionItem item : items) {
      if (item.exclude) {
        continue;
      }
      if (item.isAll()) {
        for (final String alias : iRecord.getPropertyNames()) {
          if (this.excludes.contains(alias)) {
            continue;
          }
          Object val = item.convert(iRecord.getProperty(alias));
          if (item.nestedProjection != null) {
            val = item.nestedProjection.apply(item.expression, val, iContext);
          }
          result.setProperty(alias, val);
        }
        if (iRecord.getElement().isPresent()) {
          final Document x = iRecord.getElement().get();
          if (!this.excludes.contains("@rid")) {
            result.setProperty("@rid", x.getIdentity());
          }
          if (!this.excludes.contains("@type")) {
            result.setProperty("@type", x.getType().getName());
          }
        }
      } else {
        result.setProperty(item.getProjectionAliasAsString(), item.execute(iRecord, iContext));
      }
    }

    for (final String key : iRecord.getMetadataKeys()) {
      if (!result.getMetadataKeys().contains(key)) {
        result.setMetadata(key, iRecord.getMetadata(key));
      }
    }
    return result;
  }

  private void initExcludes(final CommandContext iContext) {
    if (excludes == null) {
      this.excludes = new HashSet<String>();
      for (final ProjectionItem item : items) {
        if (item.exclude) {
          this.excludes.add(item.getProjectionAliasAsString());
        }
      }
    }
  }

  public boolean isExpand() {
    return items != null && items.size() == 1 && items.get(0).isExpand();
  }

  public void validate() {
    if (items != null && items.size() > 1) {
      for (final ProjectionItem item : items) {
        if (item.isExpand()) {
          throw new CommandSQLParsingException("Cannot execute a query with expand() together with other projections");
        }
      }
    }
  }

  public Projection getExpandContent() {
    final Projection result = new Projection(-1);
    result.setItems(new ArrayList<>());
    result.getItems().add(this.getItems().get(0).getExpandContent());
    return result;
  }

  public List<String> getAllAliases() {
    return items.stream().map(i -> i.getProjectionAliasAsString()).collect(Collectors.toList());
  }

  public Projection copy() {
    final Projection result = new Projection(-1);
    if (items != null) {
      result.items = items.stream().map(x -> x.copy()).collect(Collectors.toList());
    }
    result.distinct = distinct;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final Projection that = (Projection) o;

    return Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public void setDistinct(final boolean distinct) {
    this.distinct = distinct;
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    if (items != null) {
      for (final ProjectionItem item : items) {
        item.extractSubQueries(collector);
      }
    }
  }

  public boolean refersToParent() {
    for (final ProjectionItem item : items) {
      if (item.refersToParent()) {
        return true;
      }
    }
    return false;
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    result.setProperty("distinct", distinct);
    if (items != null) {
      result.setProperty("items", items.stream().map(x -> x.serialize()).collect(Collectors.toList()));
    }
    return result;
  }

  public void deserialize(final Result fromResult) {
    distinct = fromResult.getProperty("distinct");
    if (fromResult.getProperty("items") != null) {
      items = new ArrayList<>();

      final List<Result> ser = fromResult.getProperty("items");
      for (final Result x : ser) {
        final ProjectionItem item = new ProjectionItem(-1);
        item.deserialize(x);
        items.add(item);
      }
    }
  }

  public boolean isCacheable() {
    if (items != null) {
      for (final ProjectionItem item : items) {
        if (!item.isCacheable()) {
          return false;
        }
      }
    }
    return true;
  }
}
/* JavaCC - OriginalChecksum=3a650307b53bae626dc063c4b35e62c3 (do not edit this line) */
