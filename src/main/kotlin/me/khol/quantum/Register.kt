package me.khol.quantum

import me.khol.quantum.gate.Gate
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.abs
import kotlin.math.log

class Register private constructor(val qubits: Int, val matrix: Matrix) {

    constructor(matrix: Matrix) : this(
        log(matrix.rows.toDouble(), 2.0).toInt(),
        matrix
    )

    constructor(vararg qubits: Qubit) : this(qubits.toList())

    constructor(qubits: List<Qubit>) : this(
        qubits.size,
        Matrix(qubits.fold(Matrix.identity(1)) { acc, qubit -> acc tensor qubit.ket })
    )

    init {
        check(matrix.cols == 1) { "Register can be only result of tensor products and thus must have only one column." }
        check(abs(matrix.sumByDouble { it.square } - 1.0) < 1e-10) { "Invalid register definition for $matrix" }
    }

    /**
     * Holds the same qubit states as this Register but the global phase shift is eliminated.
     * This is mostly used for verification that two registers actually represent the same state.
     */
    val normalized: Register by lazy {
        // Choose any element that has defined global phase shift.
        // There must be at least one such element, otherwise the probabilities would
        // all equal to zero and the register would be invalid.
        val alpha = matrix.first { it != Complex.ZERO }
        Register(qubits, Matrix(matrix.rows, matrix.cols,
            matrix.map { Complex.fromPolar(r = it.r, theta = alpha.relativeTheta(it)) }
        ))
    }

    operator fun times(other: Complex) = Register(qubits, matrix * other)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Register
        if (qubits == other.qubits && matrix == other.matrix) return true
        if (normalized.matrix == other.normalized.matrix) return true
        return false
    }

    override fun hashCode(): Int {
        var result = qubits
        result = 31 * result + normalized.matrix.hashCode()
        return result
    }

    override fun toString(): String {
        return "Register ${super.toString()}:\n$matrix\n"
    }
}

operator fun Gate.times(register: Register): Register {
    return Register(this.matrix * register.matrix)
}

operator fun Gate.times(qubit: Qubit): Qubit {
    return Qubit(this.matrix * qubit.ket)
}

operator fun Complex.times(register: Register) = register * this
