package me.khol.quantum.gate

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GateSwapRootTest {

    @DisplayName("Swap root gate is root of Swap gate")
    @Test
    fun gateSwapRootIsRootOfSwapGate() {
        assertThat(GateSwapRoot * GateSwapRoot, equalTo<Gate>(GateSwap))
    }
}
