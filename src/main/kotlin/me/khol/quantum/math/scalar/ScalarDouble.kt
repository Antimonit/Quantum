package me.khol.quantum.math.scalar

inline class ScalarDouble(private val value: Double) : Scalar<ScalarDouble> {

    override fun unaryPlus() = ScalarDouble(value.unaryPlus())
    override fun unaryMinus() = ScalarDouble(value.unaryMinus())

    override fun plus(s: Double) = ScalarDouble(value.plus(s))
    override fun minus(s: Double) = ScalarDouble(value.minus(s))
    override fun times(s: Double) = ScalarDouble(value.times(s))
    override fun div(s: Double) = ScalarDouble(value.div(s))

    override fun plus(s: ScalarDouble) = ScalarDouble(value.plus(s.value))
    override fun minus(s: ScalarDouble) = ScalarDouble(value.minus(s.value))
    override fun times(s: ScalarDouble) = ScalarDouble(value.times(s.value))
    override fun div(s: ScalarDouble) = ScalarDouble(value.div(s.value))
}
