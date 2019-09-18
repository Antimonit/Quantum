package me.khol.quantum.math

import kotlin.math.sqrt

data class Complex(
    val re: Double,
    val im: Double
) {

    companion object {
        val ZERO = Complex(0.0, 0.0)
        val ONE = Complex(1.0, 0.0)
    }

    operator fun unaryPlus() = Complex(re, im)
    operator fun unaryMinus() = Complex(-re, -im)

    operator fun plus(s: Complex) = Complex(re + s.re, im + s.im)
    operator fun minus(s: Complex) = Complex(re - s.re, im - s.im)
    operator fun times(s: Complex) = Complex(re * s.re - im * s.im, re * s.im + im * s.re)
    operator fun div(s: Complex) = Complex(re * s.re + im * s.im, re * s.im - im * s.re) / s.square

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
