package me.khol.quantum.math.scalar

import kotlin.math.abs
import kotlin.math.sqrt

data class Number(val value: Double) : Scalar<Number> {

    companion object {
        val ZERO = Number(0.0)
        val HALF = Number(0.5)
        val ONE = Number(1.0)
    }

    override operator fun unaryPlus() = Number(value.unaryPlus())
    override operator fun unaryMinus() = Number(value.unaryMinus())

    override operator fun plus(s: Number) = Number(value.plus(s.value))
    override operator fun minus(s: Number) = Number(value.minus(s.value))
    override operator fun times(s: Number) = Number(value.times(s.value))
    override operator fun div(s: Number) = Number(value.div(s.value))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Number

        if (abs(this - other).value >= 1e-10) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}

fun abs(n: Number): Number = Number(abs(n.value))
fun sqrt(x: Number): Number = Number(sqrt(x.value))
