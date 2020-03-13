package me.khol.quantum.gate

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.PI

class GatePhaseTest {

    @Test
    fun `Phase by PI radians is Z gate`() {
        assertThat(GatePhase(PI), equalTo<Gate>(GateZ))
    }

    @Test
    fun `Phase by half PI radians is S gate`() {
        assertThat(GatePhase(PI / 2), equalTo<Gate>(GateS))
    }

    @Test
    fun `Phase by quarter PI radians is T gate`() {
        assertThat(GatePhase(PI / 4), equalTo<Gate>(GateT))
    }
}
