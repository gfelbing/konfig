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
import java.util.*

/**
 * Abstract [KonfigurationSource] based on [Properties]. E.g. [PropertiesFileKonfiguration], [SystemPropertiesKonfiguration].
 */
abstract class PropertiesKonfiguration(val properties: Properties = Properties(), override val LOG: Log = Sources.DEFAULT_LOG) : KonfigurationSource {

    /**
     * Reads a value from [properties] using the naming convention from [toPropertyName].
     */
    override fun getOptionalString(path: List<String>): String? {
        return properties[toPropertyName(path)]?.let { it as? String } // FIXME checked cast? Rather use toString?
    }

    /**
     * Generates a descriptive string used for logging.
     */
    override fun describe(path: List<String>): String = "PROPERTY(${toPropertyName(path)})"

    companion object {
        /**
         * Generates the property name by joining the lowercase path elements with '.'.
         */
        fun toPropertyName(path: List<String>) = path.joinToString(".") { it.toLowerCase() }
    }
}