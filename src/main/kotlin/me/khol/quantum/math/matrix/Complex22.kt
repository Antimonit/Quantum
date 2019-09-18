package me.khol.quantum.math.matrix

import me.khol.quantum.math.scalar.Complex
import me.khol.quantum.math.vector.Complex2
import me.khol.quantum.math.scalar.Complex.Companion.ONE
import me.khol.quantum.math.scalar.Complex.Companion.ZERO

typealias Complex22 = Matrix22<Complex>

val Complex22.IDENTITY
    get() = Complex22(
        Complex2(ONE, ZERO),
        Complex2(ZERO, ONE)
    )
