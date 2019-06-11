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
 * Base object for declarations, holding convenience methods.
 */
object KonfigDeclaration {
    /**
     * Extends [Parameter] to convert it into a non-null [RequiredParameter].
     */
    fun <T> Parameter<T?>.required() = RequiredParameter(this)

    /**
     * Extends [Parameter] to convert it into a [SecretParameter].
     */
    fun <T> Parameter<T>.secret() = SecretParameter(this)

    /**
     * Factory method for a [StringParameter] which is transformed to [Int].
     */
    fun int(vararg path: String) = TransformedParameter(string(*path), "Int") { it?.toInt() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Byte].
     */
    fun byte(vararg path: String) = TransformedParameter(string(*path), "Byte") { it?.toByte() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Short].
     */
    fun short(vararg path: String) = TransformedParameter(string(*path), "Short") { it?.toShort() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Long].
     */
    fun long(vararg path: String) = TransformedParameter(string(*path), "Long") { it?.toLong() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Float].
     */
    fun float(vararg path: String) = TransformedParameter(string(*path), "Float") { it?.toFloat() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Double].
     */
    fun double(vararg path: String) = TransformedParameter(string(*path), "Double") { it?.toDouble() }

    /**
     * Factory method for a [StringParameter] which is transformed to [Double].
     */
    fun boolean(vararg path: String) = TransformedParameter(string(*path), "Boolean") { it?.toBoolean() }

    /**
     * Factory method for a list parameter.
     */
    fun <T> list(vararg path: String, separator: String = ",") = TransformedParameter(string(*path), "List") {
        it?.split(separator)?.map(String::trim)
    }

    /**
     * Factory method for a [StringParameter].
     */
    fun string(vararg path: String) = StringParameter(path.toList())
}

