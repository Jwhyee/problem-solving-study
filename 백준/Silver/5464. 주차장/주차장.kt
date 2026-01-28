import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val parkBills = IntArray(n) { readLine().toInt() }
    val carWeights = IntArray(m) { readLine().toInt() }

    val queueMaxSize = 2 * m
    val cars = IntArray(queueMaxSize) { readLine().toInt() }

    var parkedSpaceCount = 0
    var totalPrice = 0

    val carQueue: Queue<Int> = LinkedList()
    val pq = PriorityQueue<Int>(compareBy { it })
    val parkingLot = IntArray(m + 1) { -1 }
    repeat(n) {
        pq.add(it)
    }

    for (index in 0 until queueMaxSize) {
        val car = cars[index]

        if (car > 0) {
            // 입차
            if (parkedSpaceCount < n) {
                val parkSpaceIndex = pq.poll()
                parkingLot[car] = parkSpaceIndex
                parkedSpaceCount++
            } else {
                carQueue.add(car)
            }
        } else {
            // 출차
            val cur = -car
            val curParkingSpaceIndex = parkingLot[cur]

            if (carQueue.isNotEmpty()) {
                val nextCar = carQueue.poll()
                parkingLot[nextCar] = curParkingSpaceIndex
            } else {
                pq.add(curParkingSpaceIndex)
                parkedSpaceCount--
            }

            totalPrice += parkBills[curParkingSpaceIndex] * carWeights[cur - 1]
        }
    }

    println(totalPrice)
}