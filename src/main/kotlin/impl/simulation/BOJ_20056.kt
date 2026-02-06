package impl.simulation

import java.util.*

/**
 * 문제 이름(난이도) : 마법사 상어와 파이어볼 (GOL4)
 * 시간 : ? ms
 * 메모리 : ? KB
 * 링크 : https://www.acmicpc.net/problem/20056
 */
private val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
private val dx = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)
private data class FireBall(
    var m: Int,
    var s: Int,
    var d: Int,
    var isMoved: Boolean
)

fun main() = with(System.`in`.bufferedReader()){
    val (N, M, K) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val posQueue: Queue<Pair<Int, Int>> = LinkedList()
    val map: Array<Array<Queue<FireBall>>> = Array(N) { Array(N) { LinkedList() } }

    repeat(M) { idx ->
        val st = StringTokenizer(readLine())
        val r = st.nextToken().toInt() - 1
        val c = st.nextToken().toInt() - 1
        val m = st.nextToken().toInt()
        val s = st.nextToken().toInt()
        val d = st.nextToken().toInt()
        map[r][c] += FireBall(m, s, d, false)
        posQueue += (r to c)
    }

    repeat(K) { idx ->
        val tempQueue: Queue<Pair<Int, Int>> = LinkedList()
        val tempMap = mutableMapOf<String, Int>()
        while (posQueue.isNotEmpty()) {
            val curPos = posQueue.poll()
            val y = curPos.first
            val x = curPos.second
            val curPosQueue = map[y][x]
            while (curPosQueue.isNotEmpty()) {
                val cur = curPosQueue.poll()
                if(!cur.isMoved) {
                    // 1. 모든 파이어볼이 자신의 방향(d), 속력(s)칸 만큼 이동한다.
                    val ny = (y + dy[cur.d] * cur.s + N) % N
                    val nx = (x + dx[cur.d] * cur.s + N) % N
                    tempMap["$ny/$nx"] = tempMap.getOrDefault("$ny/$nx", 0) + 1
                    map[ny][nx] += cur.also { it.isMoved = true }
                    tempQueue.contains(ny to nx).takeIf { !it }?.let {
                        tempQueue += (ny to nx)
                    }
                }
            }
            println("map = $tempMap")
            map.forEach { row ->
                row.forEach { fb ->
                    fb.forEach {
                        it.isMoved = false
                    }
                }
            }
            tempMap.forEach { key, value ->
                if(value >= 2) {
                    // 2. 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 다음과 같은 일이 일어난다.
                    val (py, px) = StringTokenizer(key, "/").run {
                        nextToken().toInt() to nextToken().toInt()
                    }
                    val curQueue = map[py][px]
                    // 2-1. 같은 칸에 있는 파이어볼은 모두 하나로 합쳐진다.
                    // 2-3-1. 질량은 [(합쳐진 파이어볼 질량의 합)/5]이다.
                    val tempM = curQueue.sumOf { it.m }
                    val nm = if(tempM > 0) tempM / 5 else 0
                    // 2-3-2. 속력은 [(합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)]이다.
                    val tempS = curQueue.sumOf { it.s }
                    val ns = if(tempS > 0) tempS / curQueue.size else 0
                    val isEven = curQueue.all { it.d % 2 == 0 }
                    map[py][px].clear()
                    // 2-3-4. 질량이 0인 파이어볼은 소멸되어 없어진다.
                    if(nm != 0) {
                        // 2-2. 파이어볼은 4개의 파이어볼로 나누어진다.
                        repeat(4) { num ->
                            // 2-3-3. 합쳐지는 파이어볼의 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0, 2, 4, 6이고, 그렇지 않으면 1, 3, 5, 7이다.
                            val nd = if(isEven) num * 2 else (num * 2) + 1
                            val nfb = FireBall(nm, ns, nd, false)
                            println("nfb = $nfb")
                            map[py][px] += nfb
                        }
                    }
                }
            }
        }
        posQueue += tempQueue
        tempMap.clear()
        tempQueue.clear()
    }

}