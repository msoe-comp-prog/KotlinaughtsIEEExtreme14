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
        } else if (java.lang.Long.bitCount(mulBy) == 1) {
            bw.append("MOV R0, R0, LSL #").append(java.lang.Long.numberOfTrailingZeros(mulBy).toString())
            bw.newLine()
        } else if (java.lang.Long.bitCount(mulBy) == 2) {
            if (mulBy and 1L != 1L) {
                bw.append("MOV R0, R0, LSL #").append(java.lang.Long.numberOfTrailingZeros(mulBy).toString())
                bw.newLine()
            }
            bw.append("ADD R0, R0, R0, LSL #").append(
                (1 + java.lang.Long.numberOfTrailingZeros(
                    (mulBy ushr (java.lang.Long.numberOfTrailingZeros(mulBy) + 1))
                )).toString()
            )
            bw.newLine()
        } else if (java.lang.Long.bitCount(mulBy) == 32) {
            bw.append("RSB R0, R0, R0, LSL #16")
            bw.newLine()
            bw.append("ADD R0, R0, R0, LSL #16")
            bw.newLine()
        } else if (java.lang.Long.bitCount(mulBy) == 1 + java.lang.Long.numberOfTrailingZeros(
                java.lang.Long.highestOneBit(
                    mulBy
                ) / java.lang.Long.lowestOneBit(mulBy)
            )
        ) {
            bw.append("RSB R0, R0, R0, LSL #").append(java.lang.Long.bitCount(mulBy).toString())
            bw.newLine()
            if (mulBy and 1L != 1L) {
                bw.append("MOV R0, R0, LSL #").append(java.lang.Long.numberOfTrailingZeros(mulBy).toString())
                bw.newLine()
            }
        } else {
            val extRsbs = ArrayList<Long>()
            for (i in longArrayOf(1048575, 514287, 262143, 131071, 65535, 32767, 16383, 8191, 4095, 2047, 1023, 511, 255, 127, 63, 31, 15, 7, 3)) {
                while (mulBy % i == 0L) {
                    mulBy /= i
                    extRsbs += i
                }
            }
            if (mulBy != 1L) {
                var shr = mulBy / 2
                var shAmt = 1
                while (shr != 1L) {
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
                    bw.append("ADD R0, R1, R0, LSL #").append(shAmt.toString())
                    bw.newLine()
            }
            extRsbs.forEach { num ->
                bw.append("RSB R0, R0, R0, LSL #").append(java.lang.Long.bitCount(num).toString())
                bw.newLine()
            }
        }
        bw.append("END")
        bw.newLine()
    }
    bw.close()
}
