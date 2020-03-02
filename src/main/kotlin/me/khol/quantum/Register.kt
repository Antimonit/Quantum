package me.khol.quantum

import me.khol.quantum.gate.Gate
import me.khol.quantum.math.Matrix
import kotlin.math.abs
import kotlin.math.log

class Register private constructor(val qubits: Int, val matrix: Matrix) {

    constructor(matrix: Matrix) : this(
        log(matrix.rows.toDouble(), 2.0).toInt(),
        matrix
    )

    constructor(vararg qubits: Qubit) : this(
        qubits.size,
        Matrix(qubits.fold(Matrix.identity(1)) { acc, qubit -> acc tensor qubit.ket })
    )

    init {
        check(matrix.cols == 1) { "Register can be only result of tensor products and thus must have only one column." }
        check(abs(matrix.sumByDouble { it.square } - 1.0) < 1e-10) { "Invalid register definition for $matrix" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Register
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

operator fun Gate.times(register: Register): Register {
    return Register(this.matrix * register.matrix)
}

operator fun Gate.times(qubit: Qubit): Qubit {
    return Qubit(this.matrix * qubit.ket)
}
