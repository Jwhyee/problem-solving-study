package search;

import java.io.*;
import java.util.Arrays;
import java.util.Stack;

/**
 * 문제 이름(난이도) : 팰린드롬 만들기(실버3)
 * 시간 : 80 ms
 * 메모리 : 11776 KB
 * 링크 : https://www.acmicpc.net/problem/1213
 */
public class BOJ_1213 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        String[] testCaseArr = br.readLine().split("");
        int caseLength = testCaseArr.length;

        // 배열 요소 정렬
        Arrays.sort(testCaseArr);

        // 주어진 문장의 반씩 스택에 넣기 위해 사용
        Stack<String> stack = new Stack<>();

        // 주어진 Case가 짝수일 경우
        a : if (caseLength % 2 == 0) {
            for (int i = 0; i < caseLength; i += 2) {
                // 현재 글자와 다음 글자가 같을 경우
                if (testCaseArr[i].equals(testCaseArr[i + 1])) {
                    // 현재 문자를 빌더에 추가
                    sb.append(testCaseArr[i]);
                    // 거꾸로 뽑을 수 있게 스택에 추가
                    stack.push(testCaseArr[i]);
                }
                // 첫 글자와 다음 글자가 다를 경우
                else {
                    bw.append("I'm Sorry Hansoo");
                    break a;
                }
            }
            // 앞서 만든 문자열에 스택에 있는 문자열을 이어 붙임
            while (!stack.empty()) {
                sb.append(stack.pop());
            }
            bw.append(sb.toString());
        }
        // 주어진 Case가 홀수일 경우
        else {
            // 주어진 Case에서 1개 뿐인 문자를 저장할 temp 변수
            String temp = "tmp";

            for (int i = 0; i < caseLength - 1; i += 2) {
                // 현재 글자와 다음 글자가 같을 경우
                if (testCaseArr[i].equals(testCaseArr[i + 1])) {
                    // 현재 문자를 빌더에 추가
                    sb.append(testCaseArr[i]);
                    // 거꾸로 뽑을 수 있게 스택에 추가
                    stack.push(testCaseArr[i]);
                }
                // 첫 루프에서 위 조건에 맞지 않을 경우
                else if (temp.equals("tmp")) {
                    // 임시값을 현재 문자로 변경
                    temp = testCaseArr[i];
                    // 문자 하나만 사용했기 때문에 i값 감소
                    i--;
                }
                // 위 조건에 모두 해당되지 않을 경우
                else {
                    bw.append("I'm Sorry Hansoo");
                    break a;
                }
            }
            // 모든 문자를 확인할 때까지 temp의 값이 변경되지 않았을 경우
            // temp에는 주어진 문자열의 가장 끝 문자를 넣어줌
            if (temp.equals("tmp")) {
                temp = testCaseArr[caseLength - 1];
            }
            // 최종적인 문자열 절반 완성
            sb.append(temp);

            // 스택을 돌며 나머지 문자를 붙여줌
            while (!stack.empty()) {
                sb.append(stack.pop());
            }
            bw.append(sb.toString());
        }
        bw.flush();
        bw.close();
        br.close();
    }
}