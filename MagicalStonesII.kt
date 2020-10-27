/**
 * @author Jacob Huebner
 * 
 * Scored 4/4 (100%)
 */

import java.io.BufferedReader
import java.util.*

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readInt()
    repeat(t) {
        val n = br.readInt()
        val w = IntArray(n) { br.readInt() - 1 }
        val b = IntArray(n) { br.readInt() - 1 }
        val revW = Array(n) { mutableListOf<Int>() }
        val revB = Array(n) { mutableListOf<Int>() }
        for (x in 0 until n) {
            revW[w[x]].add(x)
            revB[b[x]].add(x)
        }
        val found = Array(n) { BooleanArray(n) }
        val seq = Array(n) { Array(n) { "" } }
        val queue: Queue<Pair<Int, Int>> = ArrayDeque()
        for (x in 0 until n) {
            found[x][x] = true
            queue += Pair(x, x)
        }
        while (queue.isNotEmpty()) {
            val (c, d) = queue.poll()
            for (wC in revW[c]) {
                for (wD in revW[d]) {
                    if (!found[wC][wD]) {
                        found[wC][wD] = true
                        seq[wC][wD] = "W" + seq[c][d]
                        queue += Pair(wC, wD)
                    }
                }
            }
            for (bC in revB[c]) {
                for (bD in revB[d]) {
                    if (!found[bC][bD]) {
                        found[bC][bD] = true
                        seq[bC][bD] = "B" + seq[c][d]
                        queue += Pair(bC, bD)
                    }
                }
            }
        }
        val good = found.all { ary -> ary.all { it } }
        if (good) {
            var stones = BooleanArray(n) { true }
            fun doBlack(): BooleanArray {
                val new = BooleanArray(n)
                for(x in stones.indices){
                    if(stones[x]){
                        new[b[x]] = true
                    }
                }
                return new
            }
            fun doWhite(): BooleanArray {
                val new = BooleanArray(n)
                for(x in stones.indices){
                    if(stones[x]){
                        new[w[x]] = true
                    }
                }
                return new
            }
            fun findPair(): Pair<Int,Int>?{
                var first = -1
                for(x in stones.indices){
                    if(stones[x]){
                        if(first == -1){
                            first = x
                        } else {
                            return Pair(first, x)
                        }
                    }
                }
                return null
            }
            var p = findPair()
            while (p != null){
                seq[p.first][p.second].forEach {
                    sb.append(it)
                    stones = if(it != 'W'){
                        doBlack()
                    } else {
                        doWhite()
                    }
                }
                p = findPair()
            }
            sb.append("\n")
        } else {
            sb.append("impossible\n")
        }
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
