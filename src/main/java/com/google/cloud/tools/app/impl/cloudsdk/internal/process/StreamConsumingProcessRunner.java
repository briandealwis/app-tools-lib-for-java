/**
 * Copyright 2015 Google Inc. All Rights Reserved.
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
package com.google.cloud.tools.app.impl.cloudsdk.internal.process;

import com.google.cloud.tools.app.impl.cloudsdk.internal.process.io.StreamConsumer;

import java.io.IOException;

/**
 * Process Runner impl that allows for specialized stream consumption
 */
public class StreamConsumingProcessRunner implements ProcessRunner {

  private final StreamConsumer streamConsumer;

  public StreamConsumingProcessRunner(StreamConsumer streamConsumer) {
    this.streamConsumer = streamConsumer;
  }

  public int run(String[] command) throws ProcessRunnerException {
    final ProcessBuilder pb = new ProcessBuilder(command);
    pb.redirectErrorStream(true);

    try {
      final Process process = pb.start();

      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          if (process != null) {
            process.destroy();
          }
        }
      });

      streamConsumer.consumeStreams(process);
      return process.waitFor();

    } catch (IOException | InterruptedException e) {
      throw new ProcessRunnerException(e);
    } finally {
      streamConsumer.stop();
    }
  }
}