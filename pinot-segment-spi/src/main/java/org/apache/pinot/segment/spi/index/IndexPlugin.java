/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.pinot.segment.spi.index;

/**
 * IndexPlugins are the way {@link IndexType}s are registered in a {@link IndexService}.
 *
 * In order to create an IndexService, a set of IndexPlugin must be provided. Although IndexTypes could be directly
 * added into a IndexService, this indirection is used to decouple the way indexes are discovered
 * (usually by {@link java.util.ServiceLoader} services) and the actual implementation.
 *
 * In order to mark a class as a {@link java.util.ServiceLoader} service, some metadata has to be added. Java modules
 * define a typesafe way to define services, but given that Pinot does not use them right now, the easier way to create
 * these services is by using Google AutoService. BloomIndexPlugin can be used as example.
 */
public interface IndexPlugin<T extends IndexType<?, ?, ?>> {
  int DEFAULT_PRIORITY = 0;
  T getIndexType();

  /**
   * Returns the priority of this plugin.
   *
   * Two plugins with the same id and different priorities will be loaded in the order of their priority. Higher
   * priority values are loaded first. This is useful when two plugins implement the same index type, but one of them
   * is a more optimized version of the other. In that case, the more optimized version should have a higher priority.
   *
   * Default priority is 0, which means that custom plugins that want to override default plugins need to use a
   * positive priority.
   */
  default int getPriority() {
    return DEFAULT_PRIORITY;
  }
}
