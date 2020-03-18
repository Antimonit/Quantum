package me.khol.quantum.math

import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

internal class ExtensionsTest {

    @Test
    fun `Integer pow is correct`() {
        assertThat(2 pow 5, equalTo(32))
    }

    @Test
    fun `Integer to a list of binary digits`() {
        assertThat(0b1101.toBinaryDigits(4), equalTo(listOf(1, 1, 0, 1)))
    }

    @Test
    fun `List of binary digits to an integer`() {
        assertThat(listOf(1, 1, 0, 1).fromBinaryDigits(), equalTo(0b1101))
    }
}
