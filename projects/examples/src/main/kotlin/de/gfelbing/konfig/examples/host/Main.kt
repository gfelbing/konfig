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

package de.gfelbing.konfig.examples.host

import de.gfelbing.konfig.core.source.ChainedKonfiguration
import de.gfelbing.konfig.core.source.PropertiesFileKonfiguration

fun main() {
    // set the host name as system property
    System.setProperty("target.host", "examplehost")

    // find the configuration source for this host
    val hostName = System.getenv("HOSTNAME") ?: System.getProperty("target.host")
    val source = ChainedKonfiguration(
            PropertiesFileKonfiguration("host.$hostName.properties"),
            PropertiesFileKonfiguration("host.properties")
    )

    // load the config
    val config = HostBasedConfigDeclaration.load(source)
    println("Hello, $config!")
}

