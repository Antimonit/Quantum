package me.khol.quantum.math

import kotlin.math.pow

/**
 * [pow] extension on integers.
 */
infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}

/**
 * Convert [this] integer to a list of binary digits where the first element of the list
 * is the most significant digit of the number. Contains exactly [size] elements.
 */
fun Int.toBinaryDigits(size: Int): List<Int> {
    return (size - 1 downTo 0).map { this shr it and 1 }
}

/**
 * Convert [this] list of binary digits to an integer where the first element of the list
 * is the most significant digit of the number.
 */
fun List<Int>.fromBinaryDigits(): Int {
    return fold(0) { acc, value -> (acc shl 1) + value}
}
