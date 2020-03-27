package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.toIndex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import me.khol.quantum.gate.GateCCNot as CCNot
import me.khol.quantum.gate.GateCNot as CNot
import me.khol.quantum.gate.GateHadamard as H

internal class QuantumAdderTest {

    /**
     * Input qubits: [A, B, carryIn, ∣0⟩]
     * Output qubits: [A, B, sum, carryOut]
     */
    private val fullAdder = gateAlgorithm(4) {
        CCNot[0, 1, 3]
        CNot[0, 1]
        CCNot[1, 2, 3]
        CNot[1, 2]
        CNot[0, 1]
    }

    @Test
    fun `Quantum adder with classical values`() {
        // When we add 1 + 1 with 0 carry, we expect to get sum 0 with 1 carry
        assertThat(fullAdder * Register(ONE, ONE, ZERO, ZERO), equalTo(Register(ONE, ONE, ZERO, ONE)))

        // All possible combinations
        assertThat(fullAdder * Register(ZERO, ZERO, ZERO, ZERO), equalTo(Register(ZERO, ZERO, ZERO, ZERO)))
        assertThat(fullAdder * Register(ZERO, ZERO, ONE, ZERO), equalTo(Register(ZERO, ZERO, ONE, ZERO)))
        assertThat(fullAdder * Register(ZERO, ONE, ZERO, ZERO), equalTo(Register(ZERO, ONE, ONE, ZERO)))
        assertThat(fullAdder * Register(ZERO, ONE, ONE, ZERO), equalTo(Register(ZERO, ONE, ZERO, ONE)))
        assertThat(fullAdder * Register(ONE, ZERO, ZERO, ZERO), equalTo(Register(ONE, ZERO, ONE, ZERO)))
        assertThat(fullAdder * Register(ONE, ZERO, ONE, ZERO), equalTo(Register(ONE, ZERO, ZERO, ONE)))
        assertThat(fullAdder * Register(ONE, ONE, ZERO, ZERO), equalTo(Register(ONE, ONE, ZERO, ONE)))
        assertThat(fullAdder * Register(ONE, ONE, ONE, ZERO), equalTo(Register(ONE, ONE, ONE, ONE)))
    }

    @Test
    fun `Quantum adder with superposed qubits`() {
        val register = runnableAlgorithm(4) {
            H[0]
            H[1]
            fullAdder[0..3]
        }

        // It is equally likely to observe any of the following:
        // 0 + 0 = 00
        // 0 + 1 = 01
        // 1 + 0 = 01
        // 1 + 1 = 10
        assertThat(register.probabilityOf(ZERO, ZERO, ZERO, ZERO), closeTo(0.25, 1e-10))
        assertThat(register.probabilityOf(ZERO, ONE, ONE, ZERO), closeTo(0.25, 1e-10))
        assertThat(register.probabilityOf(ONE, ZERO, ONE, ZERO), closeTo(0.25, 1e-10))
        assertThat(register.probabilityOf(ONE, ONE, ZERO, ONE), closeTo(0.25, 1e-10))
    }

    @Test
    fun `Quantum adder with entangled qubits`() {
        // Utilize etanglement to calculate two separate additions at the same time.

        val register = runnableAlgorithm(4) {
            // Encode two combinations of input into an entangled pair.
            // By passing ∣00⟩ + ∣11⟩ / sqrt(2) as input to the adder
            // we are essentially calculating both 0 + 0 and 1 + 1.
            H[0]
            CNot[0, 1]
            fullAdder[0..3]
        }

        // In the end it is equally likely to observe result of 0 + 0 = 00 and 1 + 1 = 10
        assertThat(register.probabilityOf(ZERO, ZERO, ZERO, ZERO), closeTo(0.5, 1e-10))
        assertThat(register.probabilityOf(ONE, ONE, ZERO, ONE), closeTo(0.5, 1e-10))
    }

    private fun Register.probabilityOf(vararg state: Qubit): Double {
        return matrix[state.toList().toIndex(), 0].square
    }
}