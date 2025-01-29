import java.util.Stack
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 역폴란드 표기법 계산기 작성
 * RPN은 연산자가 피연산자 뒤에 오는 수학 표기법으로 연산자를 두 피연산자 중간에 배치하고 우선 순위 표기를 위해 괄호를 사용하는 대신 피연산 뒤에 연산자를 배치하는 수학적 표기법이다.
*/
class RpnCalc {
    companion object {
        private fun String.isNumber() = this.toDoubleOrNull() != null

        /**
         * 알고리즘 정리
         * - 계산은 숫자 목록으로 시작하는 연산식과 빈 스택으로 시작한다.
         * - 연산자의 맨 왼쪽에서 원소를 찾는 것으로 계산을 시작한다.
         * - 원소가 숫자인 경우 이를 스택에 넣는다.
         * - 원소가 연산이면 스택에서 피연산자 두 개를 꺼내서 적용한 다음 결과를 다시 스택에 넣는다.
         * */
        fun calc(expression: String) = expression.split(" ")
            .fold(FunStack<Double>()) { funStack, token ->
                when {
                    token.isNumber() -> funStack.push(token.toDouble())
                    else -> {
                        val (right, stack1) = funStack.pop()
                        val (left, stack2) = stack1.pop()
                        val result = when (token) {
                            "+" -> left + right
                            "-" -> left - right
                            "*" -> left * right
                            "/" -> left / right
                            else -> throw IllegalArgumentException("존재하지 않는 연산자 : $token")
                        }
                        stack2.push(result)
                    }
                }
            }.pop().first
    }
}
class RpnCalcTest {
    @Test
    fun `a simple sum`() {
        assertEquals(RpnCalc.calc("4 5 +"), 9.0)
    }

    @Test
    fun `a double operation`() {
        assertEquals(RpnCalc.calc("3 2 1 - +"), 4.0)

    }

    @Test
    fun `a division`() {
        assertEquals(RpnCalc.calc("6 2 /"), 3.0)
    }

    @Test
    fun `a more complicated operation`() {
        assertEquals(RpnCalc.calc("6 2 1 + /"), 2.0)
        assertEquals(RpnCalc.calc("5 6 2 1 + / *"), 10.0)

    }

    @Test
    fun `a bit of everything`() {
        assertEquals(RpnCalc.calc("2 5 * 4 + 3 2 * 1 + /"), 2.0)
    }
}

class FunStack<T>(
    private val array: List<T> = emptyList()
) {
    fun push(value: T): FunStack<T> = FunStack(array + value)

    fun pop(): Pair<T, FunStack<T>> {
        return array.last() to FunStack(array.dropLast(1))
    }

    fun size() = array.size

    override fun toString() = "[${array.joinToString(", ")}]"
}

internal class FunStackTest {
    @Test
    fun `push into the stack`() {
        val stack1 = FunStack<Char>()
        val stack2 = stack1.push('A')

        assertEquals(stack1.size(), 0)
        assertEquals(stack2.size(), 1)
    }

    @Test
    fun `push and pop`() {
        val (q, stack) = FunStack<Char>().push('Q').pop()

        assertEquals(stack.size(), 0)
        assertEquals(q, 'Q')
    }

    @Test
    fun `push push pop`() {
        val (b, stack) = FunStack<Char>()
            .push('A')
            .push('B')
            .pop()

        assertEquals(stack.size(), 1)
        assertEquals(b, 'B')
    }
}