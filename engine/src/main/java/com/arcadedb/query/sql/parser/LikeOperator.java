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
/* Generated By:JJTree: Do not edit this line. OLikeOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.DatabaseInternal;
import com.arcadedb.query.sql.executor.MultiValue;
import com.arcadedb.query.sql.executor.QueryHelper;

public class LikeOperator extends SimpleNode implements BinaryCompareOperator {
  public LikeOperator(final int id) {
    super(id);
  }

  public LikeOperator(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public boolean execute(final DatabaseInternal database, final Object iLeft, final Object iRight) {
    if (MultiValue.isMultiValue(iLeft) || MultiValue.isMultiValue(iRight))
      return false;

    if (iLeft == null || iRight == null) {
      return false;
    }
    return QueryHelper.like(iLeft.toString(), iRight.toString());
  }

  @Override
  public String toString() {
    return "LIKE";
  }

  @Override
  public boolean supportsBasicCalculation() {
    return true;
  }

  @Override
  public LikeOperator copy() {
    return this;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj != null && obj.getClass().equals(this.getClass());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
/* JavaCC - OriginalChecksum=16d302abf0f85b404e57b964606952ca (do not edit this line) */
