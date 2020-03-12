package me.khol.quantum.gate

import me.khol.quantum.math.pow

/**
 * Swaps bits of [this] integer at positions [a] and [b] (least significant digit has position 0).
 */
private fun Int.swapBits(a: Int, b: Int): Int {
    val bitA: Int = this shr a and 1
    val bitB: Int = this shr b and 1
    var x = bitA xor bitB
    x = x shl a or (x shl b)
    return this xor x
}

private fun List<Int>.swapBits(a: Int, b: Int) = map { it.swapBits(a, b) }

private val permutationVectorsCache: MutableMap<Permutation, List<Int>> = mutableMapOf()

fun permutationVector(order: Permutation): List<Int> {
    check(order.sorted() == List(order.size) { it }) {
        "Permutation must contain all numbers between 0 and ${order.size - 1}"
    }

    return permutationVectorsCache.getOrPut(order) {
        val size = order.size
        var permutation = List(2 pow size) { it }
        val sort = order.toMutableList()
        for (x in 0 until size - 1) {
            for (y in 0 until size - x - 1) {
                if (sort[y] >= sort[y + 1]) {
                    sort[y] = sort[y + 1].also { sort[y + 1] = sort[y] }
                    permutation = permutation.swapBits(size - y - 1, size - (y + 1) - 1)
                }
            }
        }
        permutation
    }
}