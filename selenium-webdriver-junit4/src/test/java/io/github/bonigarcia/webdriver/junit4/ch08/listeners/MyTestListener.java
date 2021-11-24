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
package io.github.bonigarcia.webdriver.junit4.ch08.listeners;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;

public class MyTestListener extends RunListener {

    static final Logger log = getLogger(lookup().lookupClass());

    @Override
    public void testSuiteStarted(Description description) throws Exception {
        super.testSuiteStarted(description);
        log.debug("testSuiteStarted {}", description.getDisplayName());
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
        log.debug("testRunStarted {}", description.getDisplayName());
    }

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);
        log.debug("testStarted {}", description.getDisplayName());
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
        log.debug("testIgnored {}", description.getDisplayName());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        log.debug("testFailure {} {}", failure.getException(),
                failure.getMessage());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        super.testAssumptionFailure(failure);
        log.debug("testAssumptionFailure {} {}", failure.getException(),
                failure.getMessage());

    }

    @Override
    public void testFinished(Description description) throws Exception {
        super.testFinished(description);
        log.debug("testFinished {}", description.getDisplayName());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        log.debug("testRunFinished - failures={}", result.getFailures());
    }

    @Override
    public void testSuiteFinished(Description description) throws Exception {
        super.testSuiteFinished(description);
        log.debug("testSuiteFinished {}", description.getDisplayName());
    }

}
