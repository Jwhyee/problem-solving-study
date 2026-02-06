package impl.simulation

import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val arr: Array<Queue<String>> = Array(n) { LinkedList() }

    var st: StringTokenizer
    var cnt = 0
    repeat(n) {
        st = StringTokenizer(readLine())
        while(st.hasMoreTokens()) {
            cnt++
            arr[it] += st.nextToken()
        }
    }

    st = StringTokenizer(readLine())
    val tokenSize = st.countTokens()
    if(tokenSize != cnt) {
        println("Impossible")
        return
    }

    while (st.hasMoreTokens()) {
        val keyword = st.nextToken()
        val isOk = arr.map {
            val hasIt = it.peek() == keyword
            if(hasIt) {
                it.poll()
            }
            hasIt
        }.any { it }

        if (!isOk) {
            println("Impossible")
            return
        }
    }

    println("Possible")
}