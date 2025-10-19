package list

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m, v) = st.run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    st = StringTokenizer(readLine())
    val loopArray = Array(n) {
        st.nextToken().toInt()
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val lastNextIndex = v - 1

    repeat(m) {
        val index = readLine().toInt()

        val realIndex = if (index < n) index else ((index - lastNextIndex) % (n - lastNextIndex)) + lastNextIndex

        bw.write(loopArray[realIndex].toString())

        bw.newLine()
    }

    bw.flush()
    bw.close()
    close()
}