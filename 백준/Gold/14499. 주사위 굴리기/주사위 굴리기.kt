import java.util.StringTokenizer

private data class Dice(
   var top: Int = 0,
   var back: Int = 0,
   var right: Int = 0,
   var left: Int = 0,
   var front: Int = 0,
   var bottom: Int = 0,
)

private var dice = Dice()
private var y = 0
private var x = 0

/**
 * 문제 이름(난이도) : 주사위 굴리기(GOL4)
 * 시간 : 160 ms
 * 메모리 : 21312 KB
 * 링크 : https://www.acmicpc.net/problem/14499
 */
fun main() = with(System.`in`.bufferedReader()) {
   val st = StringTokenizer(readLine())
   val n = st.nextToken().toInt()
   val m = st.nextToken().toInt()
   x = st.nextToken().toInt()
   y = st.nextToken().toInt()
   val k = st.nextToken().toInt()
   val bw = System.out.bufferedWriter()

   val map = Array(n) { IntArray(m) }

   repeat(n) {  idx ->
      map[idx] = readLine().split(" ").map { it.toInt() }.toIntArray()
   }

   val temp = x
   x = y; y = temp

   val order = readLine().split(" ").map { it.toInt() }

   for (dir in order) {
      val ny = y + dy[dir - 1]
      val nx = x + dx[dir - 1]
      if(ny !in map.indices || nx !in map[0].indices) continue

      y = ny; x = nx;

      moveDice(dir)

      if (map[ny][nx] == 0) {
         map[ny][nx] = dice.bottom
      } else {
         dice.bottom = map[ny][nx]
         map[ny][nx] = 0
      }

      bw.append("${dice.top}")
      bw.newLine()

   }

   bw.flush()
   bw.close()
   close()

}

private fun printMap(map: Array<IntArray>) {
   for (ints in map) {
      println(ints.joinToString("\t"))
   }
}

private val dy = intArrayOf(0, 0, -1, 1)
private val dx = intArrayOf(1, -1, 0, 0)

private fun moveDice(dir: Int) {
   when (dir) {
      1 -> {
         dice = Dice(dice.left, dice.back, dice.top, dice.bottom, dice.front, dice.right)
      }
      2 -> {
         dice = Dice(dice.right, dice.back, dice.bottom, dice.top, dice.front, dice.left)
      }
      3 -> {
         dice = Dice(dice.front, dice.top, dice.right, dice.left, dice.bottom, dice.back)
      }
      4 -> {
         dice = Dice(dice.back, dice.bottom, dice.right, dice.left, dice.top, dice.front)
      }
   }
}