package me.khol.quantum.math

data class Matrix22<S: Scalar<S>>(
    val x: Vector2<S>,
    val y: Vector2<S>
) {

    operator fun unaryPlus() = Matrix22(x, y)
    operator fun unaryMinus() = Matrix22(-x, -y)

    operator fun plus(v: S) = Matrix22(x + v, y + v)
    operator fun minus(v: S) = Matrix22(x - v, y - v)
    operator fun times(v: S) = Matrix22(x * v, y * v)
    operator fun div(v: S) = Matrix22(x / v, y / v)

    operator fun times(v: Vector2<S>): Vector2<S> {
        val t = transpose(this)
        return Vector2(dot(t.x, v), dot(t.y, v))
    }

    operator fun times(m: Matrix22<S>): Matrix22<S> {
        val t = transpose(this)
        return Matrix22(
            Vector2(dot(t.x, m.x), dot(t.y, m.x)),
            Vector2(dot(t.x, m.y), dot(t.y, m.y))
        )
    }

    fun transpose(m: Matrix22<S>) = Matrix22(Vector2(m.x.x, m.y.x), Vector2(m.x.y, m.y.y))

    override fun toString() = """
        |${x.x} ${y.x}|
        |${x.y} ${y.y}|
        """.trimIndent()
}
