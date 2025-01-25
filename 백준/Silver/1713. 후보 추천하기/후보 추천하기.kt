import java.util.*

/**
 * 문제: 후보 추천하기
 * 링크 : https://www.acmicpc.net/problem/1713
 * */
private data class Student(val id: Int, var recommend: Int, var time: Int)

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()
    val m = readLine().toInt()
    val st = StringTokenizer(readLine())

    val students = Array(101){ Student(it, 0, 0) }
    val recommend = arrayListOf<Student>()

    var time = 1
    while (st.hasMoreTokens()) {
        val studentId = st.nextToken().toInt()
        val student = students[studentId]

        student.recommend++

        if(recommend.any { it.id == studentId }) {
            continue
        }

        if (recommend.size == n) {
            val minRecommend = recommend.minBy { it.recommend }.recommend
            val removeStudent = recommend.filter { it.recommend == minRecommend }.minBy { it.time }
            removeStudent.recommend = 0
            recommend.remove(removeStudent)
        }

        recommend.add(student.also {
            it.time = time
        })

        time++
    }

    println(recommend.sortedBy { it.id }.joinToString(" ") { it.id.toString() })
}