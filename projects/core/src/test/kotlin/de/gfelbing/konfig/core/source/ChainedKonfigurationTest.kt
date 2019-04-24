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

package de.gfelbing.konfig.core.source

import de.gfelbing.konfig.core.definition.StringParameter
import de.gfelbing.konfig.core.log.Log
import de.gfelbing.konfig.core.log.StdoutLog
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.testng.annotations.Test

/**
 * Tests [ChainedKonfiguration].
 */
class ChainedKonfigurationTest {

    object NullSource : KonfigurationSource {
        val NULL = "NULL"
        override val LOG: Log = StdoutLog
        override fun getOptionalString(path: List<String>) = null
        override fun describe(path: List<String>) = NULL
    }

    class FixSource(val fixValue: String) : KonfigurationSource {
        override val LOG: Log = StdoutLog
        override fun getOptionalString(path: List<String>) = fixValue
        override fun describe(path: List<String>) = fixValue
    }

    @Test
    fun testRead() {
        val fixValue1 = "asdf"
        val fixValue2 = "bsdf"
        val chainedSource = ChainedKonfiguration(
                NullSource,
                FixSource(fixValue1),
                NullSource,
                FixSource(fixValue2)
        )
        val anyParameter = StringParameter(listOf())

        val valueRead = chainedSource[anyParameter]
        val descriptionRead = chainedSource[anyParameter]

        assertThat(valueRead, `is`(fixValue1))
        assertThat(descriptionRead, `is`(fixValue1))
    }

    @Test
    fun testValueMissing() {
        val chainedSource = ChainedKonfiguration(
                NullSource,
                NullSource
        )
        val anyParameter = StringParameter(listOf())


        val valueRead = chainedSource[anyParameter]
        val descriptionRead = chainedSource[anyParameter]

        assertThat(valueRead, `is`(nullValue()))
        assertThat(descriptionRead, `is`(nullValue()))
    }
}