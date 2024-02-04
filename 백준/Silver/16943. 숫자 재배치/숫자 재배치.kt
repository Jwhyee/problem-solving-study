package back_tracking

import java.util.StringTokenizer
import kotlin.math.max

private lateinit var visited: BooleanArray
private var aSize = 0
private var aStr = ""
private var a = 0
private var b = 0
private var max = -1
fun main() = with(System.`in`.bufferedReader()){
    val st = StringTokenizer(readLine())
    aStr = st.nextToken()
    aSize = aStr.length
    a = aStr.toInt()
    b = st.nextToken().toInt()
    visited = BooleanArray(aSize)
    backTracking(0, "")
    println(max)
}

private fun backTracking(depth: Int, str: String) {
    if (depth == aSize) {
        val result = str
        if (result.startsWith("0")) {
            return
        }
        val _result = result.toInt()
        if (_result in 0 until b) {
            max = max(_result, max)
        }
        return
    }

    for (i in 0 until aSize) {
        if (!visited[i]) {
            visited[i] = true
            backTracking(depth + 1, str + aStr[i])
            visited[i] = false
        }
    }
}