/**
 * @author Jacob Huebner, Nicholas Johnson, Kiel Dowdle
 * 
 * Scored 36/36 (100%)
 */

fun main() {
    val (woodPerTable, slots, woodPerSlot, wood) = readLine()!!.split(" ").map(String::toLong)
    var low = 0L
    var high = (wood / woodPerTable) + 1
    var mid = (high + low) shr 1
    while (low != high){
        if(slots - mid >= (wood - (mid * woodPerTable) + woodPerTable - 1) / woodPerSlot) {
            low = mid
        } else {
            high = mid - 1
        }
        mid = ((high + low) shr 1) + 1
    }
    println(mid)
}
