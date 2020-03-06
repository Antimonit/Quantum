package me.khol.quantum.math

import org.ejml.data.Complex_F64
import java.lang.String.format
import kotlin.Double.Companion.NaN
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

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

        fun fromPolar(theta: Number, r: Number = 1) = Complex(
            r.toDouble() * cos(theta.toDouble()),
            r.toDouble() * sin(theta.toDouble())
        )
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
    val r: Double get() = complex.magnitude

    /**
     * Angle in polar coordinates.
     */
    val theta: Double get() = if (this == ZERO) {
        NaN
    } else {
        atan2(complex.imaginary, complex.real)
    }

    override fun toString(): String = format("(%.3f, %.3fj)", re, im)

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
