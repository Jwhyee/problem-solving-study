import java.util.StringTokenizer

private data class Person(
    val id: Int,
    val weight: Int,
    val height: Int
) : Comparable<Person> {
    override fun compareTo(other: Person) = (other.weight - weight) + (other.height - height)
}

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val people = Array(n) { Person(0, 0, 0) }

    repeat(n) { index ->
        val (weight, height) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        people[index] = Person(index + 1, weight, height)
    }

    people.sort()

    val ranking = IntArray(n) { Int.MAX_VALUE }

    for (i in 0 until n) {
        var rank = 1
        
        val base = people[i]
        val baseIndex = base.id - 1

        for (j in 0 until n) {
            if(i == j) continue
            val target = people[j]

            if (base.weight < target.weight && base.height < target.height) rank++
        }

        ranking[baseIndex] = rank
    }

    println(ranking.joinToString(" "))
}