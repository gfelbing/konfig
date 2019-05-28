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
 * Suppresses logging of secret values.
 */
class SecretParameter<T>(val parent: Parameter<T>) : Parameter<T> {
    /**
     * Delegates invocation to [parent], overriding the descriptive value by 'SECRET'.
     */
    override fun readFrom(source: KonfigurationSource): T = parent.readFrom(source)

    /**
     * Hide any incoming value from evil loggers' eyes.
     */
    override fun toLoggingString(value: T?) = SECRET_LITERAL

    /**
     * Adds the property 'secret' to [parent] description.
     */
    override fun description(): ParameterDescription {
        val desc = parent.description()
        return desc.copy(props = desc.props + "secret")
    }

    companion object {
        const val SECRET_LITERAL = "SECRET"
    }
}

