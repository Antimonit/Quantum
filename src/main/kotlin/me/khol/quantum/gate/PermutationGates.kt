package me.khol.quantum.gate

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
    val permutationGate = permutationGate(*qubitOrder.toIntArray())
    return permutationGate.adjoint * this * permutationGate
}

private val basicSwapGates: MutableMap<Int, List<Gate>> = mutableMapOf(
    1 to listOf(),
    2 to listOf(GateSwap)
)

private fun basicSwapGates(size: Int): List<Gate> {
    check(size > 0)
    return basicSwapGates.getOrPut(size) {
        val previous = basicSwapGates(size - 1)
        previous.map { it tensor GateIdentity } + (GateIdentity tensor previous.last())
    }
}

private val permutationsCache: MutableMap<List<Int>, Gate> = mutableMapOf()

/**
 * Generates a Gate with permutation matrix that reorders input qubits.
 *
 * PermutationGate is technically not a valid Gate because it is not always reversible. Shouldn't
 * be used for anything other than reordering input qubits of a Gate.
 */
fun permutationGate(vararg order: Int) = permutationGate(order.toList())

fun permutationGate(order: List<Int>): Gate {
    check(order.sorted() == List(order.size) { it }) {
        "Permutation must contain all numbers between 0 and ${order.size - 1}"
    }

    return permutationsCache.getOrPut(order) {
        val swapGates = basicSwapGates(order.size)
        var gate = GateIdentity(order.size)
        val order = order.toMutableList()
        for (x in 0 until order.size - 1) {
            for (y in 0 until order.size - x - 1) {
                if (order[y] >= order[y + 1]) {
                    order[y] = order[y + 1].also { order[y + 1] = order[y] }
                    gate *= swapGates[y]
                }
            }
        }
        gate
    }
}
