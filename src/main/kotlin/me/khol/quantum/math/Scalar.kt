package me.khol.quantum.math

interface Scalar<S : Scalar<S>> {

    operator fun unaryPlus(): S
    operator fun unaryMinus(): S

    operator fun plus(s: S): S
    operator fun minus(s: S): S
    operator fun times(s: S): S
    operator fun div(s: S): S
}
