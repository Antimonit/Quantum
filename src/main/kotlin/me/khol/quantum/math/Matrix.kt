package me.khol.quantum.math

import org.ejml.data.ZMatrixRMaj
import org.ejml.dense.row.CommonOps_ZDRM
import org.ejml.dense.row.CommonOps_ZDRM.*
import kotlin.math.abs

class Matrix : Iterable<Complex> {

    companion object {

        fun identity(size: Int) = Matrix(CommonOps_ZDRM.identity(size))
    }

    private val matrix: ZMatrixRMaj

    val cols: Int get() = matrix.numCols
    val rows: Int get() = matrix.numRows

    constructor(matrix: Matrix) {
        this.matrix = matrix.matrix
    }

    constructor(matrix: ZMatrixRMaj) {
        this.matrix = matrix
    }

    constructor(numRows: Int, numCols: Int, v: List<Complex>)
            : this(numRows, numCols, *v.toTypedArray())

    constructor(numRows: Int, numCols: Int, vararg v: Complex) {
        matrix = ZMatrixRMaj(numRows, numCols, true, *v.toList().flat())
    }

    constructor(m: List<List<Complex>>) {
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

    operator fun times(other: Number): Matrix = new(rows, cols) {
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

    fun conjugateTranspose(): Matrix = new(rows, cols) {
        transposeConjugate(matrix, it)
    }

    infix fun tensor(other: Matrix): Matrix {
        return Matrix(List(this.rows * other.rows) { row ->
            List(this.cols * other.cols) { col ->
                this[row / other.rows, col / other.cols] * other[row % other.rows, col % other.cols]
            }
        })
    }

    private fun new(row: Int, col: Int, action: (ZMatrixRMaj) -> Unit): Matrix {
        val result = ZMatrixRMaj(row, col)
        action(result)
        return Matrix(result)
    }

    operator fun get(row: Int, col: Int): Complex {
        return Complex(matrix.getReal(row, col), matrix.getImag(row, col))
    }

    fun row(row: Int): List<Complex> {
        return List(cols) { col -> get(row, col) }
    }

    fun col(col: Int): List<Complex> {
        return List(rows) { row -> get(row, col) }
    }

    fun toListList(): List<List<Complex>> {
        return List(rows) { row ->
            List(cols) { col ->
                this[row, col]
            }
        }
    }

    override fun iterator(): Iterator<Complex> {
        return MatrixIterator(this)
    }

    private class MatrixIterator(private val matrix: Matrix) : Iterator<Complex> {
        private var index = 0
        override fun hasNext() = index < matrix.cols * matrix.rows
        override fun next() = matrix[index % matrix.rows, index / matrix.rows].also { index++ }
    }

    override fun toString(): String {
        return toListList().joinToString("\n") { it.joinToString(" ", "| ", " |") }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix) return false
        return this.matrix.data.zip(other.matrix.data).all { (a, b) -> abs(a - b) < 1e-10 }
    }

    override fun hashCode(): Int {
        return matrix.data.hashCode()
    }
}

private fun List<Complex>.flat() = flatMap { it.flat() }.toDoubleArray()
private fun Complex.flat() = listOf(re, im)

