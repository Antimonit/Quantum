package me.khol.quantum.gate

import me.khol.quantum.math.matrix.Matrix22
import me.khol.quantum.math.scalar.Complex
import me.khol.quantum.math.vector.Complex2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GatesTest {

    @Test
    fun `X gate is reversible`() {
        `gate is reversible`(gateX)
    }

    @Test
    fun `Y gate is reversible`() {
        `gate is reversible`(gateY)
    }

    @Test
    fun `Z gate is reversible`() {
        `gate is reversible`(gateZ)
    }

    @Test
    fun `Identity gate is reversible`() {
        `gate is reversible`(gateIdentity)
    }

    private fun `gate is reversible`(gate: Matrix22<Complex>) {
        val input = Complex2(Complex.ONE, Complex.ZERO)
        val transformed = gate * input
        val output = gate * transformed
        assertEquals(input, output)
    }
}
