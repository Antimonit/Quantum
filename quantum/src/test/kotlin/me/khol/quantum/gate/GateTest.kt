package me.khol.quantum.gate

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Mathematical properties of gates")
class GateTest {

    /**
     * Gates typically perform a 180° about some axis on the Bloch sphere.
     *
     * Performing the operation twice should loop around the Bloch sphere and return to the
     * original location, effectively negating its own effect.
     */
    @DisplayName("Gate should be reversible")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
//        GateS::class, // does not perform full 180° rotation
//        GateT::class, // does not perform full 180° rotation
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class
//        GateSwapRoot::class // does not perform full 180° rotation
    ])
    fun <T: Gate> isGateReversible(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val squared = gate * gate
        assertThat(squared, equalTo(GateIdentity(gate.qubits)))
    }

    @DisplayName("Gates should be normal")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateS::class,
        GateT::class,
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class,
        GateSwapRoot::class
    ])
    fun <T: Gate> isGateNormal(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val adjoint = gate.adjoint
        assertThat(gate * adjoint, equalTo(adjoint * gate))
    }

    /**
     * All linear operators that correspond to quantum logic gates must be unitary.
     *
     * That is, if a complex matrix U is unitary, then it must be true that inverse
     * of U (U^-1) is equal to conjugate transpose of U (U^†). It follows that:
     *
     * U^-1 * U^† == U^† * U^-1 == I
     *
     * Geometrically speaking, conjugate transpose (i.e. adjoint, dagger) of the
     * gate's transformation matrix reverts the effect of the original matrix.
     */
    @DisplayName("Gates should be unitary")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateS::class,
        GateT::class,
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class,
        GateSwapRoot::class
    ])
    fun <T: Gate> isGateUnitary(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val adjoint = gate.adjoint
        assertThat(gate * adjoint, equalTo(GateIdentity(gate.qubits)))
        assertThat(adjoint * gate, equalTo(GateIdentity(gate.qubits)))
    }
}
