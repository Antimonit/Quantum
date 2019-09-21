package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.I
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix
import kotlin.math.sqrt

val gateX = Matrix(
    arrayOf(
        arrayOf(ZERO, ONE),
        arrayOf(ONE, ZERO)
    )
)

val gateY = Matrix(
    arrayOf(
        arrayOf(ZERO, -I),
        arrayOf(I, ZERO)
    )
)

val gateZ = Matrix(
    arrayOf(
        arrayOf(ONE, ZERO),
        arrayOf(ZERO, -ONE)
    )
)

val gateIdentity = Matrix(
    arrayOf(
        arrayOf(ONE, ZERO),
        arrayOf(ZERO, ONE)
    )
)

val gateHadamard = Matrix(
    arrayOf(
        arrayOf(ONE, ONE),
        arrayOf(ONE, -ONE)
    )
) * sqrt(0.5)
