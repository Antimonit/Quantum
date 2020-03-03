package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix
import kotlin.IllegalArgumentException

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

fun Gate.withOrder(qubitOrder: List<Int>): Gate {
    val permutationGate = PermutationGate(*qubitOrder.toIntArray())
    return permutationGate * this * permutationGate
}

/**
 * Generates a Gate with permutation matrix that reorders input qubits.
 *
 * PermutationGate is technically not a valid Gate because it is not always reversible. Shouldn't
 * be used for anything other than reordering input qubits of a Gate.
 */
@Suppress("FunctionName")
internal fun PermutationGate(vararg qubitOrder: Int) = PermutationGate(qubitOrder.toList())

@Suppress("FunctionName")
internal fun PermutationGate(qubitOrder: List<Int>): Gate {
    check(qubitOrder.size <= 3) {
        "Permutation gates not yet implemented for more than 3 qubits."
    }

    check(qubitOrder.sorted() == List(qubitOrder.size) { it }) {
        "Permutation must contain all numbers between 0 and ${qubitOrder.size - 1}"
    }

    // TODO: Add a more sophisticated method to calculate the permutations.
    when (qubitOrder.size) {
        1 -> when (qubitOrder) {
            listOf(0) -> return PermutationGate0    // Also GateIdentity(1)
        }
        2 -> when (qubitOrder) {
            listOf(0, 1) -> return PermutationGate01    // Also GateIdentity(2)
            listOf(1, 0) -> return PermutationGate10    // Also GateSwap
        }
        3 -> when (qubitOrder) {
            listOf(0, 1, 2) -> return PermutationGate012    // Also GateIdentity(3)
            listOf(0, 2, 1) -> return PermutationGate021    // Also GateIdentity(1) tensor GateSwap
            listOf(1, 0, 2) -> return PermutationGate102    // Also GateSwap tensor GateIdentity(1)
            listOf(1, 2, 0) -> return PermutationGate120    // Also PermutationGate(0, 2, 1) * PermutationGate(1, 0, 2)
            listOf(2, 0, 1) -> return PermutationGate201    // Also PermutationGate(1, 0, 2) * PermutationGate(0, 2, 1)
            listOf(2, 1, 0) -> return PermutationGate210
        }
    }
    throw IllegalArgumentException("Invalid qubit order: $qubitOrder")
}

private object PermutationGate0 : Gate() {
    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO),
            listOf(ZERO, ONE)
        )
    )
}

private object PermutationGate01 : Gate() {
    override val qubits = 2
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO, ZERO, ZERO),
            listOf(ZERO, ONE, ZERO, ZERO),
            listOf(ZERO, ZERO, ONE, ZERO),
            listOf(ZERO, ZERO, ZERO, ONE)
        )
    )
}

private object PermutationGate10 : Gate() {
    override val qubits = 2
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ONE, ZERO),
            listOf(ZERO, ONE, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ONE)
        )
    )
}

private object PermutationGate012 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}

private object PermutationGate021 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}

private object PermutationGate102 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}

private object PermutationGate120 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}

private object PermutationGate201 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}

private object PermutationGate210 : Gate() {
    override val qubits = 3
    override val matrix = Matrix(
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
    )
}
