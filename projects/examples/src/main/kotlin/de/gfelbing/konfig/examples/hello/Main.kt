/*
 * Copyright (c) 2019 Georg Felbinger
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

package de.gfelbing.konfig.examples.hello

import de.gfelbing.konfig.core.source.ChainedKonfiguration
import de.gfelbing.konfig.core.source.EnvironmentKonfiguration
import de.gfelbing.konfig.core.source.PropertiesFileKonfiguration
import de.gfelbing.konfig.core.source.SystemPropertiesKonfiguration

fun main() {
    // set some system properties
    System.setProperty("required.string", "some-string")
    System.setProperty("required.long", "123456789")
    System.setProperty("required.double", "12345.6789")

    // define the source
    val source = ChainedKonfiguration(
            // use environment config as first source
            EnvironmentKonfiguration(),
            // fall back to system properties
            SystemPropertiesKonfiguration(),
            // fall back to properties file
            PropertiesFileKonfiguration("hello.properties")
    )

    // load the config and print result
    val config = MyConfigDeclaration.load(source)
    println("Hello, $config!")
}