package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Register
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix
import me.khol.quantum.times
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class PermutationGateTest {

    @Test
    fun `Permutation 0`() {
        assertThat(
            permutationGate(0).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO),
                    listOf(ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 01`() {
        assertThat(
            permutationGate(0, 1).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 10`() {
        assertThat(
            permutationGate(1, 0).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 012`() {
        assertThat(
            permutationGate(0, 1, 2).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 021`() {
        assertThat(
            permutationGate(0, 2, 1).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 102`() {
        assertThat(
            permutationGate(1, 0, 2).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 120`() {
        assertThat(
            permutationGate(1, 2, 0).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 201`() {
        assertThat(
            permutationGate(2, 0, 1).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    @Test
    fun `Permutation 210`() {
        assertThat(
            permutationGate(2, 1, 0).matrix,
            equalTo(Matrix(
                listOf(
                    listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO),
                    listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
                    listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE)
                )
            ))
        )
    }

    private fun <E> List<E>.permutations(): List<List<E>> {
        return if (isEmpty()) {
            listOf(emptyList())
        } else {
            val item = first()
            drop(1).permutations().flatMap { permutations ->
                (0..permutations.size).map { index ->
                    permutations.toMutableList().apply { add(index, item) }
                }
            }
        }
    }

    @Test
    fun `All permutations of PermutationGate correctly permute qubits`() {
        (1..4).forEach { permutationSize ->
            val indices = List(permutationSize) { it }
            val qubits = indices.map { Qubit.random() }
            val inputRegister = Register(qubits)
            indices.permutations().forEach { permutedIndices: List<Int> ->
                assertThat(
                    permutationGate(permutedIndices) * inputRegister,
                    equalTo(Register(permutedIndices.map { qubits[it] }))
                )
            }
        }
    }
}
