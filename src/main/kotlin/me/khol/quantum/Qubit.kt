package me.khol.quantum

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random

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
) {

    constructor(matrix: Matrix) : this(matrix[0, 0], matrix[0, 1])

    companion object {
        val ZERO = Qubit(Complex.ONE, Complex.ZERO)
        val ONE = Qubit(Complex.ZERO, Complex.ONE)

        fun random(): Qubit {
            val values = List(4) { Random.nextDouble() }
            val sum = values.sum()
            val normalized = values.map { sqrt(it / sum) }
            val alpha = Complex(normalized[0], normalized[1])
            val beta = Complex(normalized[2], normalized[3])
            return Qubit(alpha, beta)
        }
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

    /**
     * Holds the same qubit state as this qubit but the global phase shift is eliminated.
     * This is mostly used for verification that two qubits actually represent the same state.
     */
    val normalized: Qubit by lazy {
        val nonZero = if (alpha != Complex.ZERO) alpha else beta
        Qubit(
            Complex.fromPolar(radius = alpha.radius, theta = nonZero.relativeTheta(alpha)),
            Complex.fromPolar(radius = beta.radius, theta = nonZero.relativeTheta(beta))
        )
    }

    val ket by lazy { Matrix(2, 1, alpha, beta) }
    val bra by lazy { Matrix(1, 2, alpha, beta) }

    operator fun times(other: Complex): Qubit {
        return Qubit(alpha * other, beta * other)
    }

    /**
     * Calculates the dot product of this and [other] qubits.
     * This corresponds to <0|0> and results into 1x1 matrix.
     */
    infix fun dot(other: Qubit): Matrix {
        return this.bra * other.ket
    }

    /**
     * Calculates the cross product of this and [other] qubits.
     * This corresponds to |0><0| and results into 2x2 matrix.
     */
    infix fun cross(other: Qubit): Matrix {
        return this.ket * other.bra
    }

    /**
     * Calculates the tensor product of this and [other] qubits
     * This corresponds to |0>|0> and results into 4x1 matrix.
     */
    infix fun x(other: Qubit): Matrix {
        return this.ket tensor other.ket
    }

    override fun toString(): String = when (this) {
        ZERO -> "ZERO"
        ONE -> "ONE"
        else -> "Q" + with(listOf(alpha, beta)) {
            val real = all(Complex::isReal)
            val integer = all(Complex::isInteger)
            joinToString("; ", "[", "]") { it.toSimpleString(real, integer) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Qubit
        if (alpha == other.alpha && beta == other.beta) return true
        // Even though two Qubits may be described by different complex numbers they may actually
        // represent the same qubit state. There are infinitely many representations of the same
        // qubit state which differ only by their global phase shift. Global phase shift does not
        // alter measurement probabilities when a qubit is measured.
        if (normalized.alpha == other.normalized.alpha &&
            normalized.beta == other.normalized.beta) return true
        return false
    }

    override fun hashCode(): Int {
        var result = normalized.alpha.hashCode()
        result = 31 * result + normalized.beta.hashCode()
        return result
    }
}

operator fun Complex.times(qubit: Qubit) = qubit * this
