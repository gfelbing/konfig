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

package de.gfelbing.konfig.examples.hello

import de.gfelbing.konfig.core.definition.KonfigDeclaration
import de.gfelbing.konfig.core.source.KonfigurationSource

/**
 * Declaration of the configuration inputs.
 *
 * @author gfelbing@github.com
 */
object MyConfigDeclaration : KonfigDeclaration() {
    val requiredString = string("required", "string").required()
    val optionalString = string("optional", "string")
    val optionalInt = int("optional", "int")
    val requiredLong = long("required", "long").required()
    val requiredDouble = double("required", "double").required()
    val secretString = string("secret", "string").secret().required()

    /**
     * Util to convert the config declaration into the business object.
     */
    fun load(source: KonfigurationSource) = MyConfig(
            requiredString = source[requiredString],
            optionalString = source[optionalString],
            optionalInt = source[optionalInt],
            requiredLong = source[requiredLong],
            requiredDouble = source[requiredDouble],
            secretString = source[secretString]
    )
}