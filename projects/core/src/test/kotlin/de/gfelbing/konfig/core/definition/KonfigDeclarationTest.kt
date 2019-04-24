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
import de.gfelbing.konfig.core.source.SystemPropertiesKonfiguration
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.testng.annotations.Test

class KonfigDeclarationTest {

    object TestConfigDeclaration : KonfigDeclaration() {
        val optionalDouble = double("optional", "double")
        val optionalString = string("optional", "string")
        val requiredLong = long("required", "long").required()
        val secretInt = int("secret", "int").secret().required()

        val int = int("signed", "int")
        val byte = byte("signed", "byte")
        val short = short("signed", "short")
        val long = long("signed", "long")

        val double = double("double")
        val float = float("float")

        val boolean = boolean("boolean")

        fun load(source: KonfigurationSource) = TestConfig(
                optionalDouble = source[optionalDouble],
                optionalString = source[optionalString],
                requiredLong = source[requiredLong],
                secretInt = source[secretInt],
                int = source[int],
                byte = source[byte],
                short = source[short],
                long = source[long],
                double = source[double],
                float = source[float],
                boolean = source[boolean]
        )
    }

    data class TestConfig(
            val optionalDouble: Double?,
            val optionalString: String?,
            val requiredLong: Long,
            val secretInt: Int,
            val int: Int?,
            val byte: Byte?,
            val short: Short?,
            val long: Long?,
            val double: Double?,
            val float: Float?,
            val boolean: Boolean?
    )

    @Test
    fun test() {
        val expectedConfig = TestConfig(
                optionalDouble = 1.23,
                optionalString = null,
                requiredLong = 123L,
                secretInt = 234,
                int = -1,
                byte = -2,
                short = -3,
                long = -4,
                double = 1.2,
                float = 2.3F,
                boolean = true
        )

        System.setProperty("optional.double", expectedConfig.optionalDouble.toString())
        System.setProperty("required.long", expectedConfig.requiredLong.toString())
        System.setProperty("secret.int", expectedConfig.secretInt.toString())

        System.setProperty("signed.int", expectedConfig.int.toString())
        System.setProperty("signed.byte", expectedConfig.byte.toString())
        System.setProperty("signed.short", expectedConfig.short.toString())
        System.setProperty("signed.long", expectedConfig.long.toString())

        System.setProperty("double", expectedConfig.double.toString())
        System.setProperty("float", expectedConfig.float.toString())

        System.setProperty("boolean", expectedConfig.boolean.toString())

        val config = TestConfigDeclaration.load(SystemPropertiesKonfiguration())

        assertThat(config, `is`(expectedConfig))
    }
}