package me.khol.quantum

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
}
