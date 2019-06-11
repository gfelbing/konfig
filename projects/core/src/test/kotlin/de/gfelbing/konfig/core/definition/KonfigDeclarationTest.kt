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

import de.gfelbing.konfig.core.definition.KonfigDeclaration.string
import de.gfelbing.konfig.core.definition.KonfigDeclaration.double
import de.gfelbing.konfig.core.definition.KonfigDeclaration.long
import de.gfelbing.konfig.core.definition.KonfigDeclaration.int
import de.gfelbing.konfig.core.definition.KonfigDeclaration.byte
import de.gfelbing.konfig.core.definition.KonfigDeclaration.short
import de.gfelbing.konfig.core.definition.KonfigDeclaration.float
import de.gfelbing.konfig.core.definition.KonfigDeclaration.boolean
import de.gfelbing.konfig.core.definition.KonfigDeclaration.secret
import de.gfelbing.konfig.core.definition.KonfigDeclaration.required
import de.gfelbing.konfig.core.definition.KonfigDeclaration.default
import de.gfelbing.konfig.core.definition.KonfigDeclaration.lazyDefault
import de.gfelbing.konfig.core.definition.KonfigDeclaration.enum
import de.gfelbing.konfig.core.source.KonfigurationSource
import de.gfelbing.konfig.core.source.SystemPropertiesKonfiguration
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.testng.annotations.Test

class KonfigDeclarationTest {

    enum class Dummy {
        FOO, BAR, BAZ
    }

    object TestConfigDeclaration {
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

        val defaultString = string("default", "string").default("maybe")
        val defaultLazyInt = int("default", "int").lazyDefault { 40 + 2 }

        val dummy = enum<Dummy>("dummy").required()
        val dummyNullable = enum<Dummy>("dummy", "nullable")

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
                boolean = source[boolean],
                defaultString = source[defaultString],
                defaultLazyInt = source[defaultLazyInt],
                dummy = source[dummy],
                dummyNullable = source[dummyNullable]
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
            val boolean: Boolean?,
            val defaultString: String,
            val defaultLazyInt: Int,
            val dummy: Dummy,
            val dummyNullable: Dummy?
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
                boolean = true,
                defaultString = "maybe",
                defaultLazyInt = 42,
                dummy = Dummy.FOO,
                dummyNullable = Dummy.BAR
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

        // Default values are derived through the fallback functionality
        
        // System.setProperty("default.string", expectedConfig.defaultString)
        // System.setProperty("default.int", expectedConfig.defaultLazyInt.toString())

        System.setProperty("dummy", expectedConfig.dummy.name)
        System.setProperty("dummy.nullable", expectedConfig.dummyNullable?.name.toString())

        val config = TestConfigDeclaration.load(SystemPropertiesKonfiguration())

        assertThat(config, `is`(expectedConfig))
    }
}