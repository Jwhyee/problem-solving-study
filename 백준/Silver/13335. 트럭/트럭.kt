import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private data class Truck(val time: Int, val weight: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, w, l) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val st = StringTokenizer(readLine())
    val trucks = IntArray(n) { st.nextToken().toInt() }

    val bridge: Queue<Int> = LinkedList()

    repeat(w) {
        bridge.add(0)
    }

    var index = 0
    var time = 0
    var totalWeight = 0

    while (index < n) {
        time++

        totalWeight -= bridge.poll()

        val next = trucks[index]

        if (totalWeight + next <= l) {
            bridge.add(next)
            totalWeight += next
            index++
        } else {
            bridge.add(0)
        }
    }

    println(time + w)

    close()
}