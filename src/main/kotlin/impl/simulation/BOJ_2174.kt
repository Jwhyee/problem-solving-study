package impl.simulation

import java.util.StringTokenizer

private data class RobertDowneyJr(
   var num: Int = 0,
   var y: Int = 0,
   var x: Int  = 0,
   var dir: Direction = Direction.NORTH
)

private enum class Direction(val c: Char) {
   NORTH('N') {
      override fun findNextDirection(order: Char, repeat: Int): Direction {
         val r = repeat % 4
         return when(order) {
            'L' -> { when(r) { 1 -> WEST;2 -> SOUTH;3 -> EAST;else -> NORTH } }
            'R' -> { when (r) { 1 -> EAST;2 -> SOUTH;3 -> WEST;else -> NORTH } }
            else -> {NORTH}
         }
      }
   },
   WEST('W') {
      override fun findNextDirection(order: Char, repeat: Int): Direction {
         val r = repeat % 4
         return when(order) {
            'L' -> { when(r) { 1 -> SOUTH;2 -> EAST;3 -> NORTH;else -> WEST } }
            'R' -> { when (r) { 1 -> NORTH;2 -> EAST;3 -> SOUTH;else -> WEST } }
            else -> {WEST}
         }
      }
   },
   SOUTH('S') {
      override fun findNextDirection(order: Char, repeat: Int): Direction {
         val r = repeat % 4
         return when(order) {
            'L' -> { when(r) { 1 -> EAST;2 -> NORTH;3 -> WEST;else -> SOUTH } }
            'R' -> { when (r) { 1 -> WEST;2 -> NORTH;3 -> EAST;else -> SOUTH } }
            else -> {SOUTH}
         }
      }
   },
   EAST('E') {
      override fun findNextDirection(order: Char, repeat: Int): Direction {
         val r = repeat % 4
         return when(order) {
            'L' -> { when(r) { 1 -> NORTH;2 -> WEST;3 -> SOUTH;else -> EAST } }
            'R' -> { when (r) { 1 -> SOUTH;2 -> WEST;3 -> NORTH;else -> EAST } }
            else -> {EAST}
         }
      }
   };

   companion object {
      fun currentDirection(c: Char) = values().firstOrNull { it.c == c } ?: error("Invalid")
   }

   abstract fun findNextDirection(order: Char, repeat: Int): Direction;
}

/**
 * 문제 이름(난이도) : 로봇 시뮬레이션 (GOL5)
 * 시간 : 120 ms
 * 메모리 : 13548 KB
 * 링크 : https://www.acmicpc.net/problem/2174
 */
fun main() = with(System.`in`.bufferedReader()) {
   val (column, row) = StringTokenizer(readLine()).run {
      nextToken().toInt() to nextToken().toInt()
   }

   val (n, m) = StringTokenizer(readLine()).run {
      nextToken().toInt() to nextToken().toInt()
   }

   val robotList = Array(n) { RobertDowneyJr() }

   repeat(n) {
      val st = StringTokenizer(readLine())
      val x = st.nextToken().toInt()
      val y = st.nextToken().toInt()
      val dir = st.nextToken()[0]
      robotList[it] = RobertDowneyJr(it + 1, y, x, Direction.currentDirection(dir),)
   }

   var endFlag = false

   outer@for (i in 0 until m) {
      val st = StringTokenizer(readLine())
      val r = st.nextToken().toInt() -1
      val cur = robotList[r]
      val order = st.nextToken()[0]
      val repeat = st.nextToken().toInt()

      val nextDir = cur.dir.findNextDirection(order, repeat)
      cur.dir = nextDir
      if (order == 'F') {
         for (j in 1..repeat) {
            when(cur.dir) {
               Direction.NORTH -> {cur.y += 1}
               Direction.WEST -> {cur.x -= 1}
               Direction.SOUTH -> {cur.y -= 1}
               Direction.EAST -> {cur.x += 1}
            }
            for (next in robotList) {
               if(next == cur) continue
               if (next.y == cur.y && next.x == cur.x) {
                  println("Robot ${cur.num} crashes into robot ${next.num}")
                  endFlag = true
                  break@outer
               }
            }
            if (cur.y !in 1..row || cur.x !in 1..column) {
               println("Robot ${cur.num} crashes into the wall")
               endFlag = true
               break@outer
            }
         }
      }
   }

   if (!endFlag) {
      println("OK")
   }

}