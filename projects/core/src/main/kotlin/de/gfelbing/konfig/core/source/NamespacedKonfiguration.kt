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

/**
 * A [KonfigurationSource] that automatically wraps incoming read requests
 * into a certain [namespace], such that common key parts can be shared.
 *
 * val source = EnvironmentKonfiguration()
 * val namespaceSource = NamespacedKonfiguration(source, "my", "namespace")
 *
 * -->
 * namespaceSource[string("foo", "bar")] reads ("my", "namespace", "foo", "bar")
 */
class NamespacedKonfiguration(val originalSource: KonfigurationSource, vararg val namespace: String, override val LOG: Log = Sources.DEFAULT_LOG) : KonfigurationSource {
    override fun getOptionalString(path: List<String>) =
        originalSource.getOptionalString(namespacedPath(path))

    override fun describe(path: List<String>) = "NAMESPACE($namespace, ${originalSource.describe(path)})"

    /**
     * Append the actual request path to the general namespace of this source.
     */
    private fun namespacedPath(path: List<String>) = namespace.asList() + path
}