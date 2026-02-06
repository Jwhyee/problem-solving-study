package structure.arr

import java.util.StringTokenizer

fun main() {
    val n = readln().toInt()

    val arr = Array<Int>(n){ 0 }
    val st = StringTokenizer(readln())

    for (i in 0 until n) {
        arr[i] = st.nextToken().toInt()
    }

    var cnt = 0
    val target = readln().toInt()

    for (i in arr) {
        if(i == target) cnt++
    }

    println(cnt)
}