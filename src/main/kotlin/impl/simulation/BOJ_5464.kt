package impl.simulation

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val parkBills = IntArray(n) { readLine().toInt() }
    val carWeights = IntArray(m) { readLine().toInt() }

    val queueMaxSize = 2 * m
    val events = IntArray(queueMaxSize) { readLine().toInt() }

    var parkedSpaceCount = 0
    var totalPrice = 0

    val carQueue: Queue<Int> = LinkedList()
    val pq = PriorityQueue<Int>()

    val carLocation = IntArray(m + 1) { 0 }

    repeat(n) {
    }

    for (event in events) {
        val car = event

        if (car > 0) {
            if (parkedSpaceCount < n) {
                val spaceIdx = pq.poll()
                carLocation[car] = spaceIdx
                parkedSpaceCount++
            } else {
                carQueue.add(car)
            }
        } else {
            val leavingCar = -car
            val usedSpaceIdx = carLocation[leavingCar]

            totalPrice += parkBills[usedSpaceIdx] * carWeights[leavingCar - 1]

            if (carQueue.isNotEmpty()) {
                val nextCar = carQueue.poll()
                carLocation[nextCar] = usedSpaceIdx
            } else {
                pq.add(usedSpaceIdx)
                parkedSpaceCount--
            }
        }
    }

    println(totalPrice)
}