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
/* Generated By:JJTree: Do not edit this line. OExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.database.RID;
import com.arcadedb.database.Record;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.AggregationContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class Expression extends SimpleNode {

  protected Boolean singleQuotes;
  protected Boolean doubleQuotes;

  protected boolean               isNull = false;
  protected Rid                   rid;
  protected MathExpression        mathExpression;
  protected ArrayConcatExpression arrayConcatExpression;
  protected Json                  json;
  protected Boolean               booleanValue;

  public Expression(int id) {
    super(id);
  }

  public Expression(SqlParser p, int id) {
    super(p, id);
  }

  public Expression(Identifier identifier) {
    mathExpression = new BaseExpression(identifier);
  }

  public Expression(Identifier identifier, Modifier modifier) {

    mathExpression = new BaseExpression(identifier, modifier);
  }

  public Expression(RecordAttribute attr, Modifier modifier) {
    mathExpression = new BaseExpression(attr, modifier);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public Object execute(Identifiable iCurrentRecord, CommandContext ctx) {
    if (isNull) {
      return null;
    }
    if (rid != null) {
      return rid.toRecordId(iCurrentRecord, ctx);
    }
    if (mathExpression != null) {
      return mathExpression.execute(iCurrentRecord, ctx);
    }
    if (arrayConcatExpression != null) {
      return arrayConcatExpression.execute(iCurrentRecord, ctx);
    }
    if (json != null) {
      return json.toMap(iCurrentRecord, ctx);
    }
    if (booleanValue != null) {
      return booleanValue;
    }
    if (value instanceof PNumber) {
      return ((PNumber) value).getValue();//only for old executor (manually replaced params)
    }

    //from here it's old stuff, only for the old executor
    if (value instanceof Rid) {
      Rid v = (Rid) value;
      return new RID(ctx.getDatabase(), v.bucket.getValue().intValue(), v.position.getValue().longValue());
    } else if (value instanceof MathExpression) {
      return ((MathExpression) value).execute(iCurrentRecord, ctx);
    } else if (value instanceof ArrayConcatExpression) {
      return ((ArrayConcatExpression) value).execute(iCurrentRecord, ctx);
    } else if (value instanceof Json) {
      return ((Json) value).toMap(iCurrentRecord, ctx);
    } else if (value instanceof String) {
      return value;
    } else if (value instanceof java.lang.Number) {
      return value;
    }

    return value;
  }

  public Object execute(Result iCurrentRecord, CommandContext ctx) {
    if (isNull) {
      return null;
    }
    if (rid != null) {
      return rid.toRecordId(iCurrentRecord, ctx);
    }
    if (mathExpression != null) {
      return mathExpression.execute(iCurrentRecord, ctx);
    }
    if (arrayConcatExpression != null) {
      return arrayConcatExpression.execute(iCurrentRecord, ctx);
    }
    if (json != null) {
      return json.toMap(iCurrentRecord, ctx);
    }
    if (booleanValue != null) {
      return booleanValue;
    }
    if (value instanceof PNumber) {
      return ((PNumber) value).getValue();//only for old executor (manually replaced params)
    }

    //from here it's old stuff, only for the old executor
    if (value instanceof Rid) {
      Rid v = (Rid) value;
      return new RID(ctx.getDatabase(), v.bucket.getValue().intValue(), v.position.getValue().longValue());
    } else if (value instanceof MathExpression) {
      return ((MathExpression) value).execute(iCurrentRecord, ctx);
    } else if (value instanceof ArrayConcatExpression) {
      return ((ArrayConcatExpression) value).execute(iCurrentRecord, ctx);
    } else if (value instanceof Json) {
      return ((Json) value).toMap(iCurrentRecord, ctx);
    } else if (value instanceof String) {
      return value;
    } else if (value instanceof java.lang.Number) {
      return value;
    }

    return value;
  }

  public boolean isBaseIdentifier() {
    if (mathExpression != null) {
      return mathExpression.isBaseIdentifier();
    }
    if (value instanceof MathExpression) {//only backward stuff, remote it
      return ((MathExpression) value).isBaseIdentifier();
    }

    return false;
  }

  public boolean isEarlyCalculated() {
    if (this.mathExpression != null) {
      return this.mathExpression.isEarlyCalculated();
    }
    if (this.arrayConcatExpression != null) {
      return this.arrayConcatExpression.isEarlyCalculated();
    }

    if (booleanValue != null) {
      return true;
    }
    if (value instanceof java.lang.Number) {
      return true;
    }
    if (value instanceof String) {
      return true;
    }
    if (value instanceof MathExpression) {
      return ((MathExpression) value).isEarlyCalculated();
    }

    return false;
  }

  public Identifier getDefaultAlias() {
    Identifier identifier;
    if (isBaseIdentifier()) {
      identifier = new Identifier(((BaseExpression) mathExpression).identifier.getSuffix().identifier.getStringValue());
    } else {
      identifier = new Identifier(this.toString());
    }
    return identifier;
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    //    if (value == null) {
    //      builder.append("null");
    //    } else if (value instanceof SimpleNode) {
    //      ((SimpleNode) value).toString(params, builder);
    //    } else if (value instanceof String) {
    //      if (Boolean.TRUE.equals(singleQuotes)) {
    //        builder.append("'" + value + "'");
    //      } else {
    //        builder.append("\"" + value + "\"");
    //      }
    //    } else {
    //      builder.append("" + value);
    //    }

    if (isNull) {
      builder.append("null");
    } else if (rid != null) {
      rid.toString(params, builder);
    } else if (mathExpression != null) {
      mathExpression.toString(params, builder);
    } else if (arrayConcatExpression != null) {
      arrayConcatExpression.toString(params, builder);
    } else if (json != null) {
      json.toString(params, builder);
    } else if (booleanValue != null) {
      builder.append(booleanValue);
    } else if (value instanceof SimpleNode) {
      ((SimpleNode) value).toString(params, builder);//only for translated input params, will disappear with new executor
    } else if (value instanceof String) {
      if (Boolean.TRUE.equals(singleQuotes)) {
        builder.append("'" + value + "'");
      } else {
        builder.append("\"" + value + "\"");
      }

    } else {
      builder.append("" + value);//only for translated input params, will disappear with new executor
    }
  }

  public static String encode(String s) {
    StringBuilder builder = new StringBuilder(s.length());
    for (char c : s.toCharArray()) {
      if (c == '\n') {
        builder.append("\\n");
        continue;
      }
      if (c == '\t') {
        builder.append("\\t");
        continue;
      }
      if (c == '\\' || c == '"') {
        builder.append("\\");
      }
      builder.append(c);
    }
    return builder.toString();
  }

  public boolean supportsBasicCalculation() {
    if (mathExpression != null) {
      return mathExpression.supportsBasicCalculation();
    }
    if (arrayConcatExpression != null) {
      return arrayConcatExpression.supportsBasicCalculation();
    }
    return true;
  }

  public boolean isIndexedFunctionCal() {
    if (mathExpression != null) {
      return mathExpression.isIndexedFunctionCall();
    }
    return false;
  }

  public static String encodeSingle(String s) {

    StringBuilder builder = new StringBuilder(s.length());
    for (char c : s.toCharArray()) {
      if (c == '\n') {
        builder.append("\\n");
        continue;
      }
      if (c == '\t') {
        builder.append("\\t");
        continue;
      }
      if (c == '\\' || c == '\'') {
        builder.append("\\");
      }
      builder.append(c);
    }
    return builder.toString();
  }

  public long estimateIndexedFunction(FromClause target, CommandContext context, BinaryCompareOperator operator, Object right) {
    if (mathExpression != null) {
      return mathExpression.estimateIndexedFunction(target, context, operator, right);
    }
    return -1;
  }

  public Iterable<Record> executeIndexedFunction(FromClause target, CommandContext context, BinaryCompareOperator operator, Object right) {
    if (mathExpression != null) {
      return mathExpression.executeIndexedFunction(target, context, operator, right);
    }
    return null;
  }

  /**
   * tests if current expression is an indexed function AND that function can also be executed without using the index
   *
   * @param target   the query target
   * @param context  the execution context
   * @param operator
   * @param right
   *
   * @return true if current expression is an indexed funciton AND that function can also be executed without using the index, false
   * otherwise
   */
  public boolean canExecuteIndexedFunctionWithoutIndex(FromClause target, CommandContext context, BinaryCompareOperator operator, Object right) {
    if (mathExpression != null) {
      return mathExpression.canExecuteIndexedFunctionWithoutIndex(target, context, operator, right);
    }
    return false;
  }

  /**
   * tests if current expression is an indexed function AND that function can be used on this target
   *
   * @param target   the query target
   * @param context  the execution context
   * @param operator
   * @param right
   *
   * @return true if current expression involves an indexed function AND that function can be used on this target, false otherwise
   */
  public boolean allowsIndexedFunctionExecutionOnTarget(FromClause target, CommandContext context, BinaryCompareOperator operator, Object right) {
    if (mathExpression != null) {
      return mathExpression.allowsIndexedFunctionExecutionOnTarget(target, context, operator, right);
    }
    return false;
  }

  /**
   * tests if current expression is an indexed function AND the function has also to be executed after the index search. In some
   * cases, the index search is accurate, so this condition can be excluded from further evaluation. In other cases the result from
   * the index is a superset of the expected result, so the function has to be executed anyway for further filtering
   *
   * @param target  the query target
   * @param context the execution context
   *
   * @return true if current expression involves an indexed function AND the function has also to be executed after the index
   * search.
   */
  public boolean executeIndexedFunctionAfterIndexSearch(FromClause target, CommandContext context, BinaryCompareOperator operator, Object right) {
    if (mathExpression != null) {
      return mathExpression.executeIndexedFunctionAfterIndexSearch(target, context, operator, right);
    }
    return false;
  }

  public boolean isExpand() {
    if (mathExpression != null) {
      return mathExpression.isExpand();
    }
    return false;
  }

  public Expression getExpandContent() {
    return mathExpression.getExpandContent();
  }

  public boolean needsAliases(Set<String> aliases) {
    if (mathExpression != null) {
      return mathExpression.needsAliases(aliases);
    }
    if (arrayConcatExpression != null) {
      return arrayConcatExpression.needsAliases(aliases);
    }
    if (json != null) {
      return json.needsAliases(aliases);
    }
    return false;
  }

  public boolean isAggregate() {
    if (mathExpression != null && mathExpression.isAggregate()) {
      return true;
    }
    if (arrayConcatExpression != null && arrayConcatExpression.isAggregate()) {
      return true;
    }
    return json != null && json.isAggregate();
  }

  public Expression splitForAggregation(AggregateProjectionSplit aggregateSplit) {
    if (isAggregate()) {
      Expression result = new Expression(-1);
      if (mathExpression != null) {
        SimpleNode splitResult = mathExpression.splitForAggregation(aggregateSplit);
        if (splitResult instanceof MathExpression) {
          result.mathExpression = (MathExpression) splitResult;
        } else if (splitResult instanceof Expression) {
          return (Expression) splitResult;
        } else {
          throw new IllegalStateException("something went wrong while splitting expression for aggregate " + this);
        }
      }
      if (arrayConcatExpression != null) {
        SimpleNode splitResult = arrayConcatExpression.splitForAggregation(aggregateSplit);
        if (splitResult instanceof ArrayConcatExpression) {
          result.arrayConcatExpression = (ArrayConcatExpression) splitResult;
        } else if (splitResult instanceof Expression) {
          return (Expression) splitResult;
        } else {
          throw new IllegalStateException("something went wrong while splitting expression for aggregate " + this);
        }
      }
      if (json != null) {
        result.json = json.splitForAggregation(aggregateSplit);
      }
      return result;
    } else {
      return this;
    }
  }

  public AggregationContext getAggregationContext(CommandContext ctx) {
    if (mathExpression != null) {
      return mathExpression.getAggregationContext(ctx);
    } else if (arrayConcatExpression != null) {
      return arrayConcatExpression.getAggregationContext(ctx);
    } else {
      throw new CommandExecutionException("Cannot aggregate on " + this);
    }
  }

  public Expression copy() {

    Expression result = new Expression(-1);
    result.singleQuotes = singleQuotes;
    result.doubleQuotes = doubleQuotes;
    result.isNull = isNull;
    result.rid = rid == null ? null : rid.copy();
    result.mathExpression = mathExpression == null ? null : mathExpression.copy();
    result.arrayConcatExpression = arrayConcatExpression == null ? null : arrayConcatExpression.copy();
    result.json = json == null ? null : json.copy();
    result.booleanValue = booleanValue;

    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Expression that = (Expression) o;

    if (isNull != that.isNull)
      return false;
    if (singleQuotes != null ? !singleQuotes.equals(that.singleQuotes) : that.singleQuotes != null)
      return false;
    if (doubleQuotes != null ? !doubleQuotes.equals(that.doubleQuotes) : that.doubleQuotes != null)
      return false;
    if (rid != null ? !rid.equals(that.rid) : that.rid != null)
      return false;
    if (mathExpression != null ? !mathExpression.equals(that.mathExpression) : that.mathExpression != null)
      return false;
    if (arrayConcatExpression != null ? !arrayConcatExpression.equals(that.arrayConcatExpression) : that.arrayConcatExpression != null)
      return false;
    if (json != null ? !json.equals(that.json) : that.json != null)
      return false;
    return booleanValue != null ? booleanValue.equals(that.booleanValue) : that.booleanValue == null;
  }

  @Override
  public int hashCode() {
    int result = singleQuotes != null ? singleQuotes.hashCode() : 0;
    result = 31 * result + (doubleQuotes != null ? doubleQuotes.hashCode() : 0);
    result = 31 * result + (isNull ? 1 : 0);
    result = 31 * result + (rid != null ? rid.hashCode() : 0);
    result = 31 * result + (mathExpression != null ? mathExpression.hashCode() : 0);
    result = 31 * result + (arrayConcatExpression != null ? arrayConcatExpression.hashCode() : 0);
    result = 31 * result + (json != null ? json.hashCode() : 0);
    result = 31 * result + (booleanValue != null ? booleanValue.hashCode() : 0);
    return result;
  }

  public void setMathExpression(MathExpression mathExpression) {
    this.mathExpression = mathExpression;
  }

  public void extractSubQueries(SubQueryCollector collector) {
    if (mathExpression != null) {
      mathExpression.extractSubQueries(collector);
    }
    if (arrayConcatExpression != null) {
      arrayConcatExpression.extractSubQueries(collector);
    }
    if (json != null) {
      json.extractSubQueries(collector);
    }
  }

  public void extractSubQueries(Identifier letAlias, SubQueryCollector collector) {
    if (mathExpression != null) {
      mathExpression.extractSubQueries(letAlias, collector);
    }
    if (arrayConcatExpression != null) {
      arrayConcatExpression.extractSubQueries(collector);
    }
    if (json != null) {
      json.extractSubQueries(collector);
    }
  }

  public boolean refersToParent() {
    if (mathExpression != null && mathExpression.refersToParent()) {
      return true;
    }
    if (arrayConcatExpression != null && arrayConcatExpression.refersToParent()) {
      return true;
    }
    return json != null && json.refersToParent();
  }

  public Rid getRid() {
    return rid;
  }

  public void setRid(Rid rid) {
    this.rid = rid;
  }

  public MathExpression getMathExpression() {
    return mathExpression;
  }

  /**
   * if the condition involved the current pattern (MATCH statement, eg. $matched.something = foo), returns the name of involved
   * pattern aliases ("something" in this case)
   *
   * @return a list of pattern aliases involved in this condition. Null it does not involve the pattern
   */
  List<String> getMatchPatternInvolvedAliases() {
    if (mathExpression != null)
      return mathExpression.getMatchPatternInvolvedAliases();
    if (arrayConcatExpression != null)
      return arrayConcatExpression.getMatchPatternInvolvedAliases();
    return null;
  }

  public void applyRemove(ResultInternal result, CommandContext ctx) {
    if (mathExpression != null) {
      mathExpression.applyRemove(result, ctx);
    } else {
      throw new CommandExecutionException("Cannot apply REMOVE " + this);
    }
  }

  public boolean isCount() {
    if (mathExpression == null) {
      return false;
    }
    return mathExpression.isCount();
  }

  public ArrayConcatExpression getArrayConcatExpression() {
    return arrayConcatExpression;
  }

  public void setArrayConcatExpression(ArrayConcatExpression arrayConcatExpression) {
    this.arrayConcatExpression = arrayConcatExpression;
  }

  public Result serialize() {
    ResultInternal result = new ResultInternal();
    result.setProperty("singleQuotes", singleQuotes);
    result.setProperty("doubleQuotes", doubleQuotes);
    result.setProperty("isNull", isNull);

    if (rid != null) {
      result.setProperty("rid", rid.serialize());
    }
    if (mathExpression != null) {
      result.setProperty("mathExpression", mathExpression.serialize());
    }
    if (arrayConcatExpression != null) {
      result.setProperty("arrayConcatExpression", arrayConcatExpression.serialize());
    }
    if (json != null) {
      result.setProperty("json", json.serialize());
    }
    result.setProperty("booleanValue", booleanValue);
    return result;
  }

  public void deserialize(Result fromResult) {
    singleQuotes = fromResult.getProperty("singleQuotes");
    doubleQuotes = fromResult.getProperty("doubleQuotes");
    isNull = fromResult.getProperty("isNull");

    if (fromResult.getProperty("rid") != null) {
      rid = new Rid(-1);
      rid.deserialize(fromResult.getProperty("rid"));
    }
    if (fromResult.getProperty("mathExpression") != null) {
      mathExpression = MathExpression.deserializeFromResult(fromResult.getProperty("mathExpression"));
    }
    if (fromResult.getProperty("arrayConcatExpression") != null) {
      arrayConcatExpression = new ArrayConcatExpression(-1);
      arrayConcatExpression.deserialize(fromResult.getProperty("arrayConcatExpression"));
    }
    if (fromResult.getProperty("json") != null) {
      json = new Json(-1);
      json.deserialize(fromResult.getProperty("json"));
    }
    booleanValue = fromResult.getProperty("booleanValue");
  }

  public boolean isDefinedFor(Result currentRecord) {
    if (mathExpression != null) {
      return mathExpression.isDefinedFor(currentRecord);
    } else {
      return true;
    }
  }

  public boolean isDefinedFor(Record currentRecord) {
    if (mathExpression != null) {
      return mathExpression.isDefinedFor(currentRecord);
    } else {
      return true;
    }
  }

  public boolean isCacheable() {
    if (mathExpression != null) {
      return mathExpression.isCacheable();
    }
    if (arrayConcatExpression != null) {
      return arrayConcatExpression.isCacheable();
    }
    if (json != null) {
      return json.isCacheable();
    }

    return true;
  }
}
/* JavaCC - OriginalChecksum=9c860224b121acdc89522ae97010be01 (do not edit this line) */
