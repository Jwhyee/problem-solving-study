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
private data class Employee(
    val y: Int,
    val x: Int,
    var t: Int,
    var processingTime: Int = 0,
    var isWorking: Boolean = false
) {
    fun isFinish() = t == processingTime
    fun reset() {
        processingTime = 0
        isWorking = false
    }
    fun doWork() {
        processingTime++
        isWorking = true
        if(isFinish()) {
            reset()
        }
    }
}
private data class Present(
    var y: Int,
    var x: Int,
    var dir: Int = 0,
    val id: Int = 0
) {
    fun moveNext(B: Int, depth: Int = 0): Boolean {
        val ny = y + dy[dir]
        val nx = x + dx[dir]
        if(ny in 0 until B && nx in 0 until B) {
            y = ny
            x = nx
        } else {
            dir += 1
            if(dir == 3) { return false }
            moveNext(B, depth + 1)
        }
        return true
    }
}
private val dy = intArrayOf(0, 1, 0)
private val dx = intArrayOf(1, 0, -1)
private const val EMPLOYEE = "E"

private lateinit var employees: Array<Employee>
private lateinit var map: Array<Array<String>>

fun main() = with(System.`in`.bufferedReader()) {
    val (B, N, M) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    // 욱제는 공장에서 M개의 선물을 포장하기 위해 N명의 직원을 고용
    // i번째 직원은 선물 하나를 포장하는 데 Ti초가 걸린다.
    map = Array(B) { Array(B) { "O" } }
    employees = Array(N) { Employee(0, 0, 0) }
    for (i in 0 until N) {
        val (y, x, time) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }
        map[y][x] = EMPLOYEE
        employees[i] = Employee(y, x, time)
    }

    println(action(M))
}

private fun action(M: Int): Int {
    var id = 0
    var result = 0
    val belt: Deque<Present> = ArrayDeque()
    var presentCount = M
    val removeList = arrayListOf<Present>()
    while (true) {
        // 벨트는 1초 간격으로 시작 지점에서 ⊐ 모양을 따라 끝 지점을 향해 한 칸씩 움직인다.
        // 벨트가 한 칸 움직이면 시작 지점에 선물이 하나씩 놓인다.
        // 최초에 벨트 위에는 아무것도 없다.
        // 선물 M개가 모두 시작 지점에 오르고 나면, 시작 지점에는 더 이상 새로운 선물이 놓이지 않는다.
        if(presentCount-- > 0) {
            belt.addFirst(Present(0, 0, id = ++id))
        }
        employees.forEach {
            if(it.isWorking) {
                it.doWork()
            }
        }
        // 직원들은 상하좌우의 컨테이너 벨트 위에 있는 선물 중 하나를 포장
        // 만약 인접한 선물이 2개 이상이라면, 벨트 위에 더 오래 올려져 있던 선물을 포장
        val beltSize = belt.size
        for (i in beltSize - 1 downTo 0) {
            val present = belt.elementAt(i)
            val employee = findEmployee(present)
            if(employee != null) {
                employee.isWorking = true
                result++
                removeList += present
            } else {
                if(!present.moveNext(map.size)) {
                    removeList += present
                }
            }
        }
        belt.removeAll(removeList.toSet())
        if(belt.isEmpty()) break
    }
    return result
}

private fun findEmployee(present: Present): Employee? {
    val maxSize = map.size
    val py = present.y
    val px = present.x
    val employee = if(py == 0) {
        if(map[1][px] == EMPLOYEE) {
            employees.find { it.y == 1 && it.x == px }
        } else null
    } else if(py == maxSize -1) {
        if(map[py - 1][px] == EMPLOYEE) {
            employees.find { it.y == py - 1 && it.x == px }
        } else null
    } else {
        if(map[py][px - 1] == EMPLOYEE) {
            employees.find { it.y == py && it.x == px - 1}
        } else null
    }
    return employee?.let {
        if(!it.isWorking) {
            it
        } else {
            null
        }
    }
}