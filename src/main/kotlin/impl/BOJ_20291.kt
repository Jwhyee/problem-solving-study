package impl

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val map = TreeMap<String, Int>()
    val n = readLine().toInt()

    repeat(n) {
        val ext = readLine().substringAfterLast(".")

        map[ext] = map.getOrDefault(ext, 0) + 1
    }

    for ((ext, count) in map) {
        sb.append("$ext ${map[ext]}").append("\n")
    }

    print(sb)
    close()
}