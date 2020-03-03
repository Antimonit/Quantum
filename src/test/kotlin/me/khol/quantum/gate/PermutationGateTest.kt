package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Register
import me.khol.quantum.times
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PermutationGateTest {

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
        (1..3).forEach { permutationSize ->
            val indices = List(permutationSize) { it }
            val qubits = indices.map { Qubit.random() }
            val inputRegister = Register(qubits)
            indices.permutations().forEach { permutedIndices: List<Int> ->
                val gate = PermutationGate(permutedIndices)
                val actualRegister = gate * inputRegister
                val expectedRegister = Register(permutedIndices.map { qubits[it] })
                assertEquals(expectedRegister.matrix, actualRegister.matrix)
            }
        }
    }
}
