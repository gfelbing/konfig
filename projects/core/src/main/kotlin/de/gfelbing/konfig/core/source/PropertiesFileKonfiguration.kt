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

import de.gfelbing.konfig.core.definition.KonfigException
import de.gfelbing.konfig.core.log.Log
import java.io.File
import java.io.InputStream

/**
 * [KonfigurationSource] based on property files.
 */
class PropertiesFileKonfiguration(val fileName: String, val input: () -> InputStream, override val LOG: Log = Sources.DEFAULT_LOG) : PropertiesKonfiguration() {

    constructor(filePath: String) : this(filePath, { getInputStream(filePath) })

    /**
     * Initial update of the properties.
     */
    init {
        updateProperties()
    }

    /**
     * Updates the properties read from the file. Can be used to re-load the properties, e.g. by file watches.
     */
    fun updateProperties() {
        properties.load(input())
    }

    /**
     * Generates a descriptive string used for logging.
     */
    override fun describe(path: List<String>) = "PROPERTY($fileName, ${toPropertyName(path)})"

    companion object {
        /**
         * Creates an [InputStream] for the given [fileName].
         */
        fun getInputStream(filePath: String): InputStream {
            // First, try to load the file from the file system
            if (File(filePath).exists()) {
                return File(filePath).inputStream()
            }
            // If this fails, it may be bundled in a jar
            val systemResourceAsStream = ClassLoader.getSystemResourceAsStream(filePath)
            if (systemResourceAsStream != null) {
                return systemResourceAsStream
            }
            // Otherwise, fail.
            throw KonfigException("Unable to load property file '$filePath'.")
        }
    }
}