package de.gfelbing.konfig.examples.onefile

import de.gfelbing.konfig.core.source.ChainedKonfiguration
import de.gfelbing.konfig.core.source.EnvironmentKonfiguration
import de.gfelbing.konfig.core.source.PropertiesFileKonfiguration
import de.gfelbing.konfig.core.source.SystemPropertiesKonfiguration

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // set some system properties
        System.setProperty("required.string", "some-string")
        System.setProperty("required.long", "123456789")
        System.setProperty("required.double", "12345.6789")

        // define the source
        val source = ChainedKonfiguration(
            // use environment config as first source
            EnvironmentKonfiguration(),
            // fall back to system properties
            SystemPropertiesKonfiguration(),
            // fall back to properties file
            PropertiesFileKonfiguration("hello.properties")
        )

        // load the config and print result
        val config = SampleConfig(source)
        println("Hello, $config!")
    }
}