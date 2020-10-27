/**
 * @author Jacob Huebner
 * 
 * Scored 15/15 (100%)
 */

import java.io.BufferedReader
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()
    val (n, m, r, c) = IntArray(4) { br.readInt() }
    val costs = Array(r) { LongArray(c) { br.readInt().toLong() } }
    var curPrice = 0L
    for (row in 0 until r) {
        for (col in 0 until c) {
            val numRows = (n - row + r - 1) / r
            val numCols = (m - col + c - 1) / c
            curPrice += costs[row][col] * numRows * numCols
        }
    }
    var best = curPrice
    for (rowRotation in 0 until r) {
        val rowToRemove = rowRotation % r
        val rowToAdd = (n + rowRotation) % r
        for (col in 0 until c) {
            val times = (m - col + c - 1) / c
            curPrice -= costs[rowToRemove][col] * times
            curPrice += costs[rowToAdd][col] * times
        }
        best = min(best, curPrice)
        for (colRotation in 0 until c) {
            val colToRemove = colRotation % c
            val colToAdd = (m + colRotation) % c
            for(row in 0 until r){
                val newIdx = (row + r - rowRotation - 1)%r
                val times = (n - newIdx + r - 1) / r
                curPrice -= costs[row][colToRemove] * times
                curPrice += costs[row][colToAdd] * times
            }
            best = min(best, curPrice)
        }
    }


    println(best)
}

private const val SPACE_INT = ' '.toInt()
private const val ZERO_INT = '0'.toInt()
private const val NL_INT = '\n'.toInt()

private fun BufferedReader.readInt(): Int {
    var ret = read()
    while (ret <= SPACE_INT) {
        ret = read()
    }
    val neg = ret == '-'.toInt()
    if (neg) {
        ret = read()
    }
    ret -= ZERO_INT
    var read = read()
    while (read >= ZERO_INT) {
        ret *= 10
        ret += read - ZERO_INT
        read = read()
    }

    while (read <= SPACE_INT && read != -1 && read != NL_INT) {
        mark(1)
        read = read()
    }
    if (read > SPACE_INT) {
        reset()
    }
    return if (neg) -ret else ret
}
