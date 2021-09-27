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
/* Generated By:JJTree: Do not edit this line. ONotInCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;

import java.util.*;

public class NotInCondition extends BooleanExpression {

  protected Expression            left;
  protected BinaryCompareOperator operator;
  protected SelectStatement       rightStatement;

  protected Object         right;
  protected InputParameter rightParam;
  protected MathExpression rightMathExpression;

  private static final Object UNSET           = new Object();
  private final        Object inputFinalValue = UNSET;

  public NotInCondition(int id) {
    super(id);
  }

  public NotInCondition(SqlParser p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(SqlParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public boolean evaluate(Identifiable currentRecord, CommandContext ctx) {
    Object leftVal = left.execute(currentRecord, ctx);
    Object rightVal = null;
    if (rightStatement != null) {
      rightVal = InCondition.executeQuery(rightStatement, ctx);
    } else if (rightParam != null) {
      rightVal = rightParam.getValue(ctx.getInputParameters());
    } else if (rightMathExpression != null) {
      rightVal = rightMathExpression.execute(currentRecord, ctx);
    }
    if (rightVal == null) {
      return true;
    }
    return !InCondition.evaluateExpression(leftVal, rightVal);
  }

  @Override
  public boolean evaluate(Result currentRecord, CommandContext ctx) {
    Object leftVal = left.execute(currentRecord, ctx);
    Object rightVal = null;
    if (rightStatement != null) {
      rightVal = InCondition.executeQuery(rightStatement, ctx);
    } else if (rightParam != null) {
      rightVal = rightParam.getValue(ctx.getInputParameters());
    } else if (rightMathExpression != null) {
      rightVal = rightMathExpression.execute(currentRecord, ctx);
    }
    if (rightVal == null) {
      return true;
    }
    return !InCondition.evaluateExpression(leftVal, rightVal);
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {

    left.toString(params, builder);
    builder.append(" NOT IN ");
    if (rightStatement != null) {
      builder.append("(");
      rightStatement.toString(params, builder);
      builder.append(")");
    } else if (right != null) {
      builder.append(convertToString(right));
    } else if (rightParam != null) {
      rightParam.toString(params, builder);
    } else if (rightMathExpression != null) {
      rightMathExpression.toString(params, builder);
    }
  }

  private String convertToString(Object o) {
    if (o instanceof String) {
      return "\"" + ((String) o).replaceAll("\"", "\\\"") + "\"";
    }
    return o.toString();
  }

  @Override
  public boolean supportsBasicCalculation() {

    if (operator != null && !operator.supportsBasicCalculation()) {
      return false;
    }
    if (left != null && !left.supportsBasicCalculation()) {
      return false;
    }
    return rightMathExpression == null || rightMathExpression.supportsBasicCalculation();

  }

  @Override
  protected int getNumberOfExternalCalculations() {
    int total = 0;
    if (operator != null && !operator.supportsBasicCalculation()) {
      total++;
    }
    if (left != null && !left.supportsBasicCalculation()) {
      total++;
    }
    if (rightMathExpression != null && !rightMathExpression.supportsBasicCalculation()) {
      total++;
    }
    return total;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    List<Object> result = new ArrayList<Object>();
    if (operator != null && !operator.supportsBasicCalculation()) {
      result.add(this);
    }
    if (rightMathExpression != null && !rightMathExpression.supportsBasicCalculation()) {
      result.add(rightMathExpression);
    }
    return result;
  }

  @Override
  public boolean needsAliases(Set<String> aliases) {
    if (left.needsAliases(aliases)) {
      return true;
    }

    return rightMathExpression != null && rightMathExpression.needsAliases(aliases);
  }

  @Override
  public NotInCondition copy() {
    NotInCondition result = new NotInCondition(-1);
    result.operator = operator == null ? null : operator.copy();
    result.left = left == null ? null : left.copy();
    result.rightMathExpression = rightMathExpression == null ? null : rightMathExpression.copy();
    result.rightStatement = rightStatement == null ? null : rightStatement.copy();
    result.rightParam = rightParam == null ? null : rightParam.copy();
    result.right = right;
    return result;
  }

  @Override
  public void extractSubQueries(SubQueryCollector collector) {
    if (left != null) {
      left.extractSubQueries(collector);
    }

    if (rightMathExpression != null) {
      rightMathExpression.extractSubQueries(collector);
    } else if (rightStatement != null) {
      Identifier alias = collector.addStatement(rightStatement);
      rightMathExpression = new BaseExpression(alias);
      rightStatement = null;
    }
  }

  @Override
  public boolean refersToParent() {
    if (left != null && left.refersToParent()) {
      return true;
    }
    if (rightStatement != null && rightStatement.refersToParent()) {
      return true;
    }
    return rightMathExpression != null && rightMathExpression.refersToParent();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    NotInCondition that = (NotInCondition) o;

    if (left != null ? !left.equals(that.left) : that.left != null)
      return false;
    if (operator != null ? !operator.equals(that.operator) : that.operator != null)
      return false;
    if (rightStatement != null ? !rightStatement.equals(that.rightStatement) : that.rightStatement != null)
      return false;
    if (right != null ? !right.equals(that.right) : that.right != null)
      return false;
    if (rightParam != null ? !rightParam.equals(that.rightParam) : that.rightParam != null)
      return false;
    if (rightMathExpression != null ? !rightMathExpression.equals(that.rightMathExpression) : that.rightMathExpression != null)
      return false;
    return inputFinalValue != null ? inputFinalValue.equals(that.inputFinalValue) : that.inputFinalValue == null;
  }

  @Override
  public int hashCode() {
    int result = left != null ? left.hashCode() : 0;
    result = 31 * result + (operator != null ? operator.hashCode() : 0);
    result = 31 * result + (rightStatement != null ? rightStatement.hashCode() : 0);
    result = 31 * result + (right != null ? right.hashCode() : 0);
    result = 31 * result + (rightParam != null ? rightParam.hashCode() : 0);
    result = 31 * result + (rightMathExpression != null ? rightMathExpression.hashCode() : 0);
    result = 31 * result + (inputFinalValue != null ? inputFinalValue.hashCode() : 0);
    return result;
  }

  @Override
  public List<String> getMatchPatternInvolvedAliases() {
    List<String> leftX = left == null ? null : left.getMatchPatternInvolvedAliases();
    List<String> rightX = rightMathExpression == null ? null : rightMathExpression.getMatchPatternInvolvedAliases();

    List<String> result = new ArrayList<String>();
    if (leftX != null) {
      result.addAll(leftX);
    }
    if (rightX != null) {
      result.addAll(rightX);
    }

    return result.isEmpty() ? null : result;
  }

  @Override
  public boolean isCacheable() {
    if (left != null && !left.isCacheable()) {
      return false;
    }

    if (rightStatement != null && !rightStatement.executionPlanCanBeCached()) {
      return false;
    }

    return rightMathExpression == null || rightMathExpression.isCacheable();
  }

}
/* JavaCC - OriginalChecksum=8fb82bf72cc7d9cbdf2f9e2323ca8ee1 (do not edit this line) */
