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
package com.arcadedb.query.sql.executor;

import com.arcadedb.exception.TimeoutException;
import com.arcadedb.query.sql.parser.Projection;

/**
 * Created by luigidellaquila on 12/07/16.
 */
public class ProjectionCalculationStep extends AbstractExecutionStep {
  protected final Projection projection;
  protected       long       cost = 0;

  public ProjectionCalculationStep(final Projection projection, final CommandContext ctx, final boolean profilingEnabled) {
    super(ctx, profilingEnabled);
    this.projection = projection;
  }

  @Override
  public ResultSet syncPull(final CommandContext ctx, final int nRecords) throws TimeoutException {
    if (prev.isEmpty()) {
      throw new IllegalStateException("Cannot calculate projections without a previous source");
    }

    final ResultSet parentRs = prev.get().syncPull(ctx, nRecords);
    return new ResultSet() {
      @Override
      public boolean hasNext() {
        return parentRs.hasNext();
      }

      @Override
      public Result next() {
        final Result item = parentRs.next();
        final Object oldCurrent = ctx.getVariable("current");
        ctx.setVariable("current", item);
        final Result result = calculateProjections(ctx, item);
        ctx.setVariable("current", oldCurrent);
        return result;
      }

      @Override
      public void close() {
        parentRs.close();
      }
    };
  }

  private Result calculateProjections(final CommandContext ctx, final Result next) {
    final long begin = profilingEnabled ? System.nanoTime() : 0;
    try {
      return this.projection.calculateSingle(ctx, next);
    } finally {
      if (profilingEnabled) {
        cost += (System.nanoTime() - begin);
      }
    }
  }

  @Override
  public String prettyPrint(final int depth, final int indent) {
    final String spaces = ExecutionStepInternal.getIndent(depth, indent);
    String result = spaces + "+ CALCULATE PROJECTIONS";
    if (profilingEnabled) {
      result += " (" + getCostFormatted() + ")";
    }
    result += ("\n" + spaces + "  " + projection.toString() + "");
    return result;
  }

  @Override
  public long getCost() {
    return cost;
  }

  @Override
  public boolean canBeCached() {
    return true;
  }

  @Override
  public ExecutionStep copy(final CommandContext ctx) {
    return new ProjectionCalculationStep(projection.copy(), ctx, profilingEnabled);
  }
}
