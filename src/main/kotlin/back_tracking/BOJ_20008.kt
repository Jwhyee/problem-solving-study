package back_tracking

import java.util.StringTokenizer
import kotlin.math.min

private data class Skill(val cooldown: Int, val damage: Int)

/**
 * 문제 이름(난이도) : 몬스터를 처치하자!(GOL5)
 * 시간 : 11 ms
 * 메모리 : 11 KB
 * 링크 : https://www.acmicpc.net/problem/20008
 */
fun main() = with(System.`in`.bufferedReader()){
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val hp = st.nextToken().toInt()

    val skills = mutableListOf<Skill>()

    for (i in 0 until n) {
        st = StringTokenizer(readLine())
        skills += Skill(st.nextToken().toInt(), st.nextToken().toInt())
    }

    var result = Int.MAX_VALUE
    for (i in 0 until n) {
        var monsterHp = hp
        var currentTime = 1
        val skillTimes = IntArray(n)

        monsterHp -= skills[i].damage
        skillTimes[i] = skills[i].cooldown

        while (monsterHp > 0) {
            loop@for (j in 0 until n) {
                if (skillTimes[j] > 0) {
                    skillTimes[j]--
                }

                if (skillTimes[j] == 0) {
                    monsterHp -= skills[j].damage
                    skillTimes[j] = skills[j].cooldown
                    break@loop
                }
            }
            if(monsterHp > 0) currentTime++
        }
        result = min(result, currentTime)
    }

    println(result)
}