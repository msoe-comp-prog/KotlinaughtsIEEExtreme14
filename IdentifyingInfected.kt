/**
 * @author Jacob Huebner, Nicholas Johnson
 * 
 * Scored 20/20 (100%)
 */

import java.io.BufferedReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

fun main() = (System.`in`.bufferedReader().run {
    val bw = System.out.bufferedWriter()

    val map = Graph(readInt())
    for (end in 0 until readInt()) {
        map.addEdge(readInt() - 1, readInt() - 1)
    }

    val (art, ids, tree) = biconnectedComponents(map)
    val traversalArray = IntArray(2 * tree.n - 1)
    val posInTraversalArray = IntArray(tree.n)
    var depth = 0
    var traversalIdx = 0

    TreeDFS(tree.adj) { goDown, cur, next ->
        traversalArray[traversalIdx] = depth

        if (goDown) {
            depth++
        } else {
            posInTraversalArray[cur] = traversalIdx
            if(cur != next) depth--
        }

        traversalIdx++
    }

    val minPreCompArray = Array(Integer.bitCount(Integer.highestOneBit(((tree.n) shl 2) - 1) - 1)) {
        IntArray(2 * tree.n - 1)
    }

    for (pow in minPreCompArray.indices) {
        val num = (1 shl pow)
        if (pow == 0) {
            minPreCompArray[pow] = traversalArray
        } else {
            for (start in minPreCompArray[pow].indices) {
                if (start + num <= minPreCompArray[pow].size) {
                    minPreCompArray[pow][start] = min(minPreCompArray[pow - 1][start], minPreCompArray[pow - 1][start + (num shr 1)])
                }
            }
        }
    }

    val q = readInt()
    repeat(q){
        val a = readInt() - 1
        val b = readInt() - 1
        val dist = dist( posInTraversalArray[ids[a]], posInTraversalArray[ids[b]], minPreCompArray)
        val num = if(art[a] && art[b]){
            (dist / 2) + 1
        } else {
            (dist / 2) + 2
        }
        bw.write(num.toString())
        bw.newLine()
    }

    bw.close()
} to Unit).second


private fun dist(traversalIDA: Int, traversalIDB: Int, minPreCompArray: Array<IntArray>): Int {
    return minPreCompArray[0][traversalIDA] + minPreCompArray[0][traversalIDB] - 2 * query(minPreCompArray, min(traversalIDA, traversalIDB), max(traversalIDA, traversalIDB))
}

private fun query(preCompArray: Array<IntArray>, a: Int, b: Int): Int {
    return if ((b - a + 1) and (b - a) == 0) {
        preCompArray[Integer.bitCount(b - a)][a]
    } else {
        val largestPow = Integer.highestOneBit(b - a) - 1
        min(preCompArray[Integer.bitCount(largestPow)][a], preCompArray[Integer.bitCount(largestPow)][b - largestPow])
    }
}

private fun biconnectedComponents(g: Graph): Triple<BooleanArray, IntArray, Graph> {
    val n = g.n
    val num = IntArray(n)
    val low = IntArray(n)
    val art = BooleanArray(n)
    val stk = ArrayList<Int>()

    val comps = ArrayList<ArrayList<Int>>()

    fun dfs(u: Int, parent: Int, t: IntArray) {
        low[u] = ++t[0]
        num[u] = t[0]
        stk += u
        for (v in g[u]) {
            if (v != parent) {
                if (num[v] == 0) {
                    dfs(v, u, t)
                    low[u] = min(low[u], low[v])
                    if (low[v] >= num[u]) {
                        art[u] = num[u] > 1 || num[v] > 2
                        comps.add(ArrayList<Int>().apply { add(u) })
                        while (comps.last().last() != v) {
                            comps.last().add(stk.removeAt(stk.lastIndex))
                        }
                    }
                } else {
                    low[u] = min(low[u], num[v])
                }
            }
        }
    }

    for (u in 0 until n) {
        if (num[u] == 0) {
            val t = IntArray(1)
            dfs(u, -1, t)
        }
    }

    val tree = Graph(0)
    val id = IntArray(n)

    for (u in 0 until n) {
        if (art[u]) {
            id[u] = tree.addNode()
        }
    }

    for (comp in comps) {
        val node = tree.addNode()
        for (u in comp) {
            if (!art[u]) {
                id[u] = node
            } else {
                tree.addEdge(node, id[u])
            }
        }
    }

    return Triple(art, id, tree)
}

private data class Graph(var n: Int, val adj: ArrayList<ArrayList<Int>> = ArrayList(n)) {
    init {
        repeat(n) {
            adj.add(ArrayList())
        }
    }

    fun addEdge(u: Int, v: Int) {
        adj[u].add(v)
        adj[v].add(u)
    }

    fun addNode(): Int {
        adj.add(ArrayList())
        return n++
    }

    operator fun get(i: Int): ArrayList<Int> = adj[i]
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

private class TreeDFS(c: List<Iterable<Int>>, val p: IntArray = IntArray(c.size), b: (Boolean, Int, Int) -> Unit) {
    //    constructor(c: Array<out Iterable<Int>>, p: IntArray = IntArray(c.size), root: Int = 0, b: (Boolean, Int, Int) -> Unit) : this(c.toList(), p, root, b)
    init {
        val i = ArrayDeque<Iterator<Int>>()
        val visited = BooleanArray(c.size)
        for (r in c.indices) {
            if(visited[r]) continue
            visited[r] = true

            var cn = r
            p[r] = r
            i.push(c[r].iterator())
            while (i.isNotEmpty()) {
                if (i.peek().hasNext()) {
                    val nx = i.peek().next()
                    if (!visited[nx]) {
                        visited[nx] = true
                        p[nx] = cn
                        b(true, cn, nx)
                        i.push(c[nx].iterator())
                        cn = nx
                    }
                } else {
                    b(false, cn, p[cn])
                    cn = p[cn]
                    i.pop()
                }
            }
        }
    }
}

private class IteratorDFS(
        c: List<Iterable<Int>>,
        p: IntArray = IntArray(c.size) { -1 },
        b: (Boolean, Boolean, Int, Int) -> Unit
) {
    constructor(
            c: Array<out Iterable<Int>>,
            p: IntArray = IntArray(c.size) { -1 },
            b: (Boolean, Boolean, Int, Int) -> Unit
    ) : this(c.toList(), p, b)

    init {
        val i = ArrayDeque<Iterator<Int>>()
        val v = BooleanArray(c.size)
        for (r in c.indices) {
            if (v[r]) continue
            v[r] = true
            var cn = r
            p[r] = r
            i.push(c[r].iterator())
            while (i.isNotEmpty()) {
                if (i.peek().hasNext()) {
                    val nx = i.peek().next()
                    if (!v[nx]) {
                        p[nx] = cn
                        v[nx] = true
                        b(true, false, cn, nx)
                        i.push(c[nx].iterator())

                        cn = nx
                    } else if (nx != p[cn]) {
                        b(true, true, cn, nx)
                    }
                } else {
                    b(false, false, cn, p[cn])
                    cn = p[cn]
                    i.pop()
                }
            }
        }
    }
}
