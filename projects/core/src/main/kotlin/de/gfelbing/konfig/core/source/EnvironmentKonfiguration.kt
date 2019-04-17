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

package de.gfelbing.konfig.core.source

import de.gfelbing.konfig.core.log.Log

/**
 * Reads values from environment variables.
 */
class EnvironmentKonfiguration(override val LOG: Log = Sources.DEFAULT_LOG) : KonfigurationSource {
    /**
     * Generates the environment variable name from the path by joining uppercased elements by '_'.
     */
    fun toEnvName(path: List<String>) = path.map { it.toUpperCase() }.joinToString("_")

    /**
     * Reads the environment variable using the naming convention from [toEnvName].
     */
    override fun getOptionalString(path: List<String>) = System.getenv(toEnvName(path))

    /**
     * Generates a source description.
     */
    override fun describe(path: List<String>) = "ENV(${toEnvName(path)})"
}