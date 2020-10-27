/**
 * @author Nicholas Johnson
 * 
 * Scored 47%
 */

fun main() {
    val bw = System.out.bufferedWriter()
    repeat(readLine()!!.toInt()) {
        var mulBy = readLine()!!.toLong()
        if (mulBy == 0L) {
            bw.append("SUB R0, R0, R0, LSL #0")
            bw.newLine()
        } else if (mulBy == 1L) {
            //nothing
        } else if (mulBy.countOneBits() == 1) {
            bw.append("MOV R0, R0, LSL #").append(mulBy.countTrailingZeroBits().toString())
            bw.newLine()
        } else if (mulBy.countOneBits() == 2) {
            if (mulBy and 1L != 1L) {
                bw.append("MOV R0, R0, LSL #").append(mulBy.countTrailingZeroBits().toString())
                bw.newLine()
            }
            bw.append("ADD R0, R0, R0, LSL #").append(
                (1 + (mulBy ushr (mulBy.countTrailingZeroBits() + 1)).countTrailingZeroBits()).toString()
            )
            bw.newLine()
        } else if (mulBy.countOneBits() == 32) {
            bw.append("RSB R0, R0, R0, LSL #16")
            bw.newLine()
            bw.append("ADD R0, R0, R0, LSL #16")
            bw.newLine()
        } else if (mulBy.countOneBits() == 1 + (mulBy.takeHighestOneBit() / mulBy.takeLowestOneBit()).countTrailingZeroBits()) {
            bw.append("RSB R0, R0, R0, LSL #").append(mulBy.countOneBits().toString())
            bw.newLine()
            if (mulBy and 1L != 1L) {
                bw.append("MOV R0, R0, LSL #").append(mulBy.countTrailingZeroBits().toString())
                bw.newLine()
            }
        } else {
            val extRsbs = ArrayList<Long>()
            for (i in longArrayOf(65535, 32767, 16383, 8191, 4095, 2047, 1023, 511, 255, 127, 63, 31, 15, 7, 3)) {
                while (mulBy % i == 0L) {
                    mulBy /= i
                    extRsbs += i
                }
            }
            if (mulBy != 1L) {
                var lChar = '\u0000'
                var cLen = 0
                var cpos = 0
                val charLens = ArrayList<Triple<Int, Char, Int>>()
                val binStr = mulBy.toString(2)
                for (c in binStr) {
                    if (c == lChar) {
                        ++cLen
                    } else {
                        if (cLen != 0) {
                            charLens += Triple(cLen, lChar, cpos)
                            cpos += cLen
                        }
                        lChar = c
                        cLen = 1
                    }
                }
                if (cLen != 0) {
                    charLens += Triple(cLen, lChar, cpos)
                }
                val maxOne = charLens.filter { it.second == '1' }.maxByOrNull { it.first }!!
                if (maxOne.first >= 3) {
                    val xr = (1L shl maxOne.first) - 1L
                    mulBy = mulBy xor (xr shl (mulBy.toString(2).length - maxOne.first - maxOne.third))
                }
                var shr = mulBy / 2
                var shAmt = 1
                while (shr > 1L) {
                    if (shr and 1L == 1L) {
                        bw.append("ADD R1, ${if (mulBy and 1L == 1L && shAmt == 1) "R0" else "R1"}, R0, LSL #").append(shAmt.toString())
                        bw.newLine()
                    } else if (mulBy and 1L == 1L && shAmt == 1) {
                        bw.append("MOV R1, R0, LSL #0")
                        bw.newLine()
                    }
                    ++shAmt
                    shr /= 2
                }
                if (maxOne.first >= 3) {
                    bw.append("RSB R2, R0, R0, LSL #").append(maxOne.first.toString())
                    bw.newLine()
                    bw.append("ADD R0, R0, R2, LSL #").append((binStr.length - maxOne.first - maxOne.third).toString())
                    bw.newLine()
                }
                if (shr >= 1L) {
                    bw.append("ADD R0, R1, R0, LSL #").append((shAmt - if (mulBy <= 2L) 1 else 0).toString())
                    bw.newLine()
                }
            }
            extRsbs.forEach { num ->
                bw.append("RSB R0, R0, R0, LSL #").append(num.countOneBits().toString())
                bw.newLine()
            }
        }
        bw.append("END")
        bw.newLine()
    }
    bw.close()
}
