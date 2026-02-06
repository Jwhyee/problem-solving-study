package impl.simulation

import java.util.Stack

private const val EMPTY = "FRULA"

fun main() = with(System.`in`.bufferedReader()) {
    val target = readLine()
    val keyword = readLine()

    println(bomb(target, keyword))

    close()
}

private fun bomb(target: String, keyword: String): String {
    val stack = Stack<Char>()
    val sb = StringBuilder()

    val keywordSize = keyword.length
    var index = 0

    while (true) {
        if (index == target.length) break
        stack.push(target[index++])


        if (sb.endsWith(keyword)) {
            sb.delete(sb.length - keywordSize, sb.length)
        }
    }

    return if (sb.isEmpty()) EMPTY else sb.toString()
}