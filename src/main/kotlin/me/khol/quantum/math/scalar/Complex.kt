package me.khol.quantum.math.scalar

import java.lang.String.format
import kotlin.math.abs
import kotlin.math.sqrt

data class Complex(
    val re: Double,
    val im: Double
): Scalar<Complex> {

    companion object {
        val ZERO = Complex(0.0, 0.0)
        val HALF = Complex(sqrt(0.5), 0.0)
        val ONE = Complex(1.0, 0.0)
        val I = Complex(0.0, 1.0)
    }

    override operator fun unaryPlus() = Complex(re, im)
    override operator fun unaryMinus() = Complex(-re, -im)

    override operator fun plus(s: Double) = Complex(re + s, im)
    override operator fun minus(s: Double) = Complex(re - s, im)
    override operator fun times(s: Double) = Complex(re * s, im * s)
    override operator fun div(s: Double) = Complex(re / s, im / s)

    override operator fun plus(s: Complex) = Complex(re + s.re, im + s.im)
    override operator fun minus(s: Complex) = Complex(re - s.re, im - s.im)
    override operator fun times(s: Complex) = Complex(re * s.re - im * s.im, re * s.im + im * s.re)
    override operator fun div(s: Complex) = Complex(re * s.re + im * s.im, re * s.im - im * s.re) / s.square

    val reciprocal: Complex
        get() = Complex(re / square, -im / square)

    val conjugate: Complex
        get() = Complex(re, -im)

    val square: Double
        get() = re * re + im * im

    val module: Double
        get() = sqrt(square)

    override fun toString(): String = format("(%.3f, %.3fj)", re, im)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Complex

        if (!equals(re, other.re)) return false
        if (!equals(im, other.im)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}

fun equals(a: Double, b: Double) = abs(a - b) < 1e-10
