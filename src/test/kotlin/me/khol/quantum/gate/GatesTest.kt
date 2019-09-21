package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Gate is reversible")
@Nested
class ReversibleGates {

    @DisplayName("X")
    @Test
    fun x() = isReversible(GateX)

    @DisplayName("Y")
    @Test
    fun y() = isReversible(GateY)

    @DisplayName("Z")
    @Test
    fun z() = isReversible(GateZ)

    @DisplayName("Identity")
    @Test
    fun identity() = isReversible(GateIdentity1)

    @DisplayName("Hadamard")
    @Test
    fun hadamard() = isReversible(GateHadamard)

    private fun isReversible(gate: Gate) {
        val input = Matrix(arrayOf(arrayOf(Complex.ONE, Complex.ZERO)))
        val transformed = input * gate.matrix
        val output = transformed * gate.matrix
        assertEquals(input, output)
    }
}
