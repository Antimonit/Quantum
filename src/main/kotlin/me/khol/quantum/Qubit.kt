package me.khol.quantum

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.abs

/**
 * Quantum bit is described by:
 *   α|0> + β|1>
 * where α and β are complex numbers, satisfying:
 *   |α|^2 + |β|^2 = 1
 *
 * Qubit can be also visualised as a unit vector in a two-dimensional complex vector space.
 * X axis represents α and Y axis represents β.
 * In such system:
 *  * (1,0) point corresponds |0>
 *  * (0,1) point corresponds |1>
 *  * other points on a unit circle represents x|0> + y|1>
 */
data class Qubit(
    val alpha: Complex,
    val beta: Complex
) {

    companion object {
        val ZERO = Qubit(Complex.ONE, Complex.ZERO)
        val ONE = Qubit(Complex.ZERO, Complex.ONE)
    }

    init {
        if (abs(alpha.square + beta.square - 1.0) >= 1e-10) {
            throw IllegalStateException("Invalid qubit definition for α=$alpha β=$beta. " +
                "α^2 + β^2 = ${alpha.square} + ${beta.square} = ${alpha.square + beta.square}")
        }
    }

    val probabilityZero: Double
        get() = alpha.square

    val probabilityOne: Double
        get() = beta.square

    val ket by lazy { Matrix(2, 1, alpha, beta) }
    val bra by lazy { Matrix(1, 2, alpha, beta) }
}
