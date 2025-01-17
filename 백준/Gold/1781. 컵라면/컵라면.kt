import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val problems = mutableListOf<Pair<Int, Int>>()

    repeat(n) {
        val (deadline, ramen) = StringTokenizer(readLine()).run {
            Pair(nextToken().toInt(), nextToken().toInt())
        }
        problems.add(deadline to ramen)
    }

    problems.sortBy { it.first }

    val pq = PriorityQueue<Int>()
    for ((deadline, ramen) in problems) {
        pq.add(ramen)
        if (pq.size > deadline) {
            pq.poll()
        }
    }

    println(pq.sum())
}