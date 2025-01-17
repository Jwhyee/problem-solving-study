package simulation

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

/**
 * 문제 이름(난이도) : 선물이 넘쳐흘러(GOL5)
 * 시간 : 0 ms
 * 메모리 : 0 KB
 * 링크 : https://www.acmicpc.net/problem/17259
 */
import java.util.*

data class Worker(val r: Int, val c: Int, val t: Int, var remainingTime: Int = 0)
data class Gift(val id: Int, val timeOnBelt: Int)

fun main() {
    val scanner = Scanner(System.`in`)

    // 입력 처리
    val (B, N, M) = scanner.nextLine().split(" ").map { it.toInt() }
    val workers = mutableListOf<Worker>()
    repeat(N) {
        val (r, c, t) = scanner.nextLine().split(" ").map { it.toInt() }
        workers.add(Worker(r, c, t))
    }

    // 벨트 경로 설정
    val belt = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until B) belt.add(0 to i) // 상단
    for (i in 1 until B) belt.add(i to B - 1) // 오른쪽
    for (i in B - 2 downTo 0) belt.add(B - 1 to i) // 하단
    for (i in B - 2 downTo 1) belt.add(i to 0) // 왼쪽

    // 시뮬레이션 변수 초기화
    val beltState = Array(belt.size) { null as Gift? } // 각 위치에 있는 선물
    var time = 0
    var nextGiftId = 0 // 다음에 추가될 선물 ID
    var packedGifts = 0 // 포장된 선물 수
    var discardedGifts = 0 // 폐기된 선물 수

    while (packedGifts + discardedGifts < M) {
        // 1. 벨트를 한 칸씩 이동
        for (i in belt.size - 1 downTo 1) {
            beltState[i] = beltState[i - 1]
        }
        beltState[0] = if (nextGiftId < M) Gift(nextGiftId++, time) else null

        // 2. 벨트 끝 지점에서 폐기 처리
        if (beltState.last() != null) {
            discardedGifts++
            beltState[belt.size - 1] = null
        }

        // 3. 직원들이 선물을 포장
        for (worker in workers) {
            if (worker.remainingTime > 0) {
                // 작업 중인 경우
                worker.remainingTime--
                if (worker.remainingTime == 0) {
                    packedGifts++ // 작업 완료 시 포장된 선물 수 증가
                }
                continue
            }

            // 작업 중이 아닌 경우 포장할 선물 탐색
            val adjacentPositions = listOf(
                worker.r - 1 to worker.c, // 위
                worker.r + 1 to worker.c, // 아래
                worker.r to worker.c - 1, // 왼쪽
                worker.r to worker.c + 1  // 오른쪽
            )

            val availableGifts = adjacentPositions.mapNotNull { pos ->
                val beltIndex = belt.indexOf(pos)
                if (beltIndex != -1 && beltState[beltIndex] != null) {
                    beltIndex to beltState[beltIndex]!!
                } else null
            }

            if (availableGifts.isNotEmpty()) {
                // 가장 오래된 선물을 포장
                val (beltIndex, gift) = availableGifts.minByOrNull { it.second.timeOnBelt }!!
                beltState[beltIndex] = null // 선물을 벨트에서 제거
                worker.remainingTime = worker.t // 작업 시작
            }
        }

        time++
    }

    // 출력: 포장된 선물 수
    println(packedGifts)
}