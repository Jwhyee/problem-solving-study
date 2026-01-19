package greedy

import java.util.*

private data class Lecture(
    val pay: Int,
    val day: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val line = readLine() ?: return
    val n = line.toInt()
    val lectures = mutableListOf<Lecture>()

    repeat(n) {
        val st = StringTokenizer(readLine())
        lectures.add(Lecture(st.nextToken().toInt(), st.nextToken().toInt()))
    }

    lectures.sortBy { it.day }

    val pq = PriorityQueue<Int>()

    for (lecture in lectures) {
        pq.add(lecture.pay)

        if (pq.size > lecture.day) {
            pq.poll()
        }
    }

    println(pq.sum())
}