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
/* Generated By:JJTree: Do not edit this line. OUpdateOperations.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;
import java.util.stream.*;

public class UpdateOperations extends SimpleNode {
  public static final int TYPE_SET       = 0;
  public static final int TYPE_PUT       = 1;
  public static final int TYPE_MERGE     = 2;
  public static final int TYPE_CONTENT   = 3;
  public static final int TYPE_INCREMENT = 4;
  public static final int TYPE_ADD       = 5;
  public static final int TYPE_REMOVE    = 6;

  protected int type;

  protected List<UpdateItem> updateItems = new ArrayList<UpdateItem>();

  protected List<UpdatePutItem> updatePutItems = new ArrayList<UpdatePutItem>();

  protected Json json;

  protected List<UpdateIncrementItem> updateIncrementItems = new ArrayList<UpdateIncrementItem>();

  protected List<UpdateRemoveItem> updateRemoveItems = new ArrayList<UpdateRemoveItem>();

  public UpdateOperations(int id) {
    super(id);
  }

  public UpdateOperations(SqlParser p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    boolean first = true;
    switch (type) {
    case TYPE_SET:
      builder.append("SET ");
      for (UpdateItem item : this.updateItems) {
        if (!first) {
          builder.append(", ");
        }
        item.toString(params, builder);
        first = false;
      }
      break;
    case TYPE_PUT:
      builder.append("PUT ");
      for (UpdatePutItem item : this.updatePutItems) {
        if (!first) {
          builder.append(", ");
        }
        item.toString(params, builder);
        first = false;
      }
      break;
    case TYPE_MERGE:
      builder.append("MERGE ");
      json.toString(params, builder);
      break;
    case TYPE_CONTENT:
      builder.append("CONTENT ");
      json.toString(params, builder);
      break;
    case TYPE_INCREMENT:
      builder.append("INCREMENT ");
      for (UpdateIncrementItem item : this.updateIncrementItems) {
        if (!first) {
          builder.append(", ");
        }
        item.toString(params, builder);
        first = false;
      }
      break;
    case TYPE_ADD:
      builder.append("ADD ");
      for (UpdateIncrementItem item : this.updateIncrementItems) {
        if (!first) {
          builder.append(", ");
        }
        item.toString(params, builder);
        first = false;
      }
      break;
    case TYPE_REMOVE:
      builder.append("REMOVE ");
      for (UpdateRemoveItem item : this.updateRemoveItems) {
        if (!first) {
          builder.append(", ");
        }
        item.toString(params, builder);
        first = false;
      }
      break;

    }
  }

  public UpdateOperations copy() {

    UpdateOperations result = new UpdateOperations(-1);
    result.type = type;
    result.updateItems = updateItems == null ? null : updateItems.stream().map(x -> x.copy()).collect(Collectors.toList());
    result.updatePutItems = updatePutItems == null ? null : updatePutItems.stream().map(x -> x.copy()).collect(Collectors.toList());
    result.json = json == null ? null : json.copy();
    result.updateIncrementItems =
        updateIncrementItems == null ? null : updateIncrementItems.stream().map(x -> x.copy()).collect(Collectors.toList());
    result.updateRemoveItems =
        updateRemoveItems == null ? null : updateRemoveItems.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    UpdateOperations that = (UpdateOperations) o;

    if (type != that.type)
      return false;
    if (updateItems != null ? !updateItems.equals(that.updateItems) : that.updateItems != null)
      return false;
    if (updatePutItems != null ? !updatePutItems.equals(that.updatePutItems) : that.updatePutItems != null)
      return false;
    if (json != null ? !json.equals(that.json) : that.json != null)
      return false;
    if (updateIncrementItems != null ? !updateIncrementItems.equals(that.updateIncrementItems) : that.updateIncrementItems != null)
      return false;
    return updateRemoveItems != null ? updateRemoveItems.equals(that.updateRemoveItems) : that.updateRemoveItems == null;
  }

  @Override public int hashCode() {
    int result = type;
    result = 31 * result + (updateItems != null ? updateItems.hashCode() : 0);
    result = 31 * result + (updatePutItems != null ? updatePutItems.hashCode() : 0);
    result = 31 * result + (json != null ? json.hashCode() : 0);
    result = 31 * result + (updateIncrementItems != null ? updateIncrementItems.hashCode() : 0);
    result = 31 * result + (updateRemoveItems != null ? updateRemoveItems.hashCode() : 0);
    return result;
  }

  public int getType() {
    return type;
  }

  public List<UpdateItem> getUpdateItems() {
    return updateItems;
  }

  public List<UpdatePutItem> getUpdatePutItems() {
    return updatePutItems;
  }

  public Json getJson() {
    return json;
  }

  public List<UpdateIncrementItem> getUpdateIncrementItems() {
    return updateIncrementItems;
  }

  public List<UpdateRemoveItem> getUpdateRemoveItems() {
    return updateRemoveItems;
  }
}
/* JavaCC - OriginalChecksum=0eca3b3e4e3d96c42db57b7cd89cf755 (do not edit this line) */
