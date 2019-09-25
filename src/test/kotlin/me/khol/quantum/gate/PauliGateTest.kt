package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PauliGateTest {

    @DisplayName("Pauli gates magic")
    @Test
    fun pauliGatesMagic() {
        val result = -GateX.matrix * GateY.matrix * GateZ.matrix * Complex.I
        assertEquals(Gate.identity(1), result)
    }
}