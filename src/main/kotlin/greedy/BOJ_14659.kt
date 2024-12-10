package greedy

import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()
    var max = 0
    val arr = readLine().split(" ").map { it.toInt() }.toTypedArray()
    for (i in arr.indices) {
        val cur = arr[i]
        var cnt = 0
        for (j in (i + 1) until arr.size) {
            val next = arr[j]
            if(cur > next) {
                cnt++
            } else break
        }
        max = max(max, cnt)
    }
    println(max)
}