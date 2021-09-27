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
/* Generated By:JJTree: Do not edit this line. OIndexIdentifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class IndexIdentifier extends SimpleNode {

  public enum Type {
    INDEX, VALUES, VALUESASC, VALUESDESC
  }

  protected Type      type;
  protected String    indexNameString;
  protected IndexName indexName;

  public IndexIdentifier(int id) {
    super(id);
  }

  public IndexIdentifier(SqlParser p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    switch (type) {
    case INDEX:
      builder.append("INDEX");
      break;
    case VALUES:
      builder.append("INDEXVALUES");
      break;
    case VALUESASC:
      builder.append("INDEXVALUESASC");
      break;
    case VALUESDESC:
      builder.append("INDEXVALUESDESC");
      break;
    }
    builder.append(":");
    if (indexNameString != null) {
      builder.append(indexNameString);
    } else {
      indexName.toString(params, builder);
    }
  }

  public String getIndexName() {
    if (indexName != null) {
      return indexName.toString();
    }
    return indexNameString;
  }

  public Type getType() {
    return type;
  }

  public IndexIdentifier copy() {
    IndexIdentifier result = new IndexIdentifier(-1);
    result.type = type;
    result.indexNameString = indexNameString;
    result.indexName = indexName.copy();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    IndexIdentifier that = (IndexIdentifier) o;

    if (type != that.type)
      return false;
    if (indexNameString != null ? !indexNameString.equals(that.indexNameString) : that.indexNameString != null)
      return false;
    return indexName != null ? indexName.equals(that.indexName) : that.indexName == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (indexNameString != null ? indexNameString.hashCode() : 0);
    result = 31 * result + (indexName != null ? indexName.hashCode() : 0);
    return result;
  }

  public Result serialize() {
    ResultInternal result = new ResultInternal();
    result.setProperty("type", type.toString());
    result.setProperty("indexNameString", indexNameString);

    if (indexName != null) {
      result.setProperty("indexName", indexName.serialize());
    }
    return result;
  }

  public void deserialize(Result fromResult) {
    type = Type.valueOf(fromResult.getProperty("type"));
    indexNameString = fromResult.getProperty("indexNameString");

    if (fromResult.getProperty("indexName") != null) {
      indexName = new IndexName(-1);
      indexName.deserialize(fromResult.getProperty("indexName"));
    }
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setIndexNameString(String indexNameString) {
    this.indexNameString = indexNameString;
  }

  public void setIndexName(IndexName indexName) {
    this.indexName = indexName;
  }
}
/* JavaCC - OriginalChecksum=025f134fd4b27b84210738cdb6dd027c (do not edit this line) */
