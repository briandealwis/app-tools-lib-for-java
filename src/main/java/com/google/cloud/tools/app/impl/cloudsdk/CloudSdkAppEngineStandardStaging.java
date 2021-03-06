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

package com.google.cloud.tools.app.impl.cloudsdk;

import com.google.cloud.tools.app.api.AppEngineException;
import com.google.cloud.tools.app.api.deploy.AppEngineStandardStaging;
import com.google.cloud.tools.app.api.deploy.StageStandardConfiguration;
import com.google.cloud.tools.app.impl.cloudsdk.internal.process.ProcessRunnerException;
import com.google.cloud.tools.app.impl.cloudsdk.internal.sdk.CloudSdk;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link AppEngineStandardStaging} that uses App Engine SDK bundled with the
 * Cloud SDK.
 */
public class CloudSdkAppEngineStandardStaging implements AppEngineStandardStaging {

  private CloudSdk cloudSdk;

  public CloudSdkAppEngineStandardStaging(
      CloudSdk cloudSdk) {
    this.cloudSdk = cloudSdk;
  }

  @Override
  public void stageStandard(StageStandardConfiguration configuration) throws AppEngineException {
    Preconditions.checkNotNull(configuration);
    Preconditions.checkNotNull(configuration.getSourceDirectory());
    Preconditions.checkNotNull(configuration.getStagingDirectory());
    Preconditions.checkNotNull(cloudSdk);

    List<String> arguments = new ArrayList<>();
    if (configuration.isEnableQuickstart()) {
      arguments.add("--enable_quickstart");
    }
    if (configuration.isDisableUpdateCheck()) {
      arguments.add("--disable_update_check");
    }
    if (configuration.isEnableJarSplitting()) {
      arguments.add("--enable_jar_splitting");
    }
    if (!Strings.isNullOrEmpty(configuration.getJarSplittingExcludes())) {
      arguments.add("--jar_splitting_excludes=" + configuration.getJarSplittingExcludes());
    }
    if (!Strings.isNullOrEmpty(configuration.getCompileEncoding())) {
      arguments.add("--compile_encoding=" + configuration.getCompileEncoding());
    }
    if (configuration.isDeleteJsps()) {
      arguments.add("--delete_jsps");
    }
    if (configuration.isEnableJarClasses()) {
      arguments.add("--enable_jar_classes");
    }

    arguments.add("stage");
    arguments.add(configuration.getSourceDirectory().toPath().toString());
    arguments.add(configuration.getStagingDirectory().toPath().toString());

    Path dockerfile =
        configuration.getDockerfile() == null ? null : configuration.getDockerfile().toPath();
    Path dockerfileDestination = configuration.getStagingDirectory().toPath();

    try {

      cloudSdk.runAppCfgCommand(arguments);

      if (dockerfile != null) {
        Files.copy(dockerfile, dockerfileDestination, StandardCopyOption.REPLACE_EXISTING);
      }

    } catch (IOException | ProcessRunnerException e) {
      throw new AppEngineException(e);
    }

  }

}
