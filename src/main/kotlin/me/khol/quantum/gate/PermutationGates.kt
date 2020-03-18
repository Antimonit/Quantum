package me.khol.quantum.gate

import me.khol.quantum.util.WeakCache
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix

typealias Permutation = List<Int>

/**
 * Reorders input and output qubits of [this] gate.
 *
 * This is most useful when we need to use some kind of a controlled gate where the control and
 * target qubits are in a different order than the original gate is expecting them.
 *
 * For example, a [GateCCNot] expects the first two qubits to be control qubits (C0, C1) and the
 * last qubit to be target (T) qubit (C0 C1 T). Calling `GateCCNot.withOrder(0, 2, 1)` will return
 * a Gate whose target qubit sits in between the control qubits (C0 T C1).
 */
fun Gate.withOrder(vararg qubitOrder: Int) = withOrder(qubitOrder.toList())

fun Gate.withOrder(qubitOrder: Permutation): Gate {
    val permutationGate = permutationGate(*qubitOrder.toIntArray())
    return permutationGate.adjoint * this * permutationGate
}

private val permutationsCache: WeakCache<Permutation, Gate> = WeakCache()

/**
 * Generates a Gate with permutation matrix that reorders input qubits.
 *
 * PermutationGate is technically not a valid Gate because it is not always reversible. Shouldn't
 * be used for anything other than reordering input qubits of a Gate.
 */
fun permutationGate(vararg order: Int) = permutationGate(order.toList())

fun permutationGate(order: Permutation): Gate {
    return permutationsCache.getOrPut(order) { PermutationGate(order) }
}

private class PermutationGate(order: Permutation) : Gate() {

    override val qubits = order.size
    override val matrix: Matrix = run {
        val permutation = permutationVector(order)
        Matrix(permutation.map { permutedIndex ->
            List(permutation.size) { if (it == permutedIndex) Complex.ONE else Complex.ZERO }
        })
    }
}
