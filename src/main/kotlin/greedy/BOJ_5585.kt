package greedy

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()
    val arr = intArrayOf(1000, 500, 100, 50, 10, 5, 1)
    var money = 1000 - n
    var result = 0
    for (m in arr) {
        if(money >= m) {
            val r = money / m
            result += r
            money -= (m * r)
        }
    }
    println(result)
}