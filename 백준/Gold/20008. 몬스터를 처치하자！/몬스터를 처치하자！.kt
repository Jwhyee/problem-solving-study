import java.util.StringTokenizer
import kotlin.math.min

private data class Skill(val coolTime: Int, val damage: Int)
private lateinit var skillList: MutableList<Skill>
private lateinit var skillInfo: MutableList<Skill>
private lateinit var visited: BooleanArray
private var limit = 0
private var result = 0

fun main() = with(System.`in`.bufferedReader()){
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val hp = st.nextToken().toInt()

    var totalDamage = 0
    var totalTime = 0
    skillList = mutableListOf()

    repeat(n) {
        st = StringTokenizer(readLine())
        val time = st.nextToken().toInt()
        val damage = st.nextToken().toInt()
        totalTime += time
        totalDamage += damage
        skillList.add(Skill(time, damage))
    }

    limit = totalTime * ((hp / totalDamage) + 1)

    visited = BooleanArray(n) { false }

    skillInfo = mutableListOf<Skill>()
    result = Int.MAX_VALUE

    backTracking(hp)

    println(result)
    close()
}

private fun backTracking(
    hp: Int,
) {

    if (skillInfo.size == skillList.size) {
        val damageList = MutableList(limit) { 0 }

        for ((time, damage) in skillInfo) {
            var idx = 0
            val size = damageList.size
            while (idx < size) {
                if (damageList[idx] != 0) {
                    idx++
                    continue
                }
                damageList[idx] = damage
                idx += time
            }
        }
        var cnt = 0
        var remainingHP = hp
        for (damage in damageList) {
            if (remainingHP <= 0) {
                break
            }
            remainingHP -= damage
            cnt++
        }
        result = min(result, cnt)
        return
    }

    for (i in skillList.indices) {
        if (!visited[i]) {
            visited[i] = true
            skillInfo.add(skillList[i])
            backTracking(hp)
            visited[i] = false
            skillInfo.removeAt(skillInfo.size - 1)
        }
    }
}