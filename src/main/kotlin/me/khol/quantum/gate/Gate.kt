package me.khol.quantum.gate

import me.khol.quantum.math.Matrix
import kotlin.math.pow

interface Gate {

    companion object {
        fun identity(qubits: Int) = Matrix.identity(2 pow qubits)
    }

    val qubits: Int
    val matrix: Matrix
}

infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}