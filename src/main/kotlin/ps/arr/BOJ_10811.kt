package ps.arr

import java.util.StringTokenizer

fun main() {
    var st = StringTokenizer(readln())
    val N = st.nextToken().toInt()
    val M = st.nextToken().toInt()

    val arr = Array<Int>(N) { it + 1 }

    repeat(M) {
        st = StringTokenizer(readln())
        var i = st.nextToken().toInt()
        var j = st.nextToken().toInt()

        while (i < j) {
            val temp = arr[i - 1]
            arr[i - 1] = arr[j - 1]
            arr[j - 1] = temp
            i++
            j--
        }
    }

    arr.forEach { print("$it ") }

}