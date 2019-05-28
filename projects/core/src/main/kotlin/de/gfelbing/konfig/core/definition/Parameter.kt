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

package de.gfelbing.konfig.core.definition

import de.gfelbing.konfig.core.source.KonfigurationSource

/**
 * Typed parameter, which is used to read a value from [KonfigurationSource]s.
 * E.g. [StringParameter].
 */
interface Parameter<T> {
    /**
     * Returns a pair containing the parameter value and a descriptive value which can be used for logging.
     */
    fun readFrom(source: KonfigurationSource): T

    /**
     * The [ParameterDescription] of this parameter.
     */
    fun description(): ParameterDescription

    /**
     * Wraps a retained value into an appropriate string representation for logging.
     * Can be used to override secret value behaviour for example.
     */
    fun toLoggingString(value: T?): String = value.toDefaultLoggingString()

    /**
     * Convenience shorthand for obtaining the value of this [Parameter]
     * from a given [KonfigurationSource].
     */
    operator fun invoke(source: KonfigurationSource): T = readFrom(source)

    companion object {
        const val NULL_LITERAL = "NULL"

        fun <T> T?.toDefaultLoggingString(): String {
            return this?.let { "'$it'" } ?: NULL_LITERAL
        }
    }
}