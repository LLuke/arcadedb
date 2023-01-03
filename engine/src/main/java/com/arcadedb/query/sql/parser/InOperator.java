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
/* Generated By:JJTree: Do not edit this line. OInOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.DatabaseInternal;

import java.util.*;

public class InOperator extends SimpleNode implements BinaryCompareOperator {
  public InOperator(final int id) {
    super(id);
  }

  public InOperator(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public boolean execute(final DatabaseInternal database, Object left, Object right) {
    if (left == null) {
      return false;
    }
    if (right instanceof Collection) {
      if (left instanceof Collection) {
        return ((Collection) right).containsAll((Collection) left);
      }
      if (left instanceof Iterable) {
        left = ((Iterable) left).iterator();
      }
      if (left instanceof Iterator) {
        final Iterator iterator = (Iterator) left;
        while (iterator.hasNext()) {
          final Object next = iterator.next();
          if (!((Collection) right).contains(next)) {
            return false;
          }
        }
      }
      return ((Collection) right).contains(left);
    }
    if (right instanceof Iterable) {
      right = ((Iterable) right).iterator();
    }
    if (right instanceof Iterator) {
      if (left instanceof Iterable) {
        left = ((Iterable) left).iterator();
      }
      final Iterator leftIterator = (Iterator) left;
      final Iterator rightIterator = (Iterator) right;
      while (leftIterator.hasNext()) {
        final Object leftItem = leftIterator.next();
        boolean found = false;
        while (rightIterator.hasNext()) {
          final Object rightItem = rightIterator.next();
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
  public boolean supportsBasicCalculation() {
    return true;
  }

  @Override
  public InOperator copy() {
    return this;
  }

  @Override
  public boolean equals( final Object obj) {
    return obj != null && obj.getClass().equals(this.getClass());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
/* JavaCC - OriginalChecksum=6650a720cb942fa3c4d588ff0f381b3a (do not edit this line) */
