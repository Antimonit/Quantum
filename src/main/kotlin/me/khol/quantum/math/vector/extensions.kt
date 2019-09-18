package me.khol.quantum.math.vector

import me.khol.quantum.math.scalar.Scalar

fun <T: Scalar<T>> dot(a: Vector2<T>, b: Vector2<T>) = a.x * b.x + a.y * b.y
