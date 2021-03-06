/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.integtests.tooling.r56;

import org.gradle.api.Action;
import org.gradle.tooling.BuildAction;
import org.gradle.tooling.BuildController;
import org.gradle.tooling.model.eclipse.EclipseRuntime;
import org.gradle.tooling.model.eclipse.EclipseWorkspace;
import org.gradle.tooling.model.eclipse.RunClosedProjectBuildDependencies;

import java.io.Serializable;

public class TellGradleToRunBuildDependencyTask implements BuildAction<Void>, Serializable {

    private final EclipseWorkspace workspace;

    public TellGradleToRunBuildDependencyTask(EclipseWorkspace workspace) {

        this.workspace = workspace;
    }

    @Override
    public Void execute(BuildController controller) {
        if (workspace != null) {
            controller.getModel(RunClosedProjectBuildDependencies.class, EclipseRuntime.class, new EclipseRuntimeAction(workspace));
        } else {
            controller.getModel(RunClosedProjectBuildDependencies.class);
        }
        return null;
    }

    private static class EclipseRuntimeAction implements Action<EclipseRuntime>, Serializable {
        private final EclipseWorkspace workspace;

        public EclipseRuntimeAction(EclipseWorkspace workspace) {

            this.workspace = workspace;
        }

        @Override
        public void execute(EclipseRuntime eclipseRuntime) {
            eclipseRuntime.setWorkspace(workspace);
        }
    }

}
