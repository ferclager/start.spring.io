/*
 * Copyright 2012 - present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension.dependency.springazure;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.SupportedBootVersion;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringAzureDockerComposeProjectGenerationConfiguration}.
 *
 * @author Eddú Meléndez
 */
class SpringAzureDockerComposeProjectGenerationConfigurationTests extends AbstractExtensionTests {

	private static final SupportedBootVersion BOOT_VERSION = SupportedBootVersion.V3_4;

	@Test
	void doesNothingWithoutDockerCompose() {
		ProjectRequest request = createProjectRequest(BOOT_VERSION, "web", "azure-storage");
		ProjectStructure structure = generateProject(request);
		assertThat(structure.getProjectDirectory().resolve("compose.yaml")).doesNotExist();
	}

	@Test
	void createsAzuriteService() {
		ProjectRequest request = createProjectRequest(BOOT_VERSION, "docker-compose", "azure-storage");
		assertThat(composeFile(request)).hasSameContentAs(new ClassPathResource("compose/azurite.yaml"));
	}

	@Test
	void springAzureDockerComposeDependencyIsAdded() {
		ProjectRequest projectRequest = createProjectRequest(BOOT_VERSION, "docker-compose", "azure-storage");
		assertThat(mavenPom(projectRequest)).hasDependency("com.azure.spring", "spring-cloud-azure-docker-compose",
				null, "runtime");
	}

	@Test
	void shouldNotAddDockerComposeTestcontainersDependencyIfNoSpringAzureDependencyIsSelected() {
		ProjectRequest projectRequest = createProjectRequest("docker-compose", "web");
		assertThat(mavenPom(projectRequest)).doesNotHaveDependency("com.azure.spring",
				"spring-cloud-azure-docker-compose");
	}

}
