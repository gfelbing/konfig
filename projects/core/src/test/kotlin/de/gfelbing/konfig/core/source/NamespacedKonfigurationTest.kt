package de.gfelbing.konfig.core.source

import de.gfelbing.konfig.core.definition.KonfigDeclaration.int
import de.gfelbing.konfig.core.definition.KonfigDeclaration.string
import de.gfelbing.konfig.core.definition.KonfigDeclaration.required
import de.gfelbing.konfig.core.source.Sources.withNamespace
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.testng.annotations.Test

class NamespacedKonfigurationTest {
    data class ComplexConfiguration(
        val hisConfig: NamespaceConfiguration,
        val herConfig: NamespaceConfiguration
    ) {
        companion object {
            const val NAMESPACE_HIS = "his"
            const val NAMESPACE_HER = "her"

            fun load(source: KonfigurationSource): ComplexConfiguration {
                return ComplexConfiguration(
                    hisConfig = NamespaceConfiguration.load(source.withNamespace(NAMESPACE_HIS)),
                    herConfig = NamespaceConfiguration.load(source.withNamespace(NAMESPACE_HER))
                )
            }
        }
    }

    data class NamespaceConfiguration(
        val someKey: String,
        val someValue: Int
    ) {
        companion object {
            val someKey = string("some", "key").required()
            val someValue = int("some", "value").required()

            fun load(source: KonfigurationSource): NamespaceConfiguration {
                return NamespaceConfiguration(
                    someKey = source[someKey],
                    someValue = source[someValue]
                )
            }
        }
    }

    @Test
    fun testRead() {
        val expectedConfiguration = ComplexConfiguration(
            hisConfig = NamespaceConfiguration(
                someKey = "foo",
                someValue = 123
            ),
            herConfig = NamespaceConfiguration(
                someKey = "bar",
                someValue = 456
            )
        )

        System.setProperty("his.some.key", expectedConfiguration.hisConfig.someKey)
        System.setProperty("his.some.value", expectedConfiguration.hisConfig.someValue.toString())

        System.setProperty("her.some.key", expectedConfiguration.herConfig.someKey)
        System.setProperty("her.some.value", expectedConfiguration.herConfig.someValue.toString())

        val source = SystemPropertiesKonfiguration()
        val config = ComplexConfiguration.load(source)

        assertThat(config, `is`(expectedConfiguration))
    }
}