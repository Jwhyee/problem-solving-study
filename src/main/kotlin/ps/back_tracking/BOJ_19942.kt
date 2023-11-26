package ps.back_tracking

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

// 단백질, 지방, 탄수화물, 비타민, 가격
data class Nutrient(val p: Int, val f: Int, val c: Int, val v: Int, val price: Int)

/**
 * 문제 이름(난이도) : 다이어트(GOL4)
 * 시간 : 188 ms
 * 메모리 : 24756 KB
 * 링크 : https://www.acmicpc.net/problem/19942
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    // 입력
    val N = readLine().toInt()
    val standards = readLine().split(" ").map { it.toInt() }.toIntArray()

    // 배열 초기화
    val visited = BooleanArray(N)
    val ingredients = mutableListOf<Nutrient>()
    val idxs = mutableListOf<Int>()

    repeat(N) {
        val st = StringTokenizer(readLine())
        ingredients += Nutrient(st.nextToken().toInt()
            , st.nextToken().toInt()
            , st.nextToken().toInt()
            , st.nextToken().toInt()
            , st.nextToken().toInt()
        )
    }

    var min = Int.MAX_VALUE
    val result = mutableListOf<Int>()

    fun backtracking(depth: Int, pSum: Int, fSum: Int, cSum: Int, vSum: Int, priceSum: Int) {
        val cur = ingredients[depth]
        val curPSum = pSum + cur.p
        val curFSum = fSum + cur.f
        val curCSum = cSum + cur.c
        val curVSum = vSum + cur.v
        val curPriceSum = priceSum + cur.price

        visited[depth] = true
        idxs += depth

        // 현재 까지의 가격의 합이 최저보다 클 경우 탐색 가치가 없기 때문에 돌아감
        // 그렇지 않을 경우 최소 비용 보장
        if (curPriceSum >= min) {
            return
        }

        // 각 영양분의 합이 기준 이상일 경우
        if (curPSum >= standards[0] && curFSum >= standards[1] && curCSum >= standards[2] && curVSum >= standards[3]) {
            // 결과값 다시 지정
            result.clear()
            min = curPriceSum
            result += idxs
            return
        }
        // 영양분의 합이 부족할 경우
        else {
            // 전체 식재료 중 방문하지 않은 영양소에 대해 방문
            for (i in depth + 1 until ingredients.size) {
                if (visited[i].not()) {
                    backtracking(i, curPSum, curFSum, curCSum, curVSum, curPriceSum)
                    // 방문한 인덱스를 리스트에서 제거 후 미방문 처리
                    idxs.removeLast()
                    visited[i] = false
                }
            }
        }
    }

    // 식재료의 크기 만큼 반복
    for (i in ingredients.indices) {
        idxs.clear()
        visited.fill(false)

        backtracking(i, 0, 0, 0, 0, 0)
    }

    // 결과값이 없을 경우 -1 출력
    if (result.isEmpty()) {
        println(-1)
    } else {
        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.append("$min").append('\n')
        result.forEach {
            bw.append("${it + 1} ")
        }
        bw.flush()
        bw.close()
    }
}