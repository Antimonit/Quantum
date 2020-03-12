package me.khol.quantum.math

import kotlin.math.pow

infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}
