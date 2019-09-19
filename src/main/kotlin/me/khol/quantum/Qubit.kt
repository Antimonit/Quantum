package me.khol.quantum

import me.khol.quantum.math.scalar.Complex
import me.khol.quantum.math.scalar.Number

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
        val HALF = Qubit(Complex.HALF, Complex.HALF)
        val ONE = Qubit(Complex.ZERO, Complex.ONE)
    }

    init {
        if (alpha.square + beta.square != Number.ONE) {
            throw IllegalStateException("Invalid qubit definition for α=$alpha β=$beta. " +
                "α^2 + β^2 = ${alpha.square} + ${beta.square} = ${alpha.square + beta.square}")
        }
    }

    val probabilityZero: Number
        get() = alpha.square

    val probabilityOne: Number
        get() = beta.square
}
