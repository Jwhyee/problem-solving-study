package back_tracking

import java.lang.StringBuilder
import java.util.StringTokenizer
import kotlin.math.max

/**
 * 문제 이름(난이도) : 숫자 재배치 (SIL1)
 * 시간 : 40044 ms
 * 메모리 : 512 KB
 * 링크 : https://www.acmicpc.net/problem/16943
 */
private lateinit var visited: BooleanArray
private val sb = StringBuilder()
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
    backTracking(0)
    close()
    println(max)
}

private fun backTracking(depth: Int) {
    if (depth == aSize) {
        val st = sb.toString()
        if (st[0] == '0') {
            return
        }
        val result = st.toInt()
        if (result in 0 until b) {
            max = max(result, max)
        }
        return
    }

    for (i in 0 until aSize) {
        if (!visited[i]) {
            visited[i] = true
            sb.append(aStr[i])
            backTracking(depth + 1)
            sb.deleteCharAt(depth)
            visited[i] = false
        }
    }
}