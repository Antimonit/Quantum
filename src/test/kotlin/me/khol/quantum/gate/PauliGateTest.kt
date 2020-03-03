package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PauliGateTest {

    @DisplayName("Pauli gates magic")
    @Test
    fun pauliGatesMagic() {
        assertThat(
            -(GateX * GateY * GateZ).matrix * Complex.I,
            equalTo(GateIdentity.matrix)
        )
    }
}