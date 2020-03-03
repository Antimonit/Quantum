package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Matrix
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not

class GateCNotTest {

    private fun applyGate(control: Qubit, target: Qubit): Matrix {
        val gateInput = control x target
        val gate = GateCNot
        val gateOutput = gate.matrix * gateInput
        return gateOutput
    }

    @DisplayName("CNot gate inverts zero if control qubit is one.")
    @Test
    fun cNotGateInvertsZeroIfControlIsOne() {
        assertThat(applyGate(control = ONE, target = ZERO), equalTo(ONE x ONE))
    }

    @DisplayName("CNot gate inverts one if control qubit is one.")
    @Test
    fun cNotGateInvertsOneIfControlIsOne() {
        assertThat(applyGate(control = ONE, target = ONE), equalTo(ONE x ZERO))
    }

    @DisplayName("CNot gate inverts anything if control qubit is one.")
    @Test
    fun cNotGateInvertsAnythingIfControlIsOne() {
        val qubit = Qubit.random()
        assertThat(applyGate(control = ONE, target = qubit), not(equalTo(ONE x qubit)))
    }

    @DisplayName("CNot gate does not invert one if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertOneIfControlIsZero() {
        assertThat(applyGate(control = ZERO, target = ONE), equalTo(ZERO x ONE))
    }

    @DisplayName("CNot gate does not invert zero if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertZeroIfControlIsZero() {
        assertThat(applyGate(control = ZERO, target = ZERO), equalTo(ZERO x ZERO))
    }

    @DisplayName("CNot gate does not invert anything if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertAnythingIfControlIsZero() {
        val qubit = Qubit.random()
        assertThat(applyGate(control = ZERO, target = qubit), equalTo(ZERO x qubit))
    }
}
