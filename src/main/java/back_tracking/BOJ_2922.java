package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 문제 이름(난이도) : 즐거운 단어(GOL5)
 * 시간 : . ms
 * 메모리 : . KB
 * 링크 : https://www.acmicpc.net/problem/2922
 */
public class BOJ_2922 {
    static String WORD;
    static long cnt;
    static final int N = 65;
    static char[] wordCharArray;
    static int[] emptyIdxArr;

    public static void main(String[] args) throws IOException {
        // 보기 싫은 알파벳 _ 처리
        // 즐거운 단어
        // 1. 모음(A,E,I,O,U)이 연속해서 3번 && 자음(모음을 제외한 나머지 알파벳)이 연속해서 3번 나오지 않아야 한다.
        // 2. L을 반드시 포함
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        WORD = br.readLine();

        wordCharArray = WORD.toCharArray();
        int length = wordCharArray.length;

        int emptyCnt = 0;
        for (char c : wordCharArray) {
            if (c == '_') {
                emptyCnt++;
            }
        }

        emptyIdxArr = new int[emptyCnt];

        for (int i = 0, j = 0; i < length; i++) {
            char c = wordCharArray[i];
            if (c == '_') {
                emptyIdxArr[j++] = i;
            }
        }

        backTracking(0);

        System.out.println(cnt);
    }

    private static void backTracking(int depth) {
        if (depth == emptyIdxArr.length) {
            if (isHappy()) {
                cnt++;
            }
            return;
        }

        for (int i = 0; i < 26; i++) {
            char c = (char) (i + N);
            wordCharArray[emptyIdxArr[depth]] = c;
            backTracking(depth + 1);
        }
    }

    private static boolean isHappy() {
        int a = 0, b = 0;
        boolean isContain = false;
        for (char c : wordCharArray) {
            if (c == 'L') {
                isContain = true;
            }

            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                a++;
                b = 0;
            } else {
                b++;
                a = 0;
            }

            if (a == 3 || b == 3) {
                return false;
            }
        }

        return isContain;
    }
}
