package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.gate.GateCNot
import me.khol.quantum.gate.GateHadamard
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class QubitEntanglementTest {

    /**
     * Bell states are two-qubit states that are maximally entangled.
     *
     * We cannot simply create a Bell state by putting two unentangled qubits into a register.
     * To create a Bell state we must first apply some transformations to the qubits that will put
     * them into a superposition and then entangle them.
     *
     * Here we cheat a little bit by constructing Bell states' probability matrices first and then
     * constructing the [Register] from them.
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
        assertThat(hadamardEntanglement(ZERO, ZERO), equalTo(φ_plus))
    }

    /**
     * |1> ---|H|-----*------ |
     *                |       | φ_minus
     * |0> ---------|CNOT|--- |
     */
    @Test
    fun φ_minusEntanglement() {
        assertThat(hadamardEntanglement(ONE, ZERO), equalTo(φ_minus))
    }

    /**
     * |0> ---|H|-----*------ |
     *                |       | ψ_plus
     * |1> ---------|CNOT|--- |
     */
    @Test
    fun ψ_plusEntanglement() {
        assertThat(hadamardEntanglement(ZERO, ONE), equalTo(ψ_plus))
    }

    /**
     * |1> ---|H|-----*------ |
     *                |       | ψ_minus
     * |1> ---------|CNOT|--- |
     */
    @Test
    fun ψ_minusEntanglement() {
        assertThat(hadamardEntanglement(ONE, ONE), equalTo(ψ_minus))
    }

    /**
     * Entangles two qubits to form a Bell state.
     *
     * |top> ---|H|-----*------ |
     *                  |       | result
     * |bottom> ------|CNOT|--- |
     */
    private fun hadamardEntanglement(top: Qubit, bottom: Qubit): Register {
        return GateCNot * Register(GateHadamard * top, bottom)
    }
}
