package me.khol.quantum.math.scalar

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

    override operator fun plus(s: Complex) = Complex(re + s.re, im + s.im)
    override operator fun minus(s: Complex) = Complex(re - s.re, im - s.im)
    override operator fun times(s: Complex) = Complex(re * s.re - im * s.im, re * s.im + im * s.re)
    override operator fun div(s: Complex) = Complex(re * s.re + im * s.im, re * s.im - im * s.re) / s.square

    operator fun times(s: Double) = Complex(re * s, im * s)
    operator fun div(s: Double) = Complex(re / s, im / s)

    val reciprocal: Complex
        get() = Complex(re / square, -im / square)

    val conjugate: Complex
        get() = Complex(re, -im)

    val square: Double
        get() = re * re + im * im

    val module: Double
        get() = sqrt(square)
}
