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
 * is the most significant digit of [this] number. Contains exactly [size] elements.
 */
fun Int.toBinaryDigits(size: Int): List<Int> {
    return (size - 1 downTo 0).map { this shr it and 1 }
}
