/*
 * (C) Copyright 2021 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.webdriver.jupiter.ch8.listeners;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.slf4j.Logger;

public class MyTestListener implements BeforeEachCallback, AfterEachCallback,
        TestExecutionExceptionHandler {

    static final Logger log = getLogger(lookup().lookupClass());

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        log.debug("afterEach {}", context.getDisplayName());
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context,
            Throwable throwable) throws Throwable {
        log.debug("handleTestExecutionException {}", throwable);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        log.debug("beforeEach {}", context.getDisplayName());
    }

}
