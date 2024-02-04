package back_tracking

import java.util.StringTokenizer
import kotlin.math.min

private data class Skill(val coolTime: Int, val damage: Int)
private lateinit var skillList: MutableList<Skill>

fun main() = with(System.`in`.bufferedReader()){
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val hp = st.nextToken().toInt()

    var totalDamage = 0
    var totalTime = 0
    skillList = mutableListOf()

    // 각 스킬 정보 입력 받기
    repeat(n) {
        st = StringTokenizer(readLine())
        val time = st.nextToken().toInt()
        val damage = st.nextToken().toInt()
        totalTime += time
        totalDamage += damage
        skillList.add(Skill(time, damage))
    }

    // 스킬을 사용할 수 있는 최대 횟수의 최댓값
    val limit = totalTime * ((hp / totalDamage) + 1)

    // 사용할 스킬 순서에 대한 모든 순열
    val used = BooleanArray(n) { false }
    val currentPermutation = mutableListOf<Skill>()
    val result = IntArray(1) { Int.MAX_VALUE }

    backTracking(used, currentPermutation, limit, hp, result)

    println(result[0])
    close()
}

private fun backTracking(
    used: BooleanArray,
    currentPermutation: MutableList<Skill>,
    limit: Int,
    hp: Int,
    result: IntArray
) {
    if (currentPermutation.size == skillList.size) {
        val damageList = MutableList(limit) { 0 }
        for ((t, d) in currentPermutation) {
            damageList.addAll(timeMemo(t, d, damageList))
        }
        result[0] = min(result[0], timeCnt(damageList, hp))
        return
    }

    for (i in skillList.indices) {
        if (!used[i]) {
            used[i] = true
            currentPermutation.add(skillList[i])
            backTracking(used, currentPermutation, limit, hp, result)
            used[i] = false
            currentPermutation.removeAt(currentPermutation.size - 1)
        }
    }
}

private fun timeMemo(time: Int, damage: Int, damageList: MutableList<Int>): List<Int> {
    var idx = 0
    val limit = damageList.size
    while (idx < limit) {
        if (damageList[idx] != 0) {
            idx++
            continue
        }
        damageList[idx] = damage
        idx += time
    }
    return damageList
}

private fun timeCnt(damageMemoList: List<Int>, hp: Int): Int {
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
