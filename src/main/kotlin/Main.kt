import kotlin.reflect.KClass

private data class TestDataClass(val t: String)
inline fun <reified T> printGenerics(value: T) {
    println("${T::class} = ${value}")
}

inline fun <T: Any> printGenerics(value: T, type: KClass<T>) {
    println("${type} = ${value}")
}

fun main() {
//    printGenerics(2200000000, Long::class)
//    printGenerics(2100000000, Int::class)
//    printGenerics("type")
//    printGenerics(TestDataClass("t"))
//    println(recursive(10))
    println(recursive1(35000))
    println(recursive2(36000, 0))
}

fun recursive1(i: Int): Int {
    if(i == 0) return 0
    return i + recursive1(i - 1)
}

fun recursive2(i: Int, sum: Int = 0): Int {
    if(i == 0) return sum
    return recursive2(i - 1, sum + i)
}

//tailrec fun recursive(i: Int, sum: Int = 0): Int {
//    if(i == 0) return sum
//    return recursive(i - 1, sum + i)
//}