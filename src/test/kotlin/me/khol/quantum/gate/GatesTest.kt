package me.khol.quantum.gate

import me.khol.quantum.math.ejml.Complex
import me.khol.quantum.math.ejml.Matrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Gate is reversible")
@Nested
class ReversibleGates {

    @DisplayName("X")
    @Test
    fun x() = isReversible(gateX)

    @DisplayName("Y")
    @Test
    fun y() = isReversible(gateY)

    @DisplayName("Z")
    @Test
    fun z() = isReversible(gateZ)

    @DisplayName("Identity")
    @Test
    fun identity() = isReversible(gateIdentity)

    @DisplayName("Hadamard")
    @Test
    fun hadamard() = isReversible(gateHadamard)

    private fun isReversible(gate: Matrix) {
        val input = Matrix(arrayOf(arrayOf(Complex.ONE, Complex.ZERO)))
        val transformed = input * gate
        val output = transformed * gate
        assertEquals(input, output)
    }
}
