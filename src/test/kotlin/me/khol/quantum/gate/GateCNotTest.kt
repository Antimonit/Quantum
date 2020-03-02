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

class GateCNotTest {

    @DisplayName("CNot gate inverts zero if control qubit is one.")
    @Test
    fun cNotGateInvertsZeroIfControlIsOne() {
        assertThat(GateCNot * Register(ONE, ZERO), equalTo(Register(ONE, ONE)))
    }

    @DisplayName("CNot gate inverts one if control qubit is one.")
    @Test
    fun cNotGateInvertsOneIfControlIsOne() {
        assertThat(GateCNot * Register(ONE, ONE), equalTo(Register(ONE, ZERO)))
    }

    @DisplayName("CNot gate inverts anything if control qubit is one.")
    @Test
    fun cNotGateInvertsAnythingIfControlIsOne() {
        val qubit = Qubit.random()
        assertThat(GateCNot * Register(ONE, qubit), not(equalTo(Register(ONE, qubit))))
    }

    @DisplayName("CNot gate does not invert one if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertOneIfControlIsZero() {
        assertThat(GateCNot * Register(ZERO, ONE), equalTo(Register(ZERO, ONE)))
    }

    @DisplayName("CNot gate does not invert zero if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertZeroIfControlIsZero() {
        assertThat(GateCNot * Register(ZERO, ZERO), equalTo(Register(ZERO, ZERO)))
    }

    @DisplayName("CNot gate does not invert anything if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertAnythingIfControlIsZero() {
        val qubit = Qubit.random()
        assertThat(GateCNot * Register(ZERO, qubit), equalTo(Register(ZERO, qubit)))
    }
}
