package ps.slicing_window

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 문제 이름(난이도) : 문자열 교환(GOL5)
 * 시간 : 92 ms
 * 메모리 : 12168 KB
 * 링크 : https://www.acmicpc.net/problem/1522
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val str = readLine()

    var answer = 1000
    var bCnt = 0
    var aCnt = 0

    // str을 돌면서 a가 몇 개 있는지 확인
    for (c in str) {
        if(c == 'a') aCnt++
    }

    // str의 길이만큼 반복
    for (i in str.indices) {
        bCnt = 0
        // i번째 위치부터 a의 개수를 더한 위치까지 탐색
        for (j in i until i + aCnt) {
            // j를 문자열의 길이로 나눈 나머지의 값을 idx로 지정
            val idx = j % str.length
            // 만약 문자열의 idx 위치가 'b'일 경우 카운트 증가
            if (str[idx] == 'b') bCnt++
        }
        // answer 최신화
        answer = if(answer > bCnt) bCnt else answer
    }
    println(answer)
}