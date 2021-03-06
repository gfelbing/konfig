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

import de.gfelbing.konfig.core.definition.Parameter
import de.gfelbing.konfig.core.definition.ParameterDescription
import de.gfelbing.konfig.core.log.Log

/**
 * Interface for configuration sources, e.g. [EnvironmentKonfiguration].
 */
interface KonfigurationSource {

    /**
     * Returns the configuration value for a given path.
     */
    fun getOptionalString(path: List<String>): String?

    /**
     * Returns a descriptive string usable for logging.
     */
    fun describe(path: List<String>): String?

    /**
     * The logger used by this source.
     */
    val LOG: Log

    /**
     * Default implementation of the get operator which logs the access.
     */
    operator fun <T> get(parameter: Parameter<T>): T {
        val desc = parameter.description()

        try {
            return parameter(this).also {
                desc.logLoadEvent("value:${parameter.toLoggingString(it)}")
            }
        } catch (e: Exception) {
            throw e.also {
                desc.logLoadEvent("error:${it.message}")
            }
        }
    }

    private fun ParameterDescription.logLoadEvent(resultMessage: String) {
        LOG.info(listOfNotNull(
            path.joinToString(".", "config:"),
            resultMessage,
            "type:$typeName",
            props.joinToString(", ", "props:[", "]"),
            describe(path)?.let { "source:$it" }
        ).joinToString(", "))
    }
}