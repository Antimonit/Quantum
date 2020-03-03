package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.Register
import me.khol.quantum.times
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not

class GateCCNotTest {

    @Test
    fun `CCNot gate inverts zero if control qubits are one`() {
        assertThat(GateCCNot * Register(ONE, ONE, ZERO), equalTo(Register(ONE, ONE, ONE)))
    }

    @Test
    fun `CCNot gate inverts one if control qubits are one`() {
        assertThat(GateCCNot * Register(ONE, ONE, ONE), equalTo(Register(ONE, ONE, ZERO)))
    }

    @Test
    fun `CCNot gate inverts anything if control qubits are one`() {
        val qubit = Qubit.random()
        assertThat(GateCCNot * Register(ONE, ONE, qubit), not(equalTo(Register(ONE, ONE, qubit))))
    }

    @Test
    fun `CCNot gate does not invert one if any of the control qubits is zero`() {
        val register = Register(ONE, ZERO, ONE)
        assertThat(GateCCNot * register, equalTo(register))
    }

    @Test
    fun `CCNot gate does not invert zero if any of the control qubits is zero`() {
        val register = Register(ONE, ZERO, ZERO)
        assertThat(GateCCNot * register, equalTo(register))
    }

    @Test
    fun `CCNot gate does not invert anything if any of the control qubits is zero`() {
        val qubit = Qubit.random()
        val register = Register(ONE, ZERO, qubit)
        assertThat(GateCCNot * register, equalTo(register))
    }
}
