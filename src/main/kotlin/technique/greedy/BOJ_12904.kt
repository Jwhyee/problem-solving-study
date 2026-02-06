package technique.greedy

import java.lang.StringBuilder

/**
 * 문제 이름(난이도) : A와 B (GOL5)
 * 시간 : 148 ms
 * 메모리 : 17728 KB
 * 링크 : https://www.acmicpc.net/problem/12904
 */
fun main() = with(System.`in`.bufferedReader()) {
    val s = readLine()
    var t = readLine()

    // 역순으로 탐색
    while (true) {
        // 가능하면 1 출력 후 종료
        if (t == s) {
            println(1)
            return@with
        }

        // 탐색할 수 없으면 멈춤
        if (t.length == 1) {
            break
        }

        val last = t.length - 1

        // 마지막 문자가 A면 A를 빼고, B면 B 빼고 뒤집음
        if (t.endsWith('A')) {
            t = t.substring(0, last)
        } else if (t.endsWith('B')) {
            t = (t.substring(0, last)).reversed()
        }
    }

    println(0)
    close()
}