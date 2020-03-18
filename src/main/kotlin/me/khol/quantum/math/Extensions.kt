package me.khol.quantum.math

import me.khol.quantum.Qubit
import kotlin.math.pow

/**
 * [pow] extension on integers.
 */
infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}

/**
 * Convert a binary representation of an integer to a list of qubits where the first qubit
 * of the list is the most significant digit of the number. Contains exactly [size] elements.
 *
 * @see toIndex Inverted operation.
 */
fun Int.toQubits(size: Int): List<Qubit> {
    return (size - 1 downTo 0)
        .map { this shr it and 1 }
        .map { if (it == 0) Qubit.ZERO else Qubit.ONE }
}

/**
 * Convert a list of qubits to a binary representation of an integer where the first qubit
 * of the list is the most significant digit of the number.
 *
 * @see toQubits Inverted operation.
 */
fun List<Qubit>.toIndex(): Int {
    return map { if (it == Qubit.ZERO) 0 else 1 }
        .fold(0) { acc, value -> (acc shl 1) + value }
}
