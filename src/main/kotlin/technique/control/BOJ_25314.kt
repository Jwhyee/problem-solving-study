package technique.control

import java.lang.StringBuilder

fun main() {
    val N = readln().toInt()
    val sb = StringBuilder()

    repeat(N / 4) {
        sb.append("long ")
    }
    sb.append("int")

    println(sb)
}