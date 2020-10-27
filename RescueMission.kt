/**
 * @author Jacob Huebner, Nicholas Johnson
 * 
 * Scored 21/21 (100%)
 */

import java.io.BufferedReader
import kotlin.math.max
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()
    val n = br.readInt()
    val s = IntArray(n) { br.readInt() }
    val totalDays = br.readInt()
    val l = IntArray(totalDays)
    val r = IntArray(totalDays)
    val v = IntArray(totalDays) {
        l[it] = br.readInt() - 1
        r[it] = br.readInt() - 1
        br.readInt()
    }
    val uf = UF(n, s)
    var saved = 0
    for(day in 0 until totalDays){
        var x = uf.range[uf.find(l[day])].second
        while (x < r[day]){
            uf.union(x,x + 1)
            x = uf.range[uf.find(x)].second
        }
        x = uf.find(x)
        val toSave = min(uf.armies[x], v[day])
        saved += toSave
        uf.armies[x] -= toSave
    }
    println(saved)
}

private class UF(val n: Int, val armies : IntArray) {
    val range = Array(n) { it to it }
    val reps = IntArray(n) { it }
    val size = IntArray(n) { 1 }

    fun find(a: Int): Int {
        var k = reps[a]
        while (reps[k] != k) {
            k = reps[k]
        }
        return k
    }

    fun union(a: Int, b: Int): Int {
        val u = find(a)
        val v = find(b)
        return if (u == v) {
            u
        } else if (size[u] > size[v]) {
            size[u] += size[v]
            reps[v] = u
            armies[u] += armies[v]
            range[u] = min(range[u].first, range[v].first) to max(range[u].second, range[v].second)
            u
        } else {
            size[v] += size[u]
            reps[u] = v
            armies[v] += armies[u]
            range[v] = min(range[u].first, range[v].first) to max(range[u].second, range[v].second)
            v
        }
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
