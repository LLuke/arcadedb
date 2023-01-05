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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Created by luigidellaquila on 04/11/16.
 */
public class ResultSetTest {
  @Test
  public void testResultStream() {
    final InternalResultSet rs = new InternalResultSet();
    for (int i = 0; i < 10; i++) {
      final ResultInternal item = new ResultInternal();
      item.setProperty("i", i);
      rs.add(item);
    }
    final Optional<Integer> result = rs.stream().map(x -> (int) x.getProperty("i")).reduce((a, b) -> a + b);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(45, result.get().intValue());
  }

  @Test
  public void testResultEmptyVertexStream() {
    final InternalResultSet rs = new InternalResultSet();
    for (int i = 0; i < 10; i++) {
      final ResultInternal item = new ResultInternal();
      item.setProperty("i", i);
      rs.add(item);
    }
    final Optional<Integer> result = rs.vertexStream().map(x -> (int) x.get("i")).reduce((a, b) -> a + b);
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  public void testResultEdgeVertexStream() {
    final InternalResultSet rs = new InternalResultSet();
    for (int i = 0; i < 10; i++) {
      final ResultInternal item = new ResultInternal();
      item.setProperty("i", i);
      rs.add(item);
    }
    final Optional<Integer> result = rs.vertexStream().map(x -> (int) x.get("i")).reduce((a, b) -> a + b);
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  public void testResultConversion() {
    final ResultInternal item = new ResultInternal();
    item.setProperty("int", 10);
    item.setProperty("long", 10L);
    item.setProperty("short", (short) 10);

    Assertions.assertEquals(10, (int) item.getProperty("int", 10));
    Assertions.assertEquals(10L, (int) item.getProperty("int", 10L));

    Assertions.assertEquals(10L, (long) item.getProperty("long", 10));
    Assertions.assertEquals(10L, (long) item.getProperty("long", 10L));

    Assertions.assertEquals(10, (int) item.getProperty("absent", 10));
    Assertions.assertEquals(10L, (long) item.getProperty("absent", 10L));
  }
}
