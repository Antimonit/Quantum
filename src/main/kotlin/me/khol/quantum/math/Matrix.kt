package me.khol.quantum.math

import org.ejml.data.ZMatrixRMaj
import org.ejml.dense.row.CommonOps_ZDRM
import org.ejml.dense.row.CommonOps_ZDRM.*
import kotlin.math.abs

class Matrix {

    companion object {

        fun identity(size: Int) = Matrix(CommonOps_ZDRM.identity(size))
    }

    private val matrix: ZMatrixRMaj

    val cols: Int get() = matrix.numCols
    val rows: Int get() = matrix.numRows

    constructor(matrix: ZMatrixRMaj) {
        this.matrix = matrix
    }

    constructor(numRows: Int, numCols: Int, v: List<Complex>)
        : this(numRows, numCols, *v.toTypedArray())

    constructor(numRows: Int, numCols: Int, vararg v: Complex) {
        matrix = ZMatrixRMaj(numRows, numCols, true, *v.flat())
    }

    constructor(m: Array<Array<Complex>>) {
        matrix = ZMatrixRMaj(m.map { it.flat() }.toTypedArray())
    }

    operator fun unaryPlus(): Matrix = this * 1

    operator fun unaryMinus(): Matrix = this * -1

    operator fun times(other: Matrix): Matrix = new(this.rows, other.cols) {
        mult(this.matrix, other.matrix, it)
    }

    operator fun plus(other: Matrix): Matrix = new(rows, cols) {
        add(this.matrix, other.matrix, it)
    }

    operator fun minus(other: Matrix): Matrix = new(rows, cols) {
        subtract(this.matrix, other.matrix, it)
    }

    operator fun times(other: Number): Matrix = new(cols, rows) {
        elementMultiply(matrix, other.toDouble(), 0.0, it)
    }

    operator fun times(other: Complex): Matrix = new(rows, cols) {
        elementMultiply(matrix, other.re, other.im, it)
    }

    fun transpose(): Matrix = new(cols, rows) {
        transpose(matrix, it)
    }

    fun conjugate(): Matrix = new(rows, cols) {
        conjugate(matrix, it)
    }

    infix fun tensor(other: Matrix): Matrix {
        if (this.cols != 1 || other.cols != 1) {
            // TODO: Allow row vectors as well.
            error("Tensor product is possible only for column vectors.")
        }

        val a = this.col(0)
        val b = other.col(0)
        return Matrix(a.size * b.size, 1, a.flatMap { x -> b.map { y -> x * y } })
    }

    private fun new(row: Int, col: Int, action: (ZMatrixRMaj) -> Unit): Matrix {
        val result = ZMatrixRMaj(row, col)
        action(result)
        return Matrix(result)
    }

    operator fun get(row: Int, col: Int): Complex {
        return Complex(matrix.getReal(row, col), matrix.getImag(row, col))
    }

    fun row(row: Int): Array<Complex> {
        return Array(cols) { col -> get(row, col) }
    }

    fun col(col: Int): Array<Complex> {
        return Array(rows) { row -> get(row, col) }
    }

    fun toArrayArray(): Array<Array<Complex>> {
        return Array(rows) { row ->
            Array(cols) { col ->
                this[row, col]
            }
        }
    }

    override fun toString(): String {
        return toArrayArray().joinToString("\n") { it.joinToString(" ", "| ", " |") }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix
        return this.matrix.data.zip(other.matrix.data).all { (a, b) -> abs(a - b) < 1e-10 }
    }

    override fun hashCode(): Int {
        return matrix.data.hashCode()
    }
}

private fun Array<out Complex>.flat() = flatMap { it.flat() }.toDoubleArray()
private fun Complex.flat() = listOf(re, im)

