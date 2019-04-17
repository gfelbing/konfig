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
 * Basic optional string parameter.
 */
class StringParameter(val path: List<String>) : Parameter<String?> {

    /**
     * Reads the value from [source] and generates a descriptive value.
     */
    override fun invoke(source: KonfigurationSource) = source.getOptionalString(path).let {
        Pair(it, it?.let { "'$it'" } ?: "NULL")
    }

    /**
     * Returns the [ParameterDescription] of this parameter.
     */
    override fun description() = ParameterDescription(path, "String", listOf())
}

