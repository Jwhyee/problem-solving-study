package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 숫자 야구(실버3)
 * 시간 : 220 ms
 * 메모리 : 24548 KB
 * 링크 : https://www.acmicpc.net/problem/2503
 */
public class BOJ_2503 {
    /*
    123 1 1
    356 1 0
    327 2 0
    489 0 1
    * */
    static int N;
    static List<Baseball> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        int cnt = 0;
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int number = Integer.parseInt(st.nextToken());
            int strike = Integer.parseInt(st.nextToken());
            int ball = Integer.parseInt(st.nextToken());
            list.add(new Baseball(number, strike, ball));
        }

        for (int i = 123; i <= 987; i++) {
            if (!checkNumberValid(i)) {
                if (checkCase(i) == N) {
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }

    public static boolean checkNumberValid(int num) {
        String s = String.valueOf(num);
        if (s.contains("0")) {
            return true;
        }

        String[] arr = s.split("");
        if (arr[0].equals(arr[1])) {
            return true;
        } else if (arr[0].equals(arr[2])) {
            return true;
        } else if (arr[1].equals(arr[2])) {
            return true;
        }
        return false;
    }

    public static int checkCase(int compareNumber) {

        // 비교할 수(compareNumber)가 민혁이가 말한 숫자들의 경우와 몇 개가 동일한지 확인하는 변수
        int passCaseCnt = 0;

        // 총 N번의 테스트를 확인하기 위한 반복문
        for (int i = 0; i < N; i++) {
            // compareNumber와 민혁이가 말한 수를 비교했을 때 나오는 스트라이크, 볼의 수
            int strikeCnt = 0, ballCnt = 0;
            Baseball b = list.get(i);

            // 비교할 수
            String cNumber = String.valueOf(compareNumber);

            // 민혁이가 말한 숫자
            String bNumber = String.valueOf(b.number);

            // 각 자리의 스트라이크 수 확인
            for (int j = 0; j < 3; j++) {
                if (cNumber.charAt(j) == bNumber.charAt(j)) {
                    strikeCnt++;
                }
            }

            // 100, 10, 1의 자리끼리 비교하며 볼 수 확인
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    // j와 k가 같을 경우 strike 판정이기 때문에 제외
                    if (j == k) {
                        continue;
                    }
                    // 포함될 경우 볼 카운트 증가
                    if (cNumber.charAt(j) == bNumber.charAt(k)) {
                        ballCnt++;
                    }
                }
            }

            // compareNumber가 민혁이가 말한 숫자에 대한 strike, ball이 같다면 케이스 증가
            if (b.strike == strikeCnt && b.ball == ballCnt) {
                passCaseCnt++;
            }
        }
        return passCaseCnt;
    }

    static class Baseball {
        int number;
        int strike;
        int ball;

        public Baseball(int number, int strike, int ball) {
            this.number = number;
            this.strike = strike;
            this.ball = ball;
        }
    }
}
