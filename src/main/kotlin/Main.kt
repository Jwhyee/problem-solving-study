import kotlin.reflect.KClass

private data class TestDataClass(val t: String)
inline fun <reified T> printGenerics(value: T) {
    println("${T::class} = ${value}")
}

inline fun <T: Any> printGenerics(value: T, type: KClass<T>) {
    println("${type} = ${value}")
}

fun main() {
    printGenerics(2200000000, Long::class)
    printGenerics(2100000000, Int::class)
//    printGenerics("type")
//    printGenerics(TestDataClass("t"))
}
