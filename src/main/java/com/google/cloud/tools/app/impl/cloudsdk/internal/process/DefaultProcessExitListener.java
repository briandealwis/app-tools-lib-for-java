/*
 * Copyright 2016 Google Inc.
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

package com.google.cloud.tools.app.impl.cloudsdk.internal.process;

/**
 * Default process exit listener that simply captures the exit code and makes it available with a
 * getter. Before the process exit code is captured, it's initialized to -1.
 */
public class DefaultProcessExitListener implements ProcessExitListener {
  private int exitCode = -1;

  @Override
  public void exit(int exitCode) {
    exitCode = exitCode;
  }

  /**
   * @return The captures process exit code.
   */
  public int getExitCode() {
    return exitCode;
  }
}
