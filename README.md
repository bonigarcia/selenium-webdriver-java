[![Build Status](https://github.com/bonigarcia/selenium-webdriver-java/workflows/build/badge.svg)](https://github.com/bonigarcia/selenium-webdriver-java/actions)
[![badge-jdk](https://img.shields.io/badge/jdk-17-green.svg)](https://www.oracle.com/java/technologies/downloads/)
[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Support badge](https://img.shields.io/badge/stackoverflow-selenium_webdriver-green.svg?logo=stackoverflow)](https://stackoverflow.com/questions/tagged/selenium-webdriver)
[![Twitter Follow](https://img.shields.io/twitter/follow/boni_gg.svg?style=social)](https://twitter.com/boni_gg)

# Hands-On Selenium WebDriver with Java [![][Logo]][GitHub Repository]

This repository contains a comprehensive collection of examples about [Selenium] 4 using [Java] as language binding. These examples are explained in the O'Reilly book [Hands-On Selenium WebDriver with Java].

[![][Cover]][Hands-On Selenium WebDriver with Java]

This repo has been implemented as a multi-module project (using [Maven] and [Gradle] as build tools) composed of tests based on different frameworks: [JUnit 4], [JUnit 5] (alone or extended with [Selenium-Jupiter]), and [TestNG].

## Table of contents

[Hands-On Selenium WebDriver with Java] is organized as follows. All the examples presented in this book are available in this repo, organized by packages corresponding to each chapter.

Part I. Introduction
1. A Primer on Selenium
2. Preparing for Testing

Part II. The Selenium WebDriver API
3. WebDriver Fundamentals
4. Browser-Agnostic Features
5. Browser-Specific Manipulation
6. Remote WebDriver

Part III. Advanced Concepts
7. The Page Object Model (POM)
8. Testing Framework Specifics
9. Third-Party Integrations
10. Beyond Selenium

## Practice site

This repo also contains a [Practice site], i.e., a representative set of sample web pages used as the system under test (SUT) in the Selenium WebDriver test examples. This site is hosted using [GitHub Pages].

## Tags

This repo uses Git tags to track the evolution of the codebase in time. These tags are the following:

* `1.0.0`: The examples of the first version of the book (released in April 2022) are based on this tag.
* `1.1.0`: Bump to Java 11, due to the incompatibility of TestNG 7.6.0 (released on May 18, 2022) with Java 8.
* `1.2.0`: Remove GitHub token from the workflow setup, not required anymore since WebDriverManager 5.3.0 (released on August 21, 2022).
* `1.3.0`: Bump to Java 17, due to the incompatibility of Spring Boot 3.0.0 (released on Nov 24, 2022) with Java 11.

## About

selenium-webdriver-java (Copyright &copy; 2021-2025) is an open-source project created and maintained by [Boni Garcia], licensed under the terms of [Apache 2.0 License].

[Logo]: https://bonigarcia.dev/selenium-webdriver-java/img/hands-on-icon.png
[GitHub Repository]: https://github.com/bonigarcia/selenium-webdriver-java/
[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[Boni Garcia]: https://bonigarcia.dev/
[Gradle]: https://gradle.org/
[Java]: https://www.java.com/
[JUnit 4]: https://junit.org/junit4/
[JUnit 5]: https://junit.org/junit5/docs/current/user-guide/
[Maven]: https://maven.apache.org/
[Selenium]: https://www.selenium.dev/
[Selenium-Jupiter]: https://bonigarcia.dev/selenium-jupiter/
[TestNG]: https://testng.org/doc/
[Hands-On Selenium WebDriver with Java]: https://oreil.ly/1E7CX
[Cover]: https://bonigarcia.dev/img/hands-on-selenium-webdriver-java.png
[Practice site]: https://bonigarcia.dev/selenium-webdriver-java/
[GitHub Pages]: https://pages.github.com/
