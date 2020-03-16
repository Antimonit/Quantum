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
        assertThat(13.toBinaryDigits(4), equalTo(listOf(1, 1, 0, 1)))
    }
}
