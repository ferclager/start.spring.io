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

package io.spring.start.site.extension.dependency.okta;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.SupportedBootVersion;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OktaHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class OktaHelpDocumentCustomizerTests extends AbstractExtensionTests {

	private static final SupportedBootVersion BOOT_VERSION = SupportedBootVersion.V3_3;

	@Test
	void oktaSectionWithOktaDependencyIsPresent() {
		assertHelpDocument("okta").contains("## OAuth 2.0 and OIDC with Okta");
	}

	@Test
	void oktaSectionWithoutOktaDependencyIsMissing() {
		assertHelpDocument("web", "actuator").doesNotContain("## OAuth 2.0 and OIDC with Okta");
	}

	private TextAssert assertHelpDocument(String... dependencies) {
		ProjectRequest request = createProjectRequest(BOOT_VERSION, dependencies);
		return assertThat(helpDocument(request));
	}

}
