package impl

import java.lang.StringBuilder

private const val ZERO = "0000"
private const val EMPTY = "empty"

fun main() = with(System.`in`.bufferedReader()) {
    var origin = readLine()

    if (origin == "::") {
        println("0000:0000:0000:0000:0000:0000:0000:0000")
        close()
    } else {
        if (origin.contains("::")) {
            origin = origin.replace("::", ":$EMPTY:")
        }

        val originList = origin.split(":")
        val tempList = mutableListOf<String>()
        val originLen = originList.size

        for (i in 0 until originLen) {
            val sb = StringBuilder()
            val cur = originList[i]
            if(cur.isEmpty()) continue

            for (j in cur.length until 4) {
                sb.append("0")
            }
            sb.append(cur)
            tempList += sb.toString()
        }

        var emptySize = (8 - tempList.size) + 1
        val result = Array(8) { ZERO }
        var idx = 0
        for (s in tempList) {
            if (s == EMPTY) {
                while (emptySize-- > 0) {
                    result[idx++] = ZERO
                }
            } else {
                result[idx++] = s
            }
        }
        println(result.joinToString(":"))
    }

}