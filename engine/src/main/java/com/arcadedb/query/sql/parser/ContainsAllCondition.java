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
/* Generated By:JJTree: Do not edit this line. OContainsAllCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.MultiValue;
import com.arcadedb.query.sql.executor.Result;

import java.util.*;

public class ContainsAllCondition extends BooleanExpression {

  protected Expression left;

  protected Expression right;

  protected OrBlock rightBlock;

  public ContainsAllCondition(final int id) {
    super(id);
  }

  public ContainsAllCondition(final SqlParser p, final int id) {
    super(p, id);
  }

  public boolean execute(Object left, Object right) {
    if (left instanceof Collection) {
      if (right instanceof Collection) {
        return ((Collection) left).containsAll((Collection) right);
      }
      if (right instanceof Iterable) {
        right = ((Iterable) right).iterator();
      }
      if (right instanceof Iterator) {
        final Iterator iterator = (Iterator) right;
        while (iterator.hasNext()) {
          final Object next = iterator.next();
          if (!((Collection) left).contains(next)) {
            return false;
          }
        }
      }
      return ((Collection) left).contains(right);
    }
    if (left instanceof Iterable) {
      left = ((Iterable) left).iterator();
    }
    if (left instanceof Iterator) {
      if (!(right instanceof Iterable)) {
        right = Collections.singleton(right);
      }
      right = ((Iterable) right).iterator();

      final Iterator leftIterator = (Iterator) left;
      final Iterator rightIterator = (Iterator) right;
      while (rightIterator.hasNext()) {
        final Object leftItem = rightIterator.next();
        boolean found = false;
        while (leftIterator.hasNext()) {
          final Object rightItem = leftIterator.next();
          if (leftItem != null && leftItem.equals(rightItem)) {
            found = true;
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean evaluate(final Identifiable currentRecord, final CommandContext ctx) {
    final Object leftValue = left.execute(currentRecord, ctx);
    if (right != null) {
      final Object rightValue = right.execute(currentRecord, ctx);
      return execute(leftValue, rightValue);
    } else {
      if (!MultiValue.isMultiValue(leftValue)) {
        return false;
      }
      final Iterator<Object> iter = MultiValue.getMultiValueIterator(leftValue);
      while (iter.hasNext()) {
        final Object item = iter.next();
        if (item instanceof Identifiable) {
          if (!rightBlock.evaluate((Identifiable) item, ctx)) {
            return false;
          }
        } else if (item instanceof Result) {
          if (!rightBlock.evaluate((Result) item, ctx)) {
            return false;
          }
        } else {
          return false;
        }
      }
      return true;
    }
  }

  @Override
  public boolean evaluate(final Result currentRecord, final CommandContext ctx) {
    final Object leftValue = left.execute(currentRecord, ctx);
    if (right != null) {
      final Object rightValue = right.execute(currentRecord, ctx);
      return execute(leftValue, rightValue);
    } else {
      if (!MultiValue.isMultiValue(leftValue)) {
        return false;
      }
      final Iterator<Object> iter = MultiValue.getMultiValueIterator(leftValue);
      while (iter.hasNext()) {
        final Object item = iter.next();
        if (item instanceof Identifiable) {
          if (!rightBlock.evaluate((Identifiable) item, ctx)) {
            return false;
          }
        } else if (item instanceof Result) {
          if (!rightBlock.evaluate((Result) item, ctx)) {
            return false;
          }
        } else {
          return false;
        }
      }
      return true;
    }

  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    left.toString(params, builder);
    builder.append(" CONTAINSALL ");
    if (right != null) {
      right.toString(params, builder);
    } else if (rightBlock != null) {
      builder.append("(");
      rightBlock.toString(params, builder);
      builder.append(")");
    }
  }

  public Expression getLeft() {
    return left;
  }

  public void setLeft(final Expression left) {
    this.left = left;
  }

  public Expression getRight() {
    return right;
  }

  public void setRight(final Expression right) {
    this.right = right;
  }

  @Override
  public boolean supportsBasicCalculation() {
    if (left != null && !left.supportsBasicCalculation()) {
      return false;
    }
    if (right != null && !right.supportsBasicCalculation()) {
      return false;
    }
    return rightBlock == null || rightBlock.supportsBasicCalculation();
  }

  @Override
  protected int getNumberOfExternalCalculations() {
    int total = 0;
    if (left != null && !left.supportsBasicCalculation()) {
      total++;
    }
    if (right != null && !right.supportsBasicCalculation()) {
      total++;
    }
    if (rightBlock != null && !rightBlock.supportsBasicCalculation()) {
      total++;
    }
    return total;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    final List<Object> result = new ArrayList<Object>();
    if (left != null && !left.supportsBasicCalculation()) {
      result.add(left);
    }
    if (right != null && !right.supportsBasicCalculation()) {
      result.add(right);
    }
    if (rightBlock != null) {
      result.addAll(rightBlock.getExternalCalculationConditions());
    }
    return result;
  }

  @Override
  public boolean needsAliases(final Set<String> aliases) {
    if (left.needsAliases(aliases)) {
      return true;
    }

    if (right != null && right.needsAliases(aliases)) {
      return true;
    }
    return rightBlock != null && rightBlock.needsAliases(aliases);
  }

  @Override
  public ContainsAllCondition copy() {
    final ContainsAllCondition result = new ContainsAllCondition(-1);
    result.left = left.copy();
    result.right = right == null ? null : right.copy();
    result.rightBlock = rightBlock == null ? null : rightBlock.copy();
    return result;
  }

  @Override
  public void extractSubQueries(final SubQueryCollector collector) {
    left.extractSubQueries(collector);
    if (right != null) {
      right.extractSubQueries(collector);
    }
    if (rightBlock != null) {
      rightBlock.extractSubQueries(collector);
    }
  }

  @Override
  public boolean refersToParent() {
    if (left != null && left.refersToParent()) {
      return true;
    }
    if (right != null && right.refersToParent()) {
      return true;
    }
    return rightBlock != null && rightBlock.refersToParent();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final ContainsAllCondition that = (ContainsAllCondition) o;

    if (!Objects.equals(left, that.left))
      return false;
    if (!Objects.equals(right, that.right))
      return false;
    return Objects.equals(rightBlock, that.rightBlock);
  }

  @Override
  public int hashCode() {
    int result = left != null ? left.hashCode() : 0;
    result = 31 * result + (right != null ? right.hashCode() : 0);
    result = 31 * result + (rightBlock != null ? rightBlock.hashCode() : 0);
    return result;
  }

  @Override
  public List<String> getMatchPatternInvolvedAliases() {
    final List<String> leftX = left == null ? null : left.getMatchPatternInvolvedAliases();
    final List<String> rightX = right == null ? null : right.getMatchPatternInvolvedAliases();
    final List<String> rightBlockX = rightBlock == null ? null : rightBlock.getMatchPatternInvolvedAliases();

    final List<String> result = new ArrayList<String>();
    if (leftX != null) {
      result.addAll(leftX);
    }
    if (rightX != null) {
      result.addAll(rightX);
    }
    if (rightBlockX != null) {
      result.addAll(rightBlockX);
    }

    return result.isEmpty() ? null : result;
  }

  @Override
  public boolean isCacheable() {
    if (left != null && !left.isCacheable()) {
      return false;
    }

    if (right != null && !right.isCacheable()) {
      return false;
    }

    return rightBlock == null || rightBlock.isCacheable();
  }
}
/* JavaCC - OriginalChecksum=ab7b4e192a01cda09a82d5b80ef4ec60 (do not edit this line) */
