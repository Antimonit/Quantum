package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Register
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GateSwapTest {

    @DisplayName("Swap gate correctly swaps qubits")
    @Test
    fun gateSwapCorrectlySwapsQubits() {
        val qubitA = Qubit.random()
        val qubitB = Qubit.random()
        val input = Register(qubitA, qubitB)
        val expected = Register(qubitB, qubitA)
        val actual = GateSwap * input
        assertThat(actual, equalTo(expected))
    }
}
