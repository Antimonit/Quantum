package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.gate.GateCNot
import me.khol.quantum.gate.GateHadamard
import me.khol.quantum.gate.GateX
import me.khol.quantum.gate.GateZ
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

internal class QuantumTeleportationTest {

    @Test
    fun `Quantum teleportation`() {
        val message = Qubit.random()

        val initialRegister = Register(message, ZERO, ZERO)

        val register = program(initialRegister) {
            // Entangle qubits q1 and q2 to form a fully entangled bell state
            GateHadamard[2]
            GateCNot[2, 1]

            // Entangle message/state held by q0 with the rest.
            GateCNot[0, 1]
            GateHadamard[0]

            // Measuring the first two qubits will change the state of the third qubit because
            // of the entanglement. This will teleport the message from the first qubit to the
            // third qubit.
            val (secret, shared) = measureAndCollapse(0, 1)

            // The last qubit can be in one of four superpositions now. We can use qubits
            // measured in the previous step to conditionally apply some gates to put it
            // into one specific superposition.
            if (shared == ONE) GateX[2]
            if (secret == ONE) GateZ[2]
        }

        val measurement = register.measure()

        assertThat(register, equalTo(Register(measurement[0], measurement[1], message)))
    }
}