import java.util.StringTokenizer
import java.util.TreeMap

private class IceCream(val id: Int, val amount: Int) : Comparable<IceCream> {
    override fun compareTo(other: IceCream) = other.amount - amount
}

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val st = StringTokenizer(readLine())
    val map = TreeMap<Int, ArrayDeque<Int>>(compareByDescending { it })
    for (i in 1..n) {
        val amount = st.nextToken().toInt()
        map.computeIfAbsent(amount) { ArrayDeque() }.addLast(i)
    }

    eatIceCream(map, m)

    close()
}

private fun eatIceCream(iceCreams: TreeMap<Int, ArrayDeque<Int>>, m: Int) {
    val sb = StringBuilder()

    var isTurn = false
    var ateCount = 0

    for ((amount, deque) in iceCreams) {
        while (deque.isNotEmpty() && ateCount < m) {
            val id = if (!isTurn) {
                deque.removeFirst()
            } else {
                deque.removeLast()
            }

            sb.append(id).append("\n")
            ateCount++

            if (amount % 7 == 0) {
                isTurn = !isTurn
            }
        }

        if (ateCount == m) break
    }

    print(sb)
}