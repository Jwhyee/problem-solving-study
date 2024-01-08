package hash

import java.util.*

/**
 * 문제 이름(난이도) : 나는야 포켓몬 마스터 이다솜(SIL4)
 * 시간 : 500 ms
 * 메모리 : 41508 KB
 * 링크 : https://www.acmicpc.net/problem/1620
 */
fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val bw = System.out.bufferedWriter()
    val nameMap = hashMapOf<String, Int>()
    val list = mutableListOf<String>()

    for (i in 1 .. n) {
        val name = readLine()
        nameMap[name] = i
        list += name
    }

    for (i in 0 until m) {
        val q = readLine()
        if (q[0].isDigit()) {
            bw.append(list[q.toInt() - 1])
            bw.newLine()
        } else {
            bw.append("${nameMap[q]}")
            bw.newLine()
        }
    }
    bw.flush()
    bw.close()
}