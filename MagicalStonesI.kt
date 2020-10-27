/**
 * @author Jacob Huebner
 * 
 * Scored 29/29 (100%)
 */

import java.io.BufferedReader
import kotlin.math.max

fun main() = (System.`in`.bufferedReader().run {
    val bw = System.out.bufferedWriter()
    val n = readInt()
    val s = IntArray(n) { readInt() - 1 }
    val rev = Array(n){ mutableListOf<Int>()}
    for(x in s.indices){
        rev[s[x]].add(x)
    }
    var numIncycle = 0
    val toOutside = IntArray(n) { -1 }
    val dp = IntArray(n)
    val v = BooleanArray(n)
    val p = BooleanArray(n)
    fun dfs(i: Int): Int{
        v[i] = true
        var cycle = false
        var furthest = -1
        for(x in rev[i]){
            if(p[x]){
                furthest = max(furthest, toOutside[x])
            } else if(v[x]){
                cycle = true
            } else {
                val f = dfs(x)
                if(f == -1){
                    cycle = true
                } else {
                    furthest = max(furthest, toOutside[x])
                }
            }
        }
        p[i] = true
        if(cycle){
            furthest = -1
            numIncycle++
        } else {
            furthest++
            dp[furthest]++
        }
        toOutside[i] = furthest
        return furthest
    }
    for(x in 0 until n){
        if(!p[x]){
            dfs(x)
        }
    }
    val ans = IntArray(n){-1}
    var numStones = n
    var turn = 0
    while (numStones != numIncycle){
        numStones -= dp[turn]
        turn++
        ans[numStones] = turn
    }
    val q = readInt()
    repeat(q){
        bw.write(ans[readInt()].toString())
        bw.newLine()
    }

    bw.close()
} to Unit).second




private const val SPACE_INT = ' '.toInt()
private const val ZERO_INT = '0'.toInt()
private const val NL_INT = '\n'.toInt()

@Suppress("DuplicatedCode")
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

@Suppress("DuplicatedCode")
private fun BufferedReader.readLong(): Long {
    var ret = read().toLong()
    while (ret <= SPACE_INT) {
        ret = read().toLong()
    }
    val neg = ret == '-'.toLong()
    if (neg) {
        ret = read().toLong()
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

@Suppress("DuplicatedCode")
private fun BufferedReader.readWord(): String {
    var ret = read()
    while (ret <= SPACE_INT && ret != -1) {
        ret = read()
    }
    if (ret == -1) {
        return ""
    }
    var cb = CharArray(32)
    var idx = 0
    while (ret > SPACE_INT) {
        if (idx == cb.size) {
            cb = cb.copyOf(cb.size shl 1)
        }
        cb[idx++] = ret.toChar()
        ret = read()
    }
    while (ret <= SPACE_INT && ret != -1 && ret != NL_INT) {
        mark(1)
        ret = read()
    }
    if (ret > SPACE_INT) {
        reset()
    }
    return String(cb, 0, idx)
}

