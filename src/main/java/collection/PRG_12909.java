package collection;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 문제 이름(난이도) : 올바른 괄호(LV2)
 * 시간 : 16.99 ms
 * 메모리 : 53 MB
 * 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12909
 */
public class PRG_12909 {

    public boolean solution(String s) {
        // 검증할 문자열을 문자 배열로 변환
        char[] arr = s.toCharArray();

        // 여는 괄호를 저장할 스택 생성
        Stack<Character> stack = new Stack<>();

        // 문자 배열을 돌면서 짝이 맞는지 검증
        for (char c : arr) {
            if (c == '(') {
                stack.push(c);
            }
            // 스택이 비어있는 상태에서 pop을 할 경우 에러가 발생
            // 위 상황을 대비하기 위해 유효성 검증 진행
            else if (stack.isEmpty()) {
                return false;
            } else {
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    @Test
    void solutionTest1() {
        String s = "()()";
        boolean answer = solution(s);
        System.out.println(answer);
        assertTrue(answer);
    }

    @Test
    void solutionTest2() {
        String s = "(())()";
        boolean answer = solution(s);
        System.out.println(answer);
        assertTrue(answer);
    }

    @Test
    void solutionTest3() {
        String s = ")()(";
        boolean answer = solution(s);
        System.out.println(answer);
        assertTrue(!answer);
    }

    @Test
    void solutionTest4() {
        String s = "(()(";
        boolean answer = solution(s);
        System.out.println(answer);
        assertTrue(!answer);
    }
}
