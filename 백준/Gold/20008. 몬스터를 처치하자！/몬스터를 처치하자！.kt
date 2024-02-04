import kotlin.math.min

fun timeMemo(time: Int, damage: Int, damageMemoList: MutableList<Int>): List<Int> {
    var idx = 0
    val limit = damageMemoList.size
    while (idx < limit) {
        if (damageMemoList[idx] == 0) {
            damageMemoList[idx] = damage
            idx += time
        } else {
            idx++
        }
    }
    return damageMemoList
}

fun timeCnt(damageMemoList: List<Int>, hp: Int): Int {
    var cnt = 0
    var remainingHP = hp
    for (damage in damageMemoList) {
        if (remainingHP <= 0) {
            break
        }
        remainingHP -= damage
        cnt++
    }
    return cnt
}

fun main() {
    val (n, hp) = readLine()!!.split(" ").map { it.toInt() }

    var totalDamage = 0
    var totalTime = 0
    val lstTD = mutableListOf<Pair<Int, Int>>()

    repeat(n) {
        val (time, damage) = readLine()!!.split(" ").map { it.toInt() }
        totalTime += time
        totalDamage += damage
        lstTD.add(time to damage)
    }

    val limit = totalTime * ((hp / totalDamage) + 1)
    val permutations = lstTD.permutations()
    var result = Int.MAX_VALUE

    for (permutation in permutations) {
        val damageMemoList = MutableList(limit) { 0 }
        for ((t, d) in permutation) {
            damageMemoList.addAll(timeMemo(t, d, damageMemoList))
        }
        result = min(result, timeCnt(damageMemoList, hp))
    }

    println(result)
}

fun <T> List<T>.permute(): List<List<T>> =
    if (size == 1) listOf(this)
    else flatMap { e -> (minus(e).permute()).map { listOf(e) + it } }

fun <T> List<T>.permutations(): List<List<T>> = permute().distinct()
