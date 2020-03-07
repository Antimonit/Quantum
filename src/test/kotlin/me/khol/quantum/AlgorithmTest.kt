package me.khol.quantum

import me.khol.quantum.gate.Gate
import me.khol.quantum.gate.GateCNot
import me.khol.quantum.gate.GateHadamard
import me.khol.quantum.gate.GateIdentity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AlgorithmTest {

    @Test
    fun `Apply a gate to a single qubit`() {
        assertThat(algorithm(1) {
            GateHadamard[0]
        }.asGate(), equalTo<Gate>(GateHadamard))
    }

    @Test
    fun `Apply a gate to two qubits`() {
        assertThat(algorithm(2) {
            GateCNot[0, 1]
        }.asGate(), equalTo<Gate>(GateCNot))
    }

    @Test
    fun `Apply a gate to the first qubit only`() {
        assertThat(algorithm(2) {
            GateHadamard[0]
        }.asGate(), equalTo(GateHadamard tensor GateIdentity))
    }

    @Test
    fun `Apply a gate to the second qubit only`() {
        assertThat(algorithm(2) {
            GateHadamard[1]
        }.asGate(), equalTo(GateIdentity tensor GateHadamard))
    }

    @Test
    fun `Apply two gates in a single step`() {
        assertThat(algorithm(2) {
            GateHadamard[0] + GateHadamard[1]
        }.asGate(), equalTo(GateHadamard tensor GateHadamard))
    }

    @Test
    fun `Applying two gates to a single qubit in a single step fails`() {
        assertThrows<IllegalStateException> {
            algorithm(1) {
                GateHadamard[0] + GateHadamard[0]
            }.asGate()
        }
    }

    @Test
    fun `Apply two gates after each other`() {
        assertThat(algorithm(1) {
            GateHadamard[0]
            GateHadamard[0]
        }.asGate(), equalTo(GateHadamard * GateHadamard))
    }

    @Test
    fun `Apply two gates to two different qubits in separate steps`() {
        assertThat(algorithm(2) {
            GateHadamard[0]
            GateHadamard[1]
        }.asGate(), equalTo(GateHadamard tensor GateHadamard))
    }
}