/**
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.app.api.genconfig;

import com.google.cloud.tools.app.api.AppEngineException;

/**
 * Utility for generating missing configuration files for a source directory.
 */
public interface GenConfigUtility {

  /**
   * Generating missing configuration files.
   *
   * @param config Parameters for generating the config files.
   * @throws AppEngineException Wraps any errors in the process
   */
  void genConfig(GenConfigParams config) throws AppEngineException;
}
