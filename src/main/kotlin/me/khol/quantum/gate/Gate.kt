package me.khol.quantum.gate

import me.khol.quantum.math.Matrix
import kotlin.math.pow

abstract class Gate {

    companion object {

        fun identity(qubits: Int): Gate = object : Gate() {
            override val qubits: Int = qubits
            override val matrix: Matrix = Matrix.identity(2 pow qubits)
        }
    }

    abstract val qubits: Int
    abstract val matrix: Matrix

    operator fun times(other: Gate): Gate {
        check(this.qubits == other.qubits) {
            "The gates with $qubits and ${other.qubits} qubits cannot be combined."
        }
        return object : Gate() {
            override val qubits: Int = this@Gate.qubits
            override val matrix: Matrix = this@Gate.matrix * other.matrix
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Gate) return false

        if (qubits != other.qubits) return false
        if (matrix != other.matrix) return false

        return true
    }

    override fun hashCode(): Int {
        var result = qubits
        result = 31 * result + matrix.hashCode()
        return result
    }
}

infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}