package me.khol.quantum.gate

import me.khol.quantum.math.Matrix
import kotlin.math.pow

interface Gate {

    companion object {

        fun identity(qubits: Int): Gate = object : Gate {
            override val qubits: Int = qubits
            override val matrix: Matrix = Matrix.identity(2 pow qubits)
        }
    }

    val qubits: Int
    val matrix: Matrix

    operator fun times(other: Gate): Gate {
        check(this.qubits == other.qubits) {
            "The gates with $qubits and ${other.qubits} qubits cannot be combined."
        }
        return object : Gate {
            override val qubits: Int = this@Gate.qubits
            override val matrix: Matrix = this@Gate.matrix * other.matrix
        }
    }
}

infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}