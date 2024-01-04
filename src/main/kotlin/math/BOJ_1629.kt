package math

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())

    val n1 = st.nextToken().toLong()
    val n2 = st.nextToken().toLong()
    val n3 = st.nextToken().toLong()

    val result = pow(n1, n2, n3)
    println(result.toInt())

}

fun pow(n1: Long, n2: Long, n3: Long): Long {
    if (n2 == 1L) return n1 % n3
    val temp = pow(n1, n2 / 2, n3)

    if (n2 % 2 == 0L) {
        return (temp * temp) % n3
    }
    return ((temp * temp) % n3 * n1) % n3
}