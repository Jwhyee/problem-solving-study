package two_pointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 블로그(실버3)
 * 시간 : 288 ms
 * 메모리 : 37544 KB
 * 링크 : https://www.acmicpc.net/problem/21921
 */
public class BOJ_21921 {
    static int N, X;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 블로그 시작 후 지난 일 수
        N = Integer.parseInt(st.nextToken());
        // X일 동안 방문자 수 확인
        X = Integer.parseInt(st.nextToken());

        // 방문자 수 배열
        int[] visitors = new int[N];

        // 방문자 입력
        st = new StringTokenizer(br.readLine());
        int tempSum = 0;
        for (int i = 0; i < N; i++) {
            visitors[i] = Integer.parseInt(st.nextToken());
            tempSum += visitors[i];
        }

        if (tempSum == 0) {
            System.out.println("SAD");
        } else {
            // X일까지 방문자 수 누적합
            // X : 2 -> 1 + 4
            int sum = 0;
            for (int i = 0; i < X; i++) {
                sum += visitors[i];
            }

            // 방문할 수 있는 상태 최대값 저장
            int max = sum;
            int cnt = 1;

            // X일부터 N일까지 진행
            // 1 4 2 5 1
            // 1 4, 4 2, 2 5, 5 1
            for (int i = X; i < N; i++) {
                sum += visitors[i] - visitors[i - X];
                if (max == sum) {
                    cnt++;
                } else if (max < sum) {
                    max = sum;
                    cnt = 1;
                }
            }

            System.out.println(max);
            System.out.println(cnt);
        }
    }
}
