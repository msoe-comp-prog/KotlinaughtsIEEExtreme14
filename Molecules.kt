/**
 * @author Jacob Huebner
 * 
 * Scored 4/4 (100%)
 */

import java.io.BufferedReader
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readInt()

    fun solve(c: Int, h: Int, o: Int, netC: Int, netH: Int, netO: Int): Int {
        return if (h and 1 == 1) {
            min(solve(c, h + 1, o, netC, netH + 1, netO), solve(c, h - 1, o, netC, netH - 1, netO))
        } else {
            val excessO = o - (h shr 1)
            if (excessO < 0) {
                solve(c, h, o - excessO, netC, netH, netO - excessO)
            } else if (excessO and 1 == 1) {
                val a = solve(c, h, o - 1, netC, netH, netO - 1)
                val b = solve(c, h, o + 1, netC, netH, netO + 1)
                min(a, b)
            } else {
                val neededC = (excessO shr 1) - c
                if (neededC >= 0) {
                    (netC + neededC).absoluteValue + netH.absoluteValue + netO.absoluteValue
                } else {
                    val availableGlucose = (h / 12) * 6
                    val nC = -neededC
                    if (nC <= availableGlucose) {
                        min((netC - nC % 6).absoluteValue, (netC + 6 - (nC % 6)).absoluteValue) + netH.absoluteValue + netO.absoluteValue
                    } else {
                        val dH = (12 - (h - availableGlucose * 2)) % 12
                        val dO = dH shr 1
                        if (dH == 0) {
                            (netC - (nC - availableGlucose)).absoluteValue + netH.absoluteValue + netO.absoluteValue
                        } else {
                            val a = solve(c, h + dH, o + dO, netC, netH + dH, netO + dO)
                            val b = solve(c, h + dH - 12, o + dO - 6, netC, netH + dH - 12, netO + dO - 6)
                            min((netC - (nC - availableGlucose)).absoluteValue + netH.absoluteValue + netO.absoluteValue, min(a, b))
                        }
                    }
                }
            }
        }
    }

    repeat(t) {
        val c = br.readInt()
        val h = br.readInt()
        val o = br.readInt()
        sb.append(solve(c, h, o, 0, 0, 0)).append("\n")
    }

    print(sb)
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
