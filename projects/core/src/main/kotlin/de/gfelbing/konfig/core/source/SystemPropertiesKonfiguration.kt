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

/**
 * [KonfigurationSource] based on system properties.
 */
class SystemPropertiesKonfiguration : PropertiesKonfiguration(System.getProperties()) {

    /**
     * Generates a descriptive string used for logging.
     */
    override fun describe(path: List<String>): String = "SYSTEM_PROPERTY(${toPropertyName(path)})"
}