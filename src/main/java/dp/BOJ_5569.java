package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 출근 경로(GOL4)
 * 시간 : 76 ms
 * 메모리 : 12048 KB
 * 링크 : https://www.acmicpc.net/problem/5569
 */
public class BOJ_5569 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        // 3인덱스 : 방향, 0은 오른 1은 아래
        // 4인덱스 : 방향 변경 여부, 0은 꺾지 않은경우 1은 꺾은 경우
        int[][][][] dp = new int[w + 1][h + 1][2][2];

        // 최소 2블록 이동한 후 다시 방향 전환 가능

        // 시작점과 같은 라인의 교차로까지 올 수 있는 경우의 수 추가
        for(int i = 1; i <= w; i++) {
            dp[i][1][0][0] = 1;
        }

        for(int i = 1; i <= h; i++) {
            dp[1][i][1][0] = 1;
        }

        // 출근할 수 있는 경로의 개수를 100000로 나눈 나머지
        int mod = 100000;
        for(int i = 2; i <= w; i++) {
            for(int j = 2; j <= h; j++) {
                dp[i][j][1][0] = (dp[i][j - 1][1][1] + dp[i][j - 1][1][0]) % mod;
                dp[i][j][1][1] = dp[i][j - 1][0][0] % mod;
                dp[i][j][0][0] = (dp[i - 1][j][0][0] + dp[i - 1][j][0][1]) % mod;
                dp[i][j][0][1] = dp[i - 1][j][1][0];
            }
        }

        // 최종 결과
        int result = (dp[w][h][0][0] + dp[w][h][0][1] + dp[w][h][1][0] + dp[w][h][1][1]) % mod;
        System.out.println(result);

    }
}
