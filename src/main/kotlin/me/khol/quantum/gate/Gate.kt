package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.Register
import me.khol.quantum.math.Matrix

abstract class Gate {

    abstract val qubits: Int
    abstract val matrix: Matrix

    val adjoint: Gate by lazy { AdjointGate(this) }

    operator fun times(other: Gate): Gate = TimesGate(this, other)

    operator fun times(register: Register): Register = Register(this.matrix * register.matrix)

    operator fun times(qubit: Qubit): Qubit = Qubit(this.matrix * qubit.ket)

    infix fun tensor(other: Gate): Gate = TensorGate(this, other)

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

    override fun toString(): String {
        return matrix.toString()
    }
}

private class TimesGate(gate: Gate, other: Gate) : Gate() {

    init {
        check(gate.qubits == other.qubits) {
            "The gates with $qubits and ${other.qubits} qubits cannot be combined."
        }
    }

    override val qubits: Int = gate.qubits
    override val matrix: Matrix = gate.matrix * other.matrix
}

private class TensorGate(gate: Gate, other: Gate) : Gate() {

    override val qubits: Int = gate.qubits + other.qubits
    override val matrix: Matrix = gate.matrix tensor other.matrix
}

private class AdjointGate(gate: Gate) : Gate() {

    override val qubits: Int = gate.qubits
    override val matrix: Matrix = gate.matrix.conjugateTranspose()
}
