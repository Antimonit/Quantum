package me.khol.quantum.algorithm

import me.khol.quantum.*
import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.gate.Gate
import me.khol.quantum.math.toIndex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import me.khol.quantum.gate.GateControlled as C
import me.khol.quantum.gate.GateHadamard as H
import me.khol.quantum.gate.GateX as X
import me.khol.quantum.gate.GateZ as Z

/**
 * Although the purpose of Grover's algorithm is usually described as "searching a database", it
 * may be more accurate to describe it as "inverting a function". Roughly speaking, if a function
 * `y=f(x)` can be evaluated on a quantum computer, Grover's algorithm calculates `x` when given
 * `y`.
 *
 * The Grover's algorithm consists of four stages:
 *
 * 1) **Initialization**: Creates an equal superposition of all states. All the states are equally
 * likely to be observed when measured.
 *
 * 2) **Oracle**: Marks the solution by negating the amplitude of that state’s amplitude. This does
 * not change the measurement probabilities but lowers the mean amplitude.
 *
 * 3) **Amplification**: Performs a reflection about the mean, thus increasing the amplitude of
 * the marked state and decreasing amplitudes of other states.
 *
 * 4) **Measurement**: With higher than random probability we will observe the state marked by the
 * Oracle.
 *
 * To maximize the probability of measuring the correct state we can repeat stages 2 and 3 multiple
 * times. Each repetition brings the probability closer to 1. It turns out that we need to repeat
 * those stages [O(N^1/2) times][optimalRepetitions]. Further repetitions don't increase the
 * probability anymore but instead bring it down.
 */
internal class GroverAlgorithmTest {

    /**
     * Generates a 3-qubit oracle for use within Grover's algorithm that negates the amplitude of
     * one specific state but leaving amplitude of any other state intact.
     */
    private fun oracleGate(vararg state: Qubit): Gate {
        return gate(3) {
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
            step { C(C(Z))[0, 1, 2] }
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
        }
    }

    /**
     * Gives the most optimal number of iterations given the number of qubits in a register.
     *
     * Running the algorithm with more iterations than given by this function will decrease
     * the probability of finding marked state instead.
     */
    private fun optimalRepetitions(qubitCount: Int) = (PI * sqrt(2.0.pow(qubitCount)) / 4).toInt()

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

        val result = program(3) {
            // Initialization
            step { H[0]; H[1]; H[2] }

            repeat(optimalRepetitions(3)) {
                // Oracle
                oracle[0, 1, 2]

                // Diffusion
                step { H[0]; H[1]; H[2] }
                step { X[0]; X[1]; X[2] }
                C(C(Z))[0, 1, 2]
                step { X[0]; X[1]; X[2] }
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

    @Test
    fun `Complex Grover's algorithm`() {
        assertThat(probabilityOfSuccessfulGrover(2), closeTo(1.0000, 0.0001))
        assertThat(probabilityOfSuccessfulGrover(3), closeTo(0.9453, 0.0001))
        assertThat(probabilityOfSuccessfulGrover(4), closeTo(0.9613, 0.0001))
        assertThat(probabilityOfSuccessfulGrover(5), closeTo(0.9991, 0.0001))
        assertThat(probabilityOfSuccessfulGrover(6), closeTo(0.9966, 0.0001))
    }

    private fun probabilityOfSuccessfulGrover(size: Int): Double {
        val state = Array(size) { ZERO }

        val oracle = gate(size) {
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
            step { (1 until size).fold(Z as Gate) { acc, _ -> C(acc) }[0 until size] }
            step { state.forEachIndexed { index, qubit -> if (qubit == ZERO) X[index] } }
        }

        // Pre-compute the gate once to greatly speed up the repeated section
        val repeatedSection = gate(size) {
            // Oracle
            oracle[0 until size]

            // Diffusion
            step { repeat(size) { H[it] } }
            step { repeat(size) { X[it] } }
            step { (1 until size).fold(Z as Gate) { acc, _ -> C(acc) }[0 until size] }
            step { repeat(size) { X[it] } }
            step { repeat(size) { H[it] } }
        }

        return program(size) {
            // Initialization
            step { repeat(size) { H[it] } }

            repeat(optimalRepetitions(size)) {
                repeatedSection[0 until size]
            }
        }.probabilityOf(*state)
    }
}