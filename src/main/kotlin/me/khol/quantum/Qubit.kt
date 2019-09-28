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
class Qubit(
    val alpha: Complex,
    val beta: Complex
) : Matrix(2, 1, alpha, beta) {

    companion object {
        val ZERO = Qubit(Complex.ONE, Complex.ZERO)
        val ONE = Qubit(Complex.ZERO, Complex.ONE)
    }

    init {
        check(abs(alpha.square + beta.square - 1.0) < 1e-10) {
            "Invalid qubit definition for α=$alpha β=$beta. " +
                "α^2 + β^2 = ${alpha.square} + ${beta.square} = ${alpha.square + beta.square}"
        }
    }

    val probabilityZero: Double
        get() = alpha.square

    val probabilityOne: Double
        get() = beta.square

    val ket by lazy { this }
    val bra by lazy { transpose() }

    /**
     * Calculates the dot product of this and [other] qubits.
     * This results into 1x1 matrix.
     */
    infix fun dot(other: Qubit): Matrix {
        return this.bra tensor other.ket
    }

    /**
     * Calculates the cross product of this and [other] qubits.
     * This results into 2x2 matrix.
     */
    infix fun cross(other: Qubit): Matrix {
        return this.ket tensor other.bra
    }

    /**
     * Calculates the tensor product of this and [other] qubits
     * This results into 4x1 matrix.
     */
    infix fun x(other: Qubit): Matrix {
        return this.ket tensor other.ket
    }
}
