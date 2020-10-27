/**
 * @author Jacob Huebner, Nicholas Johnson
 * 
 * Scored 5/5 (100%)
 */

fun main() {
    val bw = System.out.bufferedWriter()
    val st = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    repeat(readLine()!!.toInt()) {
        val sz = readLine()!!.toInt()
        if (sz < 4) {
            bw.append("impossible")
            bw.newLine()
        } else {
            val arr = Array(sz) { CharArray(sz) }
            val chars = st.iterator()
            val fc = chars.next()
            for (i in 0 until sz) {
                arr[i][0] = fc
            }
            val steps = ((sz - 2) / 2)
            for (i in 0 until steps) {
                val nc1 = chars.next()
                val nc2 = chars.next()
                arr[i].fill(nc1, i + 1, sz)
                arr[i + 1].fill(nc1, 1, i + 2)
                arr[sz - i - 1].fill(nc2, i + 1, sz)
                arr[sz - i - 2].fill(nc2, 1, i + 2)
            }
            if (sz and 1 == 1) {
                val nc = chars.next()
                arr[sz / 2].fill(nc, 1, sz - 1)
                arr[(sz / 2) + 1][steps + 1] = nc
                arr[(sz / 2) - 1][steps + 1] = nc
                val lc = chars.next()
                arr[sz / 2][sz - 1] = lc
                arr[sz / 2 - 1].fill(lc, steps + 2, sz)
                arr[sz / 2 + 1].fill(lc, steps + 2, sz)
            } else {
                val nc = chars.next()
                arr[sz / 2].fill(nc, sz / 2, sz)
                arr[(sz / 2) - 1].fill(nc, sz / 2, sz)
            }
            for (line in arr) {
                line.forEach(bw::append)
                bw.newLine()
            }
        }
    }
    bw.close()
}
