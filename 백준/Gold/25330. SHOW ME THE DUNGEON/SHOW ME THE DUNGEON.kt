import java.util.StringTokenizer

private data class VillageInfo(
    val monsterAtk: Int,
    val people: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, k) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val st1 = StringTokenizer(readLine())
    val st2 = StringTokenizer(readLine())

    val villages = Array(n) { VillageInfo(st1.nextToken().toInt(), st2.nextToken().toInt()) }

    villages.sortBy { it.monsterAtk }

    var max = 0L

    fun backtracking(hp: Int, prefix: Int, cur: Int, saved: Long) {
        max = maxOf(max, saved)

        if (hp <= 0) return
        if (cur == n) return

        val (monsterAtk, people) = villages[cur]
        val p = prefix + monsterAtk
        val nextHp = hp - p

        if (nextHp < 0) return

        val nextSaved = saved + people

        backtracking(hp, prefix, cur + 1, saved)
        backtracking(nextHp, p, cur + 1, nextSaved)
    }

    backtracking(k, 0, 0, 0L)

    println(max)

    close()
}