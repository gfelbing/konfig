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

import de.gfelbing.konfig.core.log.Log
import de.gfelbing.konfig.core.source.Sources.DEFAULT_LOG

/**
 * Chains multiple [KonfigurationSource]s.
 * The first configuration which yields a value is chosen.
 */
class ChainedKonfiguration(val sources: List<KonfigurationSource>, override val LOG: Log = DEFAULT_LOG) : KonfigurationSource {

    constructor(vararg sources: KonfigurationSource, log: Log = DEFAULT_LOG) : this(sources.toList(), log)

    override fun getOptionalString(path: List<String>) =
        findSource(path)?.first

    override fun describe(path: List<String>): String? =
        findSource(path)?.second?.describe(path)

    private fun findSource(path: List<String>) =
        sources.asSequence()
            .mapNotNull { it.getOptionalString(path)?.to(it) }
            .firstOrNull()
}