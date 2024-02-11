package bfs

import java.util.*

private data class Pos(val y: Int, val x: Int, val player: Char)
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)

/**
 * 문제 이름(난이도) : 보스몬스터 전리품 (GOL3)
 * 시간 : 868 ms
 * 메모리 : 150848 KB
 * 링크 : https://www.acmicpc.net/problem/20005
 */
fun main() = with(System.`in`.bufferedReader()) {

    val (m, n, p) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val map = Array(m) { CharArray(n) }
    val queue: Queue<Pos> = LinkedList()

    repeat(m) { y ->
        val line = readLine().toCharArray()
        repeat(n) { x ->
            val cur = line[x]
            map[y][x] = when(cur) {
                'B' -> cur
                'X' -> cur
                '.' -> cur
                else -> {
                    // 플레이어인 경우에만 queue에 추가
                    queue += Pos(y, x, cur)
                    cur
                }
            }
        }
    }

    // a ~ n의 플레이어까지의 dps 배열
    val dps = IntArray(p) {
        val st = StringTokenizer(readLine())
        st.nextToken()
        st.nextToken().toInt()
    }

    // 보스의 체력
    var bossHp = readLine().toInt()

    // 각 플레이어에 대한 방문 배열
    val visited = Array(m) { Array(n) { BooleanArray(p) } }

    // 공격할 수 있는 플레이어 리스트
    val samePosPlayer = BooleanArray(p)
    var prefixDamage = 0
    var result = 0

    // bossHp가 0이하로 떨어지면 종료
    while (bossHp > 0) {
        var time = queue.size
        while (time > 0) {
            val cur = queue.poll()
            val idx = (cur.player - 97).code
            time--

            if(samePosPlayer[idx]) continue

            for (i in 0 until 4) {
                val nx = cur.x + dx[i]
                val ny = cur.y + dy[i]

                if (ny in 0 until m && nx in 0 until n) {
                    if (!visited[ny][nx][idx] && (map[ny][nx] != 'X')) {
                        visited[ny][nx][idx] = true
                        if(map[ny][nx] == 'B') {
                            samePosPlayer[idx] = true
                            prefixDamage += dps[idx]
                            result++
                        }
                        else queue += Pos(ny, nx, cur.player)
                    }
                }
            }
        }
        bossHp -= prefixDamage
    }

    println(result)
    close()

}
