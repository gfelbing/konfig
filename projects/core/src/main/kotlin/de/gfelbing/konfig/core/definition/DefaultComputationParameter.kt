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

/**
 * Transforms a nullable parameter into an non-null parameter by assuming a default value
 * which is computed on demand (i.e. only if the actual parameter is not present)
 */
class DefaultComputationParameter<T>(optionalParameter: Parameter<T?>, val default: () -> T) : WrappedOptionalParameter<T>(optionalParameter) {
    /**
     * Computes the default value only when necessary (i.e. when this method is called at all).
     */
    override fun handleAbsentParameter() = default()

    /**
     * Adds a tag to the description indicating that a default value is being used.
     *
     * This default value is not unpacked here, in order not to accidentally call
     * any computationally expensive functions
     */
    override val additionalDescriptionProperties = listOf("computedDefault")
}
