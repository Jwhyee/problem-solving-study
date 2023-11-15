import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val sb = StringBuilder()
    val n = readLine().toInt()
    repeat(n) {
        val str = readLine()
        sb.append(str[0]).append(str.get(str.length-1)).append('\n')
    }
    println(sb)
}