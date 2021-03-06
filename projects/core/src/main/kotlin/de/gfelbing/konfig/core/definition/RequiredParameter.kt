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
 * Transforms a nullable parameter into an non-null parameter by assertion at invokation.
 */
class RequiredParameter<T>(optionalParameter: Parameter<T?>) : WrappedOptionalParameter<T>(optionalParameter) {
    /**
     * Throws a [KonfigException] if the value is unset.
     */
    override fun handleAbsentParameter() =
        throw KonfigException("Unable to load required parameter ${this.description()}")

    /**
     * Adds a tag to the parameter description indicating that this parameter is required.
     */
    override val additionalDescriptionProperties = listOf("required")
}

