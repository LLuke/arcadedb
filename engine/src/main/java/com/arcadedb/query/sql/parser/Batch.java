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
/* Generated By:JJTree: Do not edit this line. OBatch.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.CommandContext;

import java.util.*;

public class Batch extends SimpleNode {

  protected PInteger num;

  protected InputParameter inputParam;

  public Batch(int id) {
    super(id);
  }

  public Batch(SqlParser p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public java.lang.Integer evaluate(CommandContext ctx) {
    if (this.num != null) {
      return num.getValue().intValue();
    } else if (inputParam != null) {
      Object obj = inputParam.getValue(ctx.getInputParameters());
      if (obj == null || !(obj instanceof Number)) {
        throw new CommandExecutionException("" + obj + " is not a number (BATCH)");
      }
      return ((Number) obj).intValue();
    }
    return -1;
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    if (num == null && inputParam == null) {
      return;
    }

    builder.append(" BATCH ");
    if (num != null) {
      num.toString(params, builder);
    } else {
      inputParam.toString(params, builder);
    }
  }

  public Batch copy() {
    Batch result = new Batch(-1);
    result.inputParam = inputParam == null ? null : inputParam.copy();
    result.num = num == null ? null : num.copy();
    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Batch oBatch = (Batch) o;

    if (num != null ? !num.equals(oBatch.num) : oBatch.num != null)
      return false;
    return inputParam != null ? inputParam.equals(oBatch.inputParam) : oBatch.inputParam == null;
  }

  @Override public int hashCode() {
    int result = num != null ? num.hashCode() : 0;
    result = 31 * result + (inputParam != null ? inputParam.hashCode() : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=b1587460e08cbf21086d8c8fcca192e0 (do not edit this line) */
