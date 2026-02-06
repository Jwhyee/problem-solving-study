package technique.control

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val result = br.readLine().toInt()

    val n = br.readLine().toInt()

    var sum = 0

    for (i in 0 until n) {
        val st = StringTokenizer(br.readLine())
        val price = st.nextToken().toInt()
        val qty = st.nextToken().toInt()

        sum += (price * qty)
    }

    val answer = if(sum == result) "Yes" else "No"
    println(answer)
}