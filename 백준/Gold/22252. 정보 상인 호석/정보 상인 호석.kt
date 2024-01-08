import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val info = HashMap<String, PriorityQueue<Long>>()
    var sum:Long = 0

    for (i in 0 until n) {
        val st = StringTokenizer(readLine())
        val num = st.nextToken().toInt()
        val name = st.nextToken()
        val m = st.nextToken().toInt()
        var pq = PriorityQueue<Long> {a, b -> b.toInt() - a.toInt()}
        if (info.containsKey(name)) {
            pq = info[name]!!
        } else {
            info[name] = pq
        }

        /*
        * 1 name k c1 c2 .. ck : 이름이 name인 고릴라가 k개의 정보를 얻었으며, 가치는 c1 .. ck
        * 2 name b : 호석이가 name인 고릴라에게 b개의 정보를 구매
        *   - 고릴라가 가진 정보들 중 가잔 비싼 b개를 구매
        *   - 고릴라가 가진 정보가 b개 이하일 경우 모두 구매
        */
        when (num) {
            1 -> {
                for (idx in 0 until m) {
                    pq += st.nextToken().toLong()
                }
            }
            2 -> {
                if (info.containsKey(name)) {
                    val infoValues = info[name]!!
                    if (infoValues.size >= m) {
                        repeat(m) {
                            sum += infoValues.poll()
                        }
                    } else {
                        while (infoValues.isNotEmpty()) {
                            sum += infoValues.poll()
                        }
                    }
                }
            }
        }
    }
    println(sum)
}