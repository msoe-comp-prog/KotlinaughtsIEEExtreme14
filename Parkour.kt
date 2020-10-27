/**
 * @author Nicholas Johnson
 * 
 * Scored 5/20 (25%)
 */

import java.io.BufferedReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val MOD = 1000000007L
private fun Pair<Int, Int>.dist(other: Pair<Int, Int>): Int {
    return abs(first - other.first) + abs(second - other.second) - 1
}

fun main() = (System.`in`.bufferedReader().run {
    val numTiles = readInt()
    val numQueries = readInt()
    val explQueries = readInt()
    val mk = readLong()

    val tiles = Array(numTiles) { readInt() to readInt() }
    val abcLine = LongArray(9) { readLong() }

    val solveFunction: (Int, Int) -> Int = if (numTiles <= 500) {
        val dists = Array(numTiles) { IntArray(numTiles) }
        for (i in tiles.indices) {
            for (j in tiles.indices) {
                dists[i][j] = tiles[i].dist(tiles[j])
            }
        }

        for (k in 0 until numTiles) {
            for (i in 0 until numTiles) {
                for (j in 0 until numTiles) {
                    dists[i][j] = min(dists[i][j], max(dists[i][k], dists[k][j]))
                }
            }
        }
        { x: Int, y: Int -> dists[x][y] }
    } else {
        val adj = Array(tiles.size) { ArrayList<Pair<Int, Int>>() }
        for (i in tiles.indices) {
            for (j in 1 + 1 until tiles.size) {
                val dist = tiles[i].dist(tiles[j])
                if (dist <= mk) {
                    adj[i].add(dist to j)
                    adj[j].add(dist to i)
                }
            }
        }
        { x: Int, y: Int ->
            var ans = Int.MAX_VALUE
            val vis = BooleanArray(tiles.size)
            val q = PriorityQueue<Pair<Int, Int>> { o1, o2 -> o1.first.compareTo(o2.first) }
            q += 0 to x
            while (q.isNotEmpty()) {
                val (dist, nodeId) = q.poll()
                if (nodeId == y) {
                    ans = dist
                    break
                }
                if (vis[nodeId]) {
                    continue
                }
                vis[nodeId] = true
                for ((aDist, aNode) in adj[nodeId]) {
                    q += max(aDist, dist) to aNode
                }
            }
            ans
        }
    }

    var ans = 0L
    val queryList = Array(2) { Triple(0L, 0L, 0L) }
    for (quNum in 0 until numQueries) {
        if (quNum < explQueries) {
            queryList[0] = queryList[1]
            queryList[1] = Triple(readLong(), readLong(), readLong())
        } else {
            val nq = Triple(
                1 + (((abcLine[0] * queryList[1].first) % numTiles) + ((abcLine[1] * queryList[0].first) % numTiles) + abcLine[2]) % numTiles,
                1 + (((abcLine[3] * queryList[1].second) % numTiles) + ((abcLine[4] * queryList[0].second) % numTiles) + abcLine[5]) % numTiles,
                (((abcLine[6] * queryList[1].third) % mk) + ((abcLine[7] * queryList[0].third) % mk) + abcLine[8]) % mk,
            )
            queryList[0] = queryList[1]
            queryList[1] = nq
        }
        val (si, ti, k) = queryList[1]
        val s = si - 1
        val t = ti - 1

        if (solveFunction(s.toInt(), t.toInt()) <= k) {
            ans += modpow(2L, (quNum + 1).toLong(), MOD)
            ans %= MOD
        }
    }
    println(ans)
} to Unit).second

private fun modpow(x: Long, n: Long, m: Long): Long {
    if (n == 0L) {
        return 1L % m
    }
    var u = modpow(x, n / 2, m)
    u = (u * u) % m
    if (n and 1L == 1L) {
        u = (u * x) % m
    }
    return u
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

