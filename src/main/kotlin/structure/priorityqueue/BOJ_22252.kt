package structure.priorityqueue

import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 정보 상인 호석(GOL5)
 * 시간 : 1540 ms
 * 메모리 : 313288 KB
 * 링크 : https://www.acmicpc.net/problem/22252
 */
fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val n = readLine().toInt()
    val info = HashMap<String, PriorityQueue<Int>>()
    var sum = 0L

    for (i in 0 until n) {
        val st = StringTokenizer(readLine())
        // 쿼리 종류
        val num = st.nextToken().toInt()
        // 이름
        val name = st.nextToken()
        // k 또는 b
        val m = st.nextToken().toInt()
        // 내림차순 pq
        var pq = PriorityQueue<Int> {a, b -> b.toInt() - a.toInt()}

        // 정보맵에 들어있을 경우 pq 가져오기, 없을 경우 맵에 추가
        if (info.containsKey(name)) {
            pq = info[name]!!
        } else {
            info[name] = pq
        }
        when (num) {
            // 1 name k c1 c2 .. ck : 이름이 name인 고릴라가 k개의 정보를 얻었으며, 가치는 c1 .. ck
            1 -> {
                for (idx in 0 until m) {
                    pq += st.nextToken().toInt()
                }
            }
            // 2 name b : 호석이가 name인 고릴라에게 b개의 정보를 구매
            2 -> {
                if (info.containsKey(name)) {
                    val infoValues = info[name]!!
                    // 고릴라가 가진 정보들 중 가잔 비싼 b개를 구매
                    if (infoValues.size >= m) {
                        repeat(m) {
                            sum += infoValues.poll()
                        }
                    }
                    // 고릴라가 가진 정보가 b개 이하일 경우 모두 구매
                    else {
                        while (infoValues.isNotEmpty()) {
                            sum += infoValues.poll()
                        }
                    }
                }
            }
        }
    }
    bw.append("${sum}\n")
    bw.close()
    close()
}