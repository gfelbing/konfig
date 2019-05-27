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
 * Transforms the type of a [Parameter].
 */
class TransformedParameter<V, T>(val parent: Parameter<V>, val typeName: String, val transform: (V) -> T) : Parameter<T> {

    /**
     * Invokes [parent] and applies the [transform] the the value.
     */
    override fun invoke(source: KonfigurationSource) = parent(source).let { (value, _) ->
        val transformedValue = transform(value)
        Pair(transformedValue, transformedValue?.let { "'$it'" } ?: "NULL")
    }

    /**
     * Overrides the type of the [parent] description.
     */
    override fun description() = parent.description().copy(typeName = typeName)
}