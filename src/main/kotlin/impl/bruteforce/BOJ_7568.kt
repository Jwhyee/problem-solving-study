package impl.bruteforce

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val n = readLine().toInt()

    val people = Array(n) { IntArray(2) { 0 } }

    repeat(n) { index ->
        val (weight, height) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        people[index][0] = weight
        people[index][1] = height
    }

    for (i in 0 until n) {
        var rank = 1

        val base = people[i]

        for (j in 0 until n) {
            if(i == j) continue
            val target = people[j]

            if (base[0] < target[0] && base[1] < target[1]) rank++
        }

        sb.append("$rank ")
    }

    println(sb)

    close()
}