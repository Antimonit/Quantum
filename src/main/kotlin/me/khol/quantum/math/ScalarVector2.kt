package me.khol.quantum.math

data class ScalarVector2<S: Scalar<S>>(
    val x: S,
    val y: S
) {

    operator fun unaryPlus() = ScalarVector2(x, y)
    operator fun unaryMinus() = ScalarVector2(-x, -y)

    operator fun plus(v: S) = ScalarVector2(x + v, y + v)
    operator fun minus(v: S) = ScalarVector2(x - v, y - v)
    operator fun times(v: S) = ScalarVector2(x * v, y * v)
    operator fun div(v: S) = ScalarVector2(x / v, y / v)

    operator fun plus(v: ScalarVector2<S>) = ScalarVector2(x + v.x, y + v.y)
    operator fun minus(v: ScalarVector2<S>) = ScalarVector2(x - v.x, y - v.y)
    operator fun times(v: ScalarVector2<S>) = ScalarVector2(x * v.x, y * v.y)
    operator fun div(v: ScalarVector2<S>) = ScalarVector2(x / v.x, y / v.y)
}
