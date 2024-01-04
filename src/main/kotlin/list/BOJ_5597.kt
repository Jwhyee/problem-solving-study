package list

fun main() {
    val list = arrayListOf<Int>()

    repeat(28) {
        list.add(readln().toInt())
    }

    for (i in 1..30) {
        if(i !in list) println(i)
    }

}