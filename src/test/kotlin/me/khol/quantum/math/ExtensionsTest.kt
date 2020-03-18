package me.khol.quantum.math

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

internal class ExtensionsTest {

    @Test
    fun `Integer pow is correct`() {
        assertThat(2 pow 5, equalTo(32))
    }

    @Test
    fun `Binary index to a list of qubits`() {
        assertThat(0b01101.toQubits(5), equalTo(listOf(ZERO, ONE, ONE, ZERO, ONE)))
    }

    @Test
    fun `List of qubits to a binary index`() {
        assertThat(listOf(ZERO, ONE, ONE, ZERO, ONE).toIndex(), equalTo(0b01101))
    }
}
