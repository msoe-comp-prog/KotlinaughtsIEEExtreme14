/**
 * @author Jacob Huebner
 * 
 * Scored 9/9 (100%)
 */

import java.io.BufferedReader
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private fun gcd(p: Int, q: Int): Int {
    return if (p == 0) q
    else if (q == 0) p
    else if (p and 1 == 0 && q and 1 == 0) gcd(p shr 1, q shr 1) shl 1
    else if (p and 1 == 0) gcd(p shr 1, q)
    else if (q and 1 == 0) gcd(p, q shr 1)
    else if (p > q) gcd((p - q) shr 1, q)
    else gcd(p, (q - p) shr 1)
}

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readInt()
    repeat(t) {
        val curState = Spot(0, 0, 0, 0)
        val n = br.readInt()
        val data = TreeMap<Fraction, Spot>()
        repeat(n) {
            val (xS, yS, flavor) = br.readLine().split(" ")
            val x = (xS.toDouble() * 100).roundToInt()
            val y = (yS.toDouble() * 100).roundToInt()
            if (x != 0 || y != 0) {
                val f = Fraction(y, x).reduce()

                val spot = data.getOrPut(f) {
                    Spot(0, 0, 0, 0)
                }
                if (x == 0) {
                    if (y > 0) {
                        if (flavor == "1") {
                            curState.positiveOut++
                            spot.positiveOut++
                        } else {
                            curState.negativeOut++
                            spot.negativeOut++
                        }
                    } else {
                        if (flavor == "1") {
                            curState.positiveIn++
                            spot.positiveIn++
                        } else {
                            curState.negativeIn++
                            spot.negativeIn++
                        }
                    }

                } else if (x > 0) {
                    if (flavor == "1") {
                        curState.positiveIn++
                        spot.positiveIn++
                    } else {
                        curState.negativeIn++
                        spot.negativeIn++
                    }
                } else {
                    if (flavor == "1") {
                        curState.positiveOut++
                        spot.positiveOut++
                    } else {
                        curState.negativeOut++
                        spot.negativeOut++
                    }
                }
            }
        }
        var good = curState.isValid()
        data.forEach {
            curState.addTo(it.value)
            good = good || curState.isValid()
            curState.addReversed(it.value)
        }
        if(good){
            sb.append("YES\n")
        } else {
            sb.append("NO\n")
        }
    }
    print(sb)
}

data class Fraction(var a: Int, var b: Int) : Comparable<Fraction> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Fraction

        return a * other.b == b * other.a
    }

    fun reduce(): Fraction {
        return if (a != 0 || b != 0) {
            val gcd = gcd(a.absoluteValue, b.absoluteValue)
            Fraction(a / gcd, b / gcd)
        } else {
            Fraction(0, 0)
        }
    }

    override fun compareTo(other: Fraction): Int {
        if (equals(other)) return 0
        return if (other.b == 0) {
            1
        } else if (b == 0) {
            -1
        } else {
            (a.toDouble() / b).compareTo(other.a.toDouble() / other.b)
        }
    }

    override fun hashCode(): Int {
        return 0
    }

}

data class Spot(var positiveIn: Int, var positiveOut: Int, var negativeIn: Int, var negativeOut: Int) {
    fun isValid(): Boolean {
        return (positiveIn == 0 && negativeOut == 0) || (positiveOut == 0 && negativeIn == 0)
    }

    fun addTo(other: Spot) {
        positiveIn -= other.positiveIn
        positiveOut -= other.positiveOut
        negativeIn -= other.negativeIn
        negativeOut -= other.negativeOut
    }

    fun addReversed(other: Spot){
        positiveIn += other.positiveOut
        positiveOut += other.positiveIn
        negativeIn += other.negativeOut
        negativeOut += other.negativeIn
    }
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