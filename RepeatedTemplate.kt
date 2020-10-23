import java.io.BufferedReader

fun main() = (System.`in`.bufferedReader().run {
    val bw = System.out.bufferedWriter()

    val times = readInt()

    for (time in 0 until times) {

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
