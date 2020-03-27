package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.math.Complex
import me.khol.quantum.times
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.PI

class GateRotationTest {

    @Test
    fun `PI rotations about X axis is X Gate`() {
        // Gates differ only in the global phase that we ultimately don't care about
        val qubit = Qubit.random()
        assertThat(GateRx(PI) * qubit, equalTo(GateX * qubit))
        assertThat(GateRx(PI).matrix * Complex.I, equalTo(GateX.matrix))
    }

    @Test
    fun `PI rotations about Y axis is Y Gate`() {
        // Gates differ only in the global phase that we ultimately don't care about
        val qubit = Qubit.random()
        assertThat(GateRy(PI) * qubit, equalTo(GateY * qubit))
        assertThat(GateRy(PI).matrix * Complex.I, equalTo(GateY.matrix))
    }

    @Test
    fun `PI rotations about Z axis is Z Gate`() {
        // Gates differ only in the global phase that we ultimately don't care about
        val qubit = Qubit.random()
        assertThat(GateRz(PI) * qubit, equalTo(GateZ * qubit))
        assertThat(GateRz(PI).matrix * Complex.I, equalTo(GateZ.matrix))
    }
}
