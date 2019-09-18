package me.khol.quantum.gate

import me.khol.quantum.math.matrix.Complex22
import me.khol.quantum.math.scalar.Complex.Companion.ONE
import me.khol.quantum.math.scalar.Complex.Companion.ZERO
import me.khol.quantum.math.scalar.Complex.Companion.I
import me.khol.quantum.math.vector.Complex2
import kotlin.math.sqrt

val gateX = Complex22(
    Complex2(ZERO, ONE),
    Complex2(ONE, ZERO)
)

val gateY = Complex22(
    Complex2(ZERO, -I),
    Complex2(I, ZERO)
)

val gateZ = Complex22(
    Complex2(ONE, ZERO),
    Complex2(ZERO, -ONE)
)

val gateIdentity = Complex22(
    Complex2(ONE, ZERO),
    Complex2(ZERO, ONE)
)

val gateHadamard = Complex22(
    Complex2(ONE, ONE),
    Complex2(ONE, -ONE)
) * sqrt(0.5)
