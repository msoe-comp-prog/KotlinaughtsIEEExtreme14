/**
 * @author Jacob Huebner, Kiel Dowdle
 * 
 * Scored 39/41 (95%)
 */

import java.io.BufferedReader
import kotlin.math.max
import kotlin.system.exitProcess

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val n = br.readInt()
    val k = br.readInt()
    val vals = charArrayOf('2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K', 'A')
    val cards = br.readLine().map {
        when (it) {
            'X' -> 8
            'J' -> 9
            'Q' -> 10
            'K' -> 11
            'A' -> 12
            else -> it - '2'
        }
    }

    val actions = br.readLine().map { it == 'y' }
    val inCommunity = IntArray(13)
    val mins = IntArray(13)
    val maxs = IntArray(13) { 4 }

    for (x in 0 until n) {
        maxs[cards[x]] = max(0, maxs[cards[x]] - 1)
        if (actions[x]) {
            if (inCommunity[cards[x]] == 0) {
                mins[cards[x]] = 1
            }
        } else {
            maxs[cards[x]] = 0
            if (inCommunity[cards[x]] != 0) {
                println("impossible")
                exitProcess(0)
            }
        }
        inCommunity[cards[x]]++
    }
    if (mins.sum() > k || maxs.sum() < k) {
        println("impossible")
        exitProcess(0)
    }
    for (x in 0 until 13) {
        if (maxs[x] < mins[x]) {
            println("impossible")
            exitProcess(0)
        }
    }
    var freeCards = k - mins.sum()
    for(goal in 4 downTo 2) {
        for (cardsToAdd in 1..4) {
            for (x in 0 until 13) {
                if (freeCards >= cardsToAdd && maxs[x] != 0 && mins[x] + inCommunity[x] + cardsToAdd == goal) {
                    mins[x] += cardsToAdd
                    freeCards -= cardsToAdd
                }
            }
        }
    }
    for(x in 0 until 13){
        if(freeCards > 0 && mins[x] < maxs[x]){
            mins[x]++
            freeCards--
        }
    }

    if (mins.sum() != k) {
        println("impossible")
        exitProcess(0)
    }
    for (x in 0 until 13) {
        repeat(mins[x]) {
            sb.append(vals[x])
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
