package ps.arr

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val sb = StringBuilder()
    var st = StringTokenizer(readLine())

    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val arr = Array(n) { 0 }

    repeat(m) {
        st = StringTokenizer(readLine())
        val i = st.nextToken().toInt()
        val j = st.nextToken().toInt()
        val k = st.nextToken().toInt()
        init(arr, i - 1, j - 1, k)
    }


    for (i in arr) {
        sb.append("$i ")
    }

    print(sb)

}

fun init(arr: Array<Int>, start: Int, end: Int, num: Int) {
    for (i in start..end) {
        arr[i] = num
    }
}