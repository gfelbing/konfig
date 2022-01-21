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

package de.gfelbing.konfig.examples.onefile

import de.gfelbing.konfig.core.definition.KonfigDeclaration.double
import de.gfelbing.konfig.core.definition.KonfigDeclaration.int
import de.gfelbing.konfig.core.definition.KonfigDeclaration.long
import de.gfelbing.konfig.core.definition.KonfigDeclaration.required
import de.gfelbing.konfig.core.definition.KonfigDeclaration.secret
import de.gfelbing.konfig.core.definition.KonfigDeclaration.string
import de.gfelbing.konfig.core.source.KonfigurationSource

/**
 * Example for a single file configuration.
 *
 * The default constructor directly receives the desired values, which can be useful for testing.
 * The secondary constructor defines the paths within a [KonfigurationSource] and reads the values from it.
 *
 * @author gfelbing@github.com
 */
data class SampleConfig(
    val requiredString: String,
    val optionalString: String?,
    val optionalInt: Int?,
    val requiredLong: Long,
    val requiredDouble: Double,
    val secretString: String
) {

    /**
     * The constructor defines the paths read from the [KonfigurationSource],
     * so no second declaration necessary.
     */
    constructor(source: KonfigurationSource) : this(
        requiredString = source[string("required", "string").required()],
        optionalString = source[string("optional", "string")],
        optionalInt = source[int("optional", "int")],
        requiredLong = source[long("required", "long").required()],
        requiredDouble = source[double("required", "double").required()],
        secretString = source[string("secret", "string").secret().required()],
    )
}