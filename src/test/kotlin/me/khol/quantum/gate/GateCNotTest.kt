package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Matrix
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

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
        assertEquals(ONE x ONE, applyGate(control = ONE, target = ZERO))
    }

    @DisplayName("CNot gate inverts one if control qubit is one.")
    @Test
    fun cNotGateInvertsOneIfControlIsOne() {
        assertEquals(ONE x ZERO, applyGate(control = ONE, target = ONE))
    }

    @DisplayName("CNot gate inverts anything if control qubit is one.")
    @Test
    fun cNotGateInvertsAnythingIfControlIsOne() {
        val qubit = Qubit.random()
        assertNotEquals(ONE x qubit, applyGate(control = ONE, target = qubit))
    }

    @DisplayName("CNot gate does not invert one if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertOneIfControlIsZero() {
        assertEquals(ZERO x ONE, applyGate(control = ZERO, target = ONE))
    }

    @DisplayName("CNot gate does not invert zero if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertZeroIfControlIsZero() {
        assertEquals(ZERO x ZERO, applyGate(control = ZERO, target = ZERO))
    }

    @DisplayName("CNot gate does not invert anything if control qubit is zero.")
    @Test
    fun cNotGateDoesNotInvertAnythingIfControlIsZero() {
        val qubit = Qubit.random()
        assertEquals(ZERO x qubit, applyGate(control = ZERO, target = qubit))
    }
}
