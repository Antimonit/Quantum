package me.khol.quantum.math.ejml

import org.ejml.data.Complex_F64
import java.lang.String.format

class Complex {

    companion object {
        val ONE = Complex(1, 0)
        val ZERO = Complex(0, 0)
        val I = Complex(0, 1)
    }

    constructor(real: Number, imaginary: Number = 0) {
        this.complex = Complex_F64(real.toDouble(), imaginary.toDouble())
    }

    constructor(complex: Complex_F64) {
        this.complex = complex
    }

    private val complex: Complex_F64

    val re: Double get() = complex.real
    val im: Double get() = complex.imaginary

    operator fun unaryPlus() = Complex(re, im)
    operator fun unaryMinus() = Complex(-re, -im)

    operator fun plus(s: Complex) = Complex(complex.plus(s.complex))
    operator fun minus(s: Complex) = Complex(complex.minus(s.complex))
    operator fun times(s: Complex) = Complex(complex.times(s.complex))
    operator fun div(s: Complex) = Complex(complex.divide(s.complex))

    fun conjugate() = Complex(re, -im)

    val square: Double get() = complex.magnitude2

    val module: Double get() = complex.magnitude

    override fun toString(): String = format("(%.3f, %.3fj)", re, im)
}
