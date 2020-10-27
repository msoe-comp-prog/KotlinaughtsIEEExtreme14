/**
 * @author Jacob Huebner
 * 
 * Scored 16/16 (100%)
 */

import java.io.BufferedReader
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val n = br.readInt()
    val m = br.readInt()
    val names = mapOf(
            "Zulian" to 0,
            "Razzashi" to 1,
            "Hakkari" to 2,
            "Sandfury" to 3,
            "Skullsplitter" to 4,
            "Bloodscalp" to 5,
            "Gurubashi" to 6,
            "Vilebranch" to 7,
            "Witherbark" to 8
    )
    val cards = IntArray(m)
    val qtys = IntArray(m)
    val prices = LongArray(m) {
        val (a, b, c) = br.readLine().split(" ")
        cards[it] = names[a] ?: error("")
        qtys[it] = b.toInt()
        c.toLong()
    }
    val pricesToGet = Array(9) { LongArray(n + 1) { Long.MAX_VALUE } }
    for (x in 0 until 9) {
        pricesToGet[x][0] = 0L
    }
    for (x in 0 until m) {
        val card = cards[x]
        val qty = qtys[x]
        val price = prices[x]
        for (y in n - 1 downTo 0) {
            if (pricesToGet[card][y] != Long.MAX_VALUE) {
                pricesToGet[card][min(n, y + qty)] = min(pricesToGet[card][min(n, y + qty)], price + pricesToGet[card][y])
            }
        }
    }
    for (card in 0 until 9) {
        var lowest = pricesToGet[card].last()
        for (y in n downTo 0) {
            lowest = min(lowest, pricesToGet[card][y])
            pricesToGet[card][y] = lowest
        }
    }
    var best = Long.MAX_VALUE
    for (set1 in 0..n) {
        for (set2 in 0..(n - set1)) {
            val set3 = n - set1 - set2
            val p = LongArray(9) {
                when (it) {
                    0, 1, 2 -> {
                        pricesToGet[it][set1]
                    }
                    3, 4, 5 -> {
                        pricesToGet[it][set2]
                    }
                    else -> {
                        pricesToGet[it][set3]
                    }
                }
            }
            if (p.none { it == Long.MAX_VALUE }) {
                best = min(best, p.sum())
            }
        }
    }
    println(if (best == Long.MAX_VALUE) "impossible" else best)
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
