package me.khol.quantum.math.vector

import me.khol.quantum.math.scalar.Scalar
import me.khol.quantum.math.scalar.Number

data class Vector2<S: Scalar<S>>(
    val x: S,
    val y: S
) {

    operator fun unaryPlus() = Vector2(x, y)
    operator fun unaryMinus() = Vector2(-x, -y)

    operator fun plus(s: Number) = Vector2(x + s, y)
    operator fun minus(s: Number) = Vector2(x - s, y)
    operator fun times(s: Number) = Vector2(x * s, y * s)
    operator fun div(s: Number) = Vector2(x / s, y / s)

    operator fun plus(s: S) = Vector2(x + s, y + s)
    operator fun minus(s: S) = Vector2(x - s, y - s)
    operator fun times(s: S) = Vector2(x * s, y * s)
    operator fun div(s: S) = Vector2(x / s, y / s)

    operator fun plus(v: Vector2<S>) = Vector2(x + v.x, y + v.y)
    operator fun minus(v: Vector2<S>) = Vector2(x - v.x, y - v.y)
    operator fun times(v: Vector2<S>) = Vector2(x * v.x, y * v.y)
    operator fun div(v: Vector2<S>) = Vector2(x / v.x, y / v.y)
}
