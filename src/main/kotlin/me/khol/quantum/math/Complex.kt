package me.khol.quantum.math

import org.ejml.data.Complex_F64
import java.lang.String.format
import kotlin.Double.Companion.NaN
import kotlin.math.*

class Complex private constructor(
    private val complex: Complex_F64
) {

    constructor(re: Number = 0, im: Number = 0) : this(
        Complex_F64(re.toDouble(), im.toDouble())
    )

    companion object {
        val ONE = Complex(1, 0)
        val ZERO = Complex(0, 0)
        val I = Complex(0, 1)

        /**
         * Construct a complex number from polar coordinates.
         *
         * If [the radius][radius] is zero in length then [the angle][theta] is ignored and zero complex
         * number is returned immediately. This allows to create a complex number even if the angle
         * is NaN.
         */
        fun fromPolar(theta: Number, radius: Number = 1): Complex {
            return if (radius.toDouble() == 0.0) {
                ZERO
            } else {
                Complex(
                    radius.toDouble() * cos(theta.toDouble()),
                    radius.toDouble() * sin(theta.toDouble())
                )
            }
        }
    }

    val re: Double = complex.real
    val im: Double = complex.imaginary

    operator fun unaryPlus() = Complex(re, im)
    operator fun unaryMinus() = Complex(-re, -im)

    operator fun plus(s: Complex) = Complex(complex.plus(s.complex))
    operator fun minus(s: Complex) = Complex(complex.minus(s.complex))
    operator fun times(s: Complex) = Complex(complex.times(s.complex))
    operator fun div(s: Complex) = Complex(complex.divide(s.complex))

    fun conjugate() = Complex(re, -im)

    val square: Double get() = complex.magnitude2

    /**
     * Radius in polar coordinates.
     */
    val radius: Double get() = complex.magnitude

    /**
     * Angle in radians in polar coordinates.
     * Returns NaN if the complex number has zero radius.
     */
    val theta: Double get() = if (this == ZERO) {
        NaN
    } else {
        atan2(complex.imaginary, complex.real)
    }

    /**
     * Difference between polar angles of two complex numbers.
     * Returns NaN if this or the other complex number has zero radius.
     */
    fun relativeTheta(other: Complex): Double {
        return (other.theta - this.theta + 2 * PI) % (2 * PI)
    }

    fun toSimpleString(real: Boolean, integer: Boolean): String = when {
        real && integer -> format("%.0f", re)
        real && !integer -> format("%.3f", re)
        integer -> format("(%.0f, %.0f)", re, im)
        else -> format("(%.3f, %.3fj)", re, im)
    }

    override fun toString(): String = toSimpleString(real = false, integer = false)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Complex
        if (abs(re - other.re) >= 1e-10) return false
        if (abs(im - other.im) >= 1e-10) return false
        return true
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
