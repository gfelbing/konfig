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

package de.gfelbing.konfig.core.log

object StdoutLog : Log {
    val isDebug = System.getenv("KONFIG_DEBUG")?.let { it.toLowerCase() == "true" } ?: false

    override fun debug(msg: String) {
        if (isDebug) {
            println(msg)
        }
    }

    override fun info(msg: String) {
        println(msg)
    }
}