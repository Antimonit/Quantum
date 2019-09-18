package me.khol.quantum.math.vector

import me.khol.quantum.math.scalar.Scalar

data class Vector2<S: Scalar<S>>(
    val x: S,
    val y: S
) {

    operator fun unaryPlus() = Vector2(x, y)
    operator fun unaryMinus() = Vector2(-x, -y)

    operator fun plus(v: S) = Vector2(x + v, y + v)
    operator fun minus(v: S) = Vector2(x - v, y - v)
    operator fun times(v: S) = Vector2(x * v, y * v)
    operator fun div(v: S) = Vector2(x / v, y / v)

    operator fun plus(v: Vector2<S>) = Vector2(x + v.x, y + v.y)
    operator fun minus(v: Vector2<S>) = Vector2(x - v.x, y - v.y)
    operator fun times(v: Vector2<S>) = Vector2(x * v.x, y * v.y)
    operator fun div(v: Vector2<S>) = Vector2(x / v.x, y / v.y)
}
