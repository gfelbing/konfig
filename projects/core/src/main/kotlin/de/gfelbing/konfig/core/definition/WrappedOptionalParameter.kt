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
 * Wraps a nullable parameter into an non-null parameter by assuming some abstract behaviour
 * that has to be implemented by subclasses.
 */
abstract class WrappedOptionalParameter<T>(val optionalParameter: Parameter<T?>) : Parameter<T> {
    /**
     * Delegates the invocation to the [optionalParameter] and executes some behaviour
     * (specified via [handleAbsentParameter]) if it is not present in the original source
     */
    override fun readFrom(source: KonfigurationSource): T = optionalParameter.readFrom(source)
        ?: handleAbsentParameter()

    /**
     * Injects the behaviour that is desired for when a configuration string
     * cannot be found in the original source.
     */
    abstract fun handleAbsentParameter(): T

    /**
     * Delegates the invocation to the wrapped parameter.
     *
     * This is mostly interesting when SecretParameter is wrapped, such that the
     * confidentiality is retained when logging.
     */
    override fun toLoggingString(value: T?) = optionalParameter.toLoggingString(value)

    /**
     * Adds the property 'required' to the parent description.
     */
    override fun description(): ParameterDescription {
        val parentDescription = optionalParameter.description()
        return parentDescription.copy(props = parentDescription.props + additionalDescriptionProperties)
    }

    /**
     * Defines additional properties to be used as flags during the logging description process.
     */
    abstract val additionalDescriptionProperties: List<String>
}
