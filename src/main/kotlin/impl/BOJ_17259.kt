package impl

import java.util.StringTokenizer

private data class Worker(
    var r: Int,
    var c: Int,
    var t: Int,
    var processingTime: Int = 0,
    var isWorking: Boolean = false
) {
    private val isFinish: Boolean
        get() = t == processingTime

    private fun reset() {
        processingTime = 0
        isWorking = false
    }

    fun setData(r: Int, c: Int, t: Int) {
        this.r = r
        this.c = c
        this.t = t
    }

    fun doWork() {
        processingTime++
        isWorking = true
        if(isFinish) {
            reset()
        }
    }
}

private const val WORKER = "W"
private const val EMPTY = "O"
private const val PRESENT = "P"
private val dy = arrayOf(1, -1, 0)
private val dx = arrayOf(0, 0, -1)
private var presentCount = 0

/**
 * 문제 이름(난이도) : 선물이 넘쳐흘러(GOL5)
 * 시간 : 0 ms
 * 메모리 : 0 KB
 * 링크 : https://www.acmicpc.net/problem/17259
 */
fun main() = with(System.`in`.bufferedReader()) {
    val (B, N, M) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val map = Array(B) { Array(B) { EMPTY } }
    val belt = Array((B * 3) - 2) { EMPTY }
    val workers = Array(N) { Worker(0, 0, 0) }

    repeat(N) {
        val (r, c, t) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }
        map[r][c] = WORKER
        workers[it].setData(r, c, t)
    }

    val beltSize = belt.size - 1
    var flag = false
    var result = 0
    var idx = 1
    while(true) {
        if(flag) {
            break
        }
        moveBelt(belt, M)
        flag = belt.all { it == EMPTY }
        workers.forEach { worker ->
            if(worker.isWorking) {
                worker.doWork()
            }
        }
        for (idx in beltSize downTo 0) {
            val square = belt[idx]
            if(square == PRESENT) {
                val (r, c) = findPresentPosition(idx, B)
                findWorker(map, workers, r, c)?.let {
                    it.isWorking = true
                    belt[idx] = EMPTY
                    result++
                }
            }
        }
    }
    println(result)
}

private fun moveBelt(belt: Array<String>, M: Int) {
    val size = belt.size - 1
    if (belt[size] == PRESENT) {
        belt[size] = EMPTY
    }
    for (i in size downTo 1) {
        belt[i] = belt[i - 1]
    }
    if (presentCount++ < M) {
        belt[0] = PRESENT
    } else {
        belt[0] = EMPTY
    }
}

private fun findPresentPosition(idx: Int, B: Int) = when(idx) {
    in 0 until B -> {
        0 to idx
    }
    in B until (B * 2) - 1 -> {
        (idx - B) + 1 to B - 1
    }
    else -> {
        B - 1 to (B * 3) - 3 - idx
    }
}

private fun isValid(B: Int, r: Int, c: Int) = r in 0 until B && c in 0 until B

private fun findWorker(
    map: Array<Array<String>>,
    workers: Array<Worker>, r: Int, c: Int
): Worker? {
    repeat(3) { idx ->
        val nr = r + dy[idx]
        val nc = c + dx[idx]
        if(isValid(map.size, nr, nc) && map[nr][nc] == WORKER) {
            return workers.find { worker -> worker.r == nr && worker.c == nc }?.let {
                if(it.isWorking) null
                else it
            }
        }
    }
    return null
}