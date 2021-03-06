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

package com.google.cloud.tools.app.api.module;

/**
 * Options for setting manager of applications deployed to App Engine flexible environment. See:
 * {@link AppEngineModuleService#setManagedBy(SetManagedByConfiguration)}
 */
public enum Manager {
  SELF("--self"),
  GOOGLE("--google");

  private String flagForm;

  Manager(String flagForm) {
    this.flagForm = flagForm;
  }

  public String getFlagForm() {
    return flagForm;
  }
}
