/**
 * @author Jacob Huebner
 * 
 * Scored 10/10 (100%)
 */

import java.io.BufferedReader
import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readInt()
    repeat(t) {
        val str = br.readLine()
        val n = str.length
        fun getD1(s: String): IntArray {
            val d1 = IntArray(s.length)
            var l = 0
            var r = -1
            for (i in s.indices) {
                var k = if (i > r) 1 else min(d1[l + r - i], r - i + 1)
                while (0 <= i - k && i + k < n && s[i - k] == s[i + k]) {
                    k++
                }
                d1[i] = k--
                if (i + k > r) {
                    l = i - k
                    r = i + k
                }
            }
            return d1
        }

        fun getD2(s: String): IntArray {
            val d2 = IntArray(s.length)
            var l = 0
            var r = -1
            for (i in s.indices) {
                var k = if (i > r) 0 else min(d2[l + r - i + 1], r - i + 1)
                while (0 <= i - k - 1 && i + k < n && s[i - k - 1] == s[i + k]) {
                    k++
                }
                d2[i] = k--
                if (i + k > r) {
                    l = i - k - 1
                    r = i + k
                }
            }
            return d2
        }

        val d1 = getD1(str)
        val d2 = getD2(str)

        fun calcPrefix(): IntArray {
            val prefix = IntArray(n)
            var best = 1
            var right = -1
            val deque: Deque<Pair<Int, Int>> = ArrayDeque()
            for (x in 0 until n) {
                val (length, cRight) = if (x + d1[x] - 1 > x + d2[x] - 1) {
                    Pair(2 * d1[x] - 1, x + d1[x] - 1)
                } else {
                    Pair(2 * d2[x], x + d2[x] - 1)
                }
                if (cRight > right) {
                    deque.addFirst(Pair(length, cRight))
                    right = cRight
                }
                val cur = deque.peekLast().first - (deque.peekLast().second - x) * 2
                best = max(cur, best)
                if (x == deque.peekLast().second) {
                    deque.pollLast()
                }

                prefix[x] = best
            }
            return prefix
        }

        fun calcSuffix(): IntArray {
            val suffix = IntArray(n)
            var best = 1
            var left = n
            val deque: Deque<Pair<Int, Int>> = ArrayDeque()
            for (x in str.indices.reversed()) {
                val (length, cLeft) = if (x - d1[x] + 1 <= x - d2[x]) {
                    Pair(2 * d1[x] - 1, x - d1[x] + 1)
                } else {
                    Pair(2 * d2[x], x - d2[x])
                }
                if (cLeft < left) {
                    deque.addFirst(Pair(length, cLeft))
                    left = cLeft
                }
                val cur = deque.peekLast().first - (x - deque.peekLast().second) * 2
                best = max(cur, best)
                if (x == deque.peekLast().second) {
                    deque.pollLast()
                }
                suffix[x] = best
            }
            return suffix
        }
        val prefix = calcPrefix()
        val suffix = calcSuffix()
        var best = 0
        for(x in 0 until n - 1){
            best = max(best, prefix[x] + suffix[x + 1])
        }
        sb.append(best).append("\n")
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
