package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.gate.Gate
import me.khol.quantum.math.toIndex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import me.khol.quantum.gate.GateControlled as C
import me.khol.quantum.gate.GateHadamard as H
import me.khol.quantum.gate.GateX as X
import me.khol.quantum.gate.GateZ as Z

internal class GroverAlgorithmTest {

    /**
     * Generates a 3-qubit oracle for use within Grover's algorithm that negates the amplitude of
     * one specific state but leaving amplitude of any other state intact. 
     */
    private fun oracleGate(vararg state: Qubit): Gate {
        return gateAlgorithm(3) {
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
            step { C(C(Z))[0, 1, 2] }
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
        }
    }

    @Test
    fun `Grover's oracle gate negates amplitude only of one specific state`() {
        val gate = oracleGate(ZERO, ONE, ZERO)

        assertThat((gate * Register(ZERO, ONE, ZERO)).matrix, not(equalTo(Register(ZERO, ONE, ZERO).matrix)))

        assertThat((gate * Register(ZERO, ZERO, ZERO)).matrix, equalTo(Register(ZERO, ZERO, ZERO).matrix))
        assertThat((gate * Register(ZERO, ZERO, ONE)).matrix, equalTo(Register(ZERO, ZERO, ONE).matrix))
        assertThat((gate * Register(ZERO, ONE, ONE)).matrix, equalTo(Register(ZERO, ONE, ONE).matrix))
        assertThat((gate * Register(ONE, ZERO, ZERO)).matrix, equalTo(Register(ONE, ZERO, ZERO).matrix))
        assertThat((gate * Register(ONE, ZERO, ONE)).matrix, equalTo(Register(ONE, ZERO, ONE).matrix))
        assertThat((gate * Register(ONE, ONE, ZERO)).matrix, equalTo(Register(ONE, ONE, ZERO).matrix))
        assertThat((gate * Register(ONE, ONE, ONE)).matrix, equalTo(Register(ONE, ONE, ONE).matrix))
    }

    @Test
    fun `Grover's algorithm`() {
        val oracle = oracleGate(ONE, ONE, ZERO)

        val result = runnableAlgorithm(Register(ZERO, ZERO, ZERO)) {
            // Initialization
            step { H[0]; H[1]; H[2] }

            repeat(2) {
                // Oracle
                oracle[0, 1, 2]

                // Diffusion
                step { H[0]; H[1]; H[2] }
                step { X[1]; X[0]; X[2] }
                C(C(Z))[0, 1, 2]
                step { X[1]; X[0]; X[2] }
                step { H[0]; H[1]; H[2] }
            }

            // Measure
            // measureAndCollapse(0, 1, 2)  // Probably [ONE, ONE, ZERO]
        }

        // At this point, there is roughly a 95% probability of observing [ONE, ONE, ZERO] state
        // and less than 1% probability of observing other states.

        // To make the test 100% reliable, inspect the register's underlying probabilities
        // directly instead of randomly measuring the correct state in only 95% of the cases.
        assertThat(result.probabilityOf(ONE, ONE, ZERO), closeTo(0.95, 0.01))
    }

    private fun Register.probabilityOf(vararg state: Qubit): Double {
        return matrix[state.toList().toIndex(), 0].square
    }
}