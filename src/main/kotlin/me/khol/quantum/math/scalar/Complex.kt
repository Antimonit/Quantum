package me.khol.quantum.math.scalar

import java.lang.String.format

data class Complex(
    val re: Number,
    val im: Number
) : Scalar<Complex> {

    companion object {
        val ZERO = Complex(Number.ZERO, Number.ZERO)
        val HALF = Complex(sqrt(Number.HALF), Number.ZERO)
        val ONE = Complex(Number.ONE, Number.ZERO)
        val I = Complex(Number.ZERO, Number.ONE)
    }

    override operator fun unaryPlus() = Complex(re, im)
    override operator fun unaryMinus() = Complex(-re, -im)

    override operator fun plus(s: Number) = Complex(re + s, im)
    override operator fun minus(s: Number) = Complex(re - s, im)
    override operator fun times(s: Number) = Complex(re * s, im * s)
    override operator fun div(s: Number) = Complex(re / s, im / s)

    override operator fun plus(s: Complex) = Complex(re + s.re, im + s.im)
    override operator fun minus(s: Complex) = Complex(re - s.re, im - s.im)
    override operator fun times(s: Complex) = Complex(re * s.re - im * s.im, re * s.im + im * s.re)
    override operator fun div(s: Complex) = Complex(re * s.re + im * s.im, re * s.im - im * s.re) / s.square

    val reciprocal: Complex
        get() = Complex(re / square, -im / square)

    val conjugate: Complex
        get() = Complex(re, -im)

    val square: Number
        get() = re * re + im * im

    val module: Number
        get() = sqrt(square)

    override fun toString(): String = format("(%.3f, %.3fj)", re, im)
}
