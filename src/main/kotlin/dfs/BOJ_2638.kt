package dfs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

var N:Int = 0
var M:Int = 0

val dx = arrayOf(1, 0, -1, 0)
val dy = arrayOf(0, 1, 0, -1)

/**
 * 문제 이름(난이도) : 치즈(GOL3)
 * 시간 : 260 ms
 * 메모리 : 21880 KB
 * 링크 : https://www.acmicpc.net/problem/2638
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())

    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    // 초기화
    val map = Array(N) { IntArray(M) }
    val visited = Array(N) { BooleanArray(M) }

    for (y in 0 until N) {
        st = StringTokenizer(readLine())
        for (x in 0 until M) {
            map[y][x] = st.nextToken().toInt()
        }
    }

    var time = 0

    loop@while (true) {
        // 외부 공기 초기화 및 확인
        // 치즈 내부에 있는 공간은 치즈 외부 공기와 접촉하지 않는 것으로 가정
        val outerAir = Array(N) { BooleanArray(M) }
        airCheck(map, outerAir, 0, 0)

        // 실내온도에 내어놓으면 공기와 접촉하여 천천히 녹는다.
        // 현재 좌표가 치즈인 경우 주변에 외부 공기와 몇 칸 닿아있는지 확인하고 제거
        for (y in 0 until N) {
            for (x in 0 until M) {
                if(map[y][x] == 1 && visited[y][x].not())
                    removeCheese(map, visited, outerAir, y, x)
            }
        }

        var cnt = 0
        for (i in 0 until N) {
            if(false in outerAir[i]) cnt++
        }

        if(cnt == 0) break@loop
        time++

    }

    println(time)

}

fun checkRange(y: Int, x: Int): Boolean {
    return y in 0 until N && x in 0 until M
}

fun removeCheese(
    map: Array<IntArray>, visited: Array<BooleanArray>, outerAir: Array<BooleanArray>,
    y: Int, x: Int) {
    var cnt = 0

    // 접촉한 면이 외부 공기인지 확인
    // 4변 중에서 적어도 2변 이상이 실내온도의 공기와 접촉한 것은 정확히 한시간만에 녹아 없어져 버린다.
    for (i in 0..3) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (checkRange(ny, nx)) {
            if (outerAir[ny][nx]) {
                cnt++
            }
        }
    }

    // 현재 좌표에 대한 방문 처리 + 녹았으니 공기로 변환
    if (cnt >= 2) {
        visited[y][x] = true
        map[y][x] = 0
    }

}

fun airCheck(map: Array<IntArray>, outerAir: Array<BooleanArray>, y: Int, x: Int) {
    outerAir[y][x] = true

    for (i in 0..3) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (checkRange(ny, nx)) {
            if (map[ny][nx] == 0 && outerAir[ny][nx].not()) {
                airCheck(map, outerAir, ny, nx)
            }
        }
    }
}

/*
8 9
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 1 1 0
0 1 0 1 1 1 0 1 0
0 1 0 0 1 0 0 1 0
0 1 0 1 1 1 0 1 0
0 1 1 0 0 0 1 1 0
0 0 0 0 0 0 0 0 0
*/