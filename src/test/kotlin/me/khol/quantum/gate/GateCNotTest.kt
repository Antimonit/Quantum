package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.Register
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not

class GateCNotTest {

    @Test
    fun `CNot gate inverts zero if control qubit is one`() {
        assertThat(GateCNot * Register(ONE, ZERO), equalTo(Register(ONE, ONE)))
    }

    @Test
    fun `CNot gate inverts one if control qubit is one`() {
        assertThat(GateCNot * Register(ONE, ONE), equalTo(Register(ONE, ZERO)))
    }

    @Test
    fun `CNot gate inverts anything if control qubit is one`() {
        val qubit = Qubit.random()
        assertThat(GateCNot * Register(ONE, qubit), not(equalTo(Register(ONE, qubit))))
    }

    @Test
    fun `CNot gate does not invert one if control qubit is zero`() {
        val register = Register(ZERO, ONE)
        assertThat(GateCNot * register, equalTo(register))
    }

    @Test
    fun `CNot gate does not invert zero if control qubit is zero`() {
        val register = Register(ZERO, ZERO)
        assertThat(GateCNot * register, equalTo(register))
    }

    @Test
    fun `CNot gate does not invert anything if control qubit is zero`() {
        val qubit = Qubit.random()
        val register = Register(ZERO, qubit)
        assertThat(GateCNot * register, equalTo(register))
    }
}
