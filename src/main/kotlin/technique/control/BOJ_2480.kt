package technique.control

import java.util.StringTokenizer

fun main() {
    val st = StringTokenizer(readln())
    val n1 = st.nextToken().toInt()
    val n2 = st.nextToken().toInt()
    val n3 = st.nextToken().toInt()

    val result = if (n1 == n2 && n2 == n3) {
        10000 + (n1 * 1000)
    } else if ((n1 == n2) || (n1 == n3) || (n2 == n3)) {
        val same = if(n2 == n3) n2 else n1
        1000 + (same * 100)
    } else {
        val temp = Math.max(n1, n2)
        val max = Math.max(temp, n3)
        max * 100
    }

    println(result)
}