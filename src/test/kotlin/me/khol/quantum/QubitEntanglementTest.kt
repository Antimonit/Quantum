package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.gate.GateCNot
import me.khol.quantum.gate.GateHadamard
import me.khol.quantum.math.Matrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class QubitEntanglementTest {

    /**
     * Bell states are two-qubit states that are maximally entangled.
     *
     * Just like in a real world, we cannot simply create a Bell state by putting two untentangled
     * qubits into a register. To create a Bell state we must first apply some transformations to
     * them that will put them into a superposition and then entangle them.
     *
     * That is why we don't create [Register]s representations of the Bell states below but rather
     * their probability matrices. In the tests we verify that after applying some transformations
     * to the registers, their measurement probabilities will match the probabilities of these
     * matrices.
     */
    val φ_plus = Register(((ZERO x ZERO) + (ONE x ONE)) * sqrt(0.5))
    val φ_minus = Register(((ZERO x ZERO) - (ONE x ONE)) * sqrt(0.5))
    val ψ_plus = Register(((ZERO x ONE) + (ONE x ZERO)) * sqrt(0.5))
    val ψ_minus = Register(((ZERO x ONE) - (ONE x ZERO)) * sqrt(0.5))

    /**
     * |0> ---|H|-----*------ |
     *                |       | φ_plus
     * |0> ---------|CNOT|--- |
     */
    @Test
    fun φ_plusEntanglement() {
        assertEquals(φ_plus.matrix, hadamardEntanglement(ZERO, ZERO))
    }

    /**
     * |1> ---|H|-----*------ |
     *                |       | φ_minus
     * |0> ---------|CNOT|--- |
     */
    @Test
    fun φ_minusEntanglement() {
        assertEquals(φ_minus.matrix, hadamardEntanglement(ONE, ZERO))
    }

    /**
     * |0> ---|H|-----*------ |
     *                |       | ψ_plus
     * |1> ---------|CNOT|--- |
     */
    @Test
    fun ψ_plusEntanglement() {
        assertEquals(ψ_plus.matrix, hadamardEntanglement(ZERO, ONE))
    }

    /**
     * |1> ---|H|-----*------ |
     *                |       | ψ_minus
     * |1> ---------|CNOT|--- |
     */
    @Test
    fun ψ_minusEntanglement() {
        assertEquals(ψ_minus.matrix, hadamardEntanglement(ONE, ONE))
    }

    /**
     * Entangles two qubits to form a Bell state.
     *
     * |top> ---|H|-----*------ |
     *                  |       | result
     * |bottom> ------|CNOT|--- |
     */
    private fun hadamardEntanglement(top: Qubit, bottom: Qubit): Matrix {
        return GateCNot.matrix * Register(Qubit(GateHadamard.matrix * top.ket), bottom).matrix
    }
}
