package back_tracking

import java.util.*
import kotlin.math.min

private data class Skill(val coolTime: Int, val damage: Int)
private lateinit var skillList: MutableList<Skill>
private lateinit var skillInfo: MutableList<Skill>
private lateinit var visited: BooleanArray
private var limit = 0
private var result = 0

/**
 * 문제 이름(난이도) : 몬스터를 처치하자!(GOL5)
 * 시간 : 104 ms
 * 메모리 : 14048 KB
 * 링크 : https://www.acmicpc.net/problem/20008
 */
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
    limit = totalTime * ((hp / totalDamage) + 1)

    // 사용한 스킬에 대한 방문 배열
    visited = BooleanArray(n) { false }
    // 사용한 스킬 정보
    skillInfo = mutableListOf()
    result = Int.MAX_VALUE

    backTracking(hp)

    println(result)
    close()
}

private fun backTracking(hp: Int) {
    // 사용한 스킬의 수와 전체 스킬의 수가 같을 경우
    if (skillInfo.size == skillList.size) {
        val damageList = MutableList(limit) { 0 }

        // 스킬을 사용할 수 있는 시간에 스킬 채워 넣기
        for ((time, damage) in skillInfo) {
            var t = 0
            val size = damageList.size
            while (t < size) {
                // 데미지를 입혀야할 시간에 입힌 데미지가 0일 경우
                if (damageList[t] == 0) {
                    // 해당 시간에 데미지를 매핑 후 다음 시간(t)을 쿨타임 이후로 지정
                    damageList[t] = damage
                    t += time
                } else { t++ }
            }
        }
        var t = 0
        var remainingHP = hp
        // 몬스터의 체력이 0이하가 될 때까지 데미지 입히기
        for (damage in damageList) {
            if (remainingHP <= 0) {
                break
            }
            remainingHP -= damage
            t++
        }
        result = min(result, t)
        return
    }

    // 전체 스킬 리스트를 순회하며 백트래킹
    for (i in skillList.indices) {
        // 현재 스킬을 사용하지 않았을 경우
        if (!visited[i]) {
            visited[i] = true
            // 스킬 사용 정보에 추가
            skillInfo.add(skillList[i])
            backTracking(hp)
            visited[i] = false
            // 스킬 사용 정보에서 제거
            skillInfo.removeAt(skillInfo.size - 1)
        }
    }
}