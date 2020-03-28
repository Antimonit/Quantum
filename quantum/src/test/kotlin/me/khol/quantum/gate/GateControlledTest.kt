package me.khol.quantum.gate

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class GateControlledTest {

    @Test
    fun `Controlled Not is CNot`() {
        assertThat(GateControlled(GateNot), equalTo<Gate>(GateCNot))
    }

    @Test
    fun `Controlled CNot is CCNot`() {
        assertThat(GateControlled(GateCNot), equalTo<Gate>(GateCCNot))
    }

    @Test
    fun `Controlled Controlled Not is CCNot`() {
        assertThat(GateControlled(GateControlled(GateNot)), equalTo<Gate>(GateCCNot))
    }

    @Test
    fun `Controlled Swap is CSwap`() {
        assertThat(GateControlled(GateSwap), equalTo<Gate>(GateCSwap))
    }
}
