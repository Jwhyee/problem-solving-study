package impl

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.ArrayDeque
import java.util.StringTokenizer
import java.util.LinkedList
import java.util.Queue

/**
 * 문제 이름(난이도) : 뱀(GOL4)
 * 시간 : 108 ms
 * 메모리 : 12744 KB
 * 링크 : https://www.acmicpc.net/problem/3190
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val N = readLine().toInt()
    val map = Array(N) { IntArray(N) }

    // 사과 입력
    val K = readLine().toInt()
    repeat(K) {
        val st = StringTokenizer(readLine())
        val y = st.nextToken().toInt() - 1
        val x = st.nextToken().toInt() - 1
        map[y][x] = -1
    }

    // 회전 입력
    val L = readLine().toInt()
    val turnList:Queue<Pair<Int, String>> = LinkedList()
    repeat(L) {
        val st = StringTokenizer(readLine())
        turnList += Pair(st.nextToken().toInt(), st.nextToken())
    }

    // 뱀의 길이는 1 이다. 뱀은 처음에 오른쪽을 향한다.
    val dy = intArrayOf(-1, 0, 1, 0)
    val dx = intArrayOf(0, 1, 0, -1)

    var dir = 1
    var time = 0

    val snake = ArrayDeque<Pair<Int, Int>>()
    snake.addFirst(0 to 0)

    fun isPossible(x: Int, y: Int): Boolean = (x in 0 until N && y in 0 until N)
    fun isApple(x: Int, y: Int): Boolean = map[y][x] == -1

    while (true) {
        val ny = snake.peekFirst().first + dy[dir]
        val nx = snake.peekFirst().second + dx[dir]

        // 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
        snake.addFirst(Pair(ny, nx))
        val cur = snake.peekFirst()

        // 벽 혹은 자기 자신과 부딪히면 종료
        if(!isPossible(cur.second, cur.first)) break
        if(map[ny][nx] == 1) break

        // 만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
        if (!isApple(nx, ny)) {
            val tail = snake.pollLast()
            map[tail.first][tail.second] = 0
        }

        // 만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
        map[ny][nx] = 1

        time++

        turnList.peek()?.let {
            if(it.first == time) {
                dir = when(it.second) {
                    "L" -> (dir + 3) % 4
                    "D" -> (dir + 1) % 4
                    else -> 0
                }
                turnList.poll()
            }
        }
    }

    println(++time)

}