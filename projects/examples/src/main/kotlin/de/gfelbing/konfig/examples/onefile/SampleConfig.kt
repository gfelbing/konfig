package de.gfelbing.konfig.examples.onefile

import de.gfelbing.konfig.core.definition.KonfigDeclaration.double
import de.gfelbing.konfig.core.definition.KonfigDeclaration.int
import de.gfelbing.konfig.core.definition.KonfigDeclaration.long
import de.gfelbing.konfig.core.definition.KonfigDeclaration.required
import de.gfelbing.konfig.core.definition.KonfigDeclaration.secret
import de.gfelbing.konfig.core.definition.KonfigDeclaration.string
import de.gfelbing.konfig.core.source.KonfigurationSource

/**
 * Example for a single file configuration.
 *
 * The default constructor directly receives the desired values, which can be useful for testing.
 * The secondary constructor defines the paths within a [KonfigurationSource] and reads the values from it.
 *
 * @author gfelbing@github.com
 */
data class SampleConfig(
    val requiredString: String,
    val optionalString: String?,
    val optionalInt: Int?,
    val requiredLong: Long,
    val requiredDouble: Double,
    val secretString: String
) {

    /**
     * The constructor defines the paths read from the [KonfigurationSource],
     * so no second declaration necessary.
     */
    constructor(source: KonfigurationSource) : this(
        requiredString = source[string("required", "string").required()],
        optionalString = source[string("optional", "string")],
        optionalInt = source[int("optional", "int")],
        requiredLong = source[long("required", "long").required()],
        requiredDouble = source[double("required", "double").required()],
        secretString = source[string("secret", "string").secret().required()],
    )
}