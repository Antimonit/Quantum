package me.khol.quantum.gate

import me.khol.quantum.math.Matrix

abstract class Gate {

    abstract val qubits: Int
    abstract val matrix: Matrix

    val adjoint: Gate by lazy {
        object : Gate() {
            override val qubits: Int = this@Gate.qubits
            override val matrix: Matrix = this@Gate.matrix.conjugateTranspose()
        }
    }

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
