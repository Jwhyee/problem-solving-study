package two_pointer

import java.util.StringTokenizer
import kotlin.math.abs

/**
 * 문제 이름(난이도) : A와 B (GOL5)
 * 시간 : 148 ms
 * 메모리 : 17728 KB
 * 링크 : https://www.acmicpc.net/problem/20366
 */
fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val n = readLine().toInt()

    val st = StringTokenizer(readLine())

    val arr = IntArray(n) {
        st.nextToken().toInt()
    }

    arr.sort()

    var answer = Int.MAX_VALUE

    for (i in 0 until n - 1) {
        for (j in i + 1 until n) {
            val sum1 = arr[i] + arr[j]
            var left = 0
            var right = n - 1

            while (left < right) {
                if (left == i || left == j) {
                    left++
                    continue
                }
                if (right == i || right == j) {
                    right--
                    continue
                }

                val sum2 = arr[left] + arr[right]
                val result = sum1 - sum2

                if (result < 0) right--
                else if (result > 0) left++
                else {
                    bw.write("0")
                    bw.close()
                    close()
                    return@with
                }
                answer = answer.coerceAtMost(abs(result))
            }

        }
    }

    bw.write("$answer")
    bw.close()
    close()

}