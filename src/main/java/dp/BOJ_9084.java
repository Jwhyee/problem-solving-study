package dp;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 동전(GOL5)
 * 시간 : 84 ms
 * 메모리 : 12128 KB
 * 링크 : https://www.acmicpc.net/problem/9084
 */
public class BOJ_9084 {
    static int T, N, price;
    static int[] coins, dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st;

        // 테스트 케이스
        T = Integer.parseInt(br.readLine());

        // 테스트 케이스를 돌면서 경우의 수 탐색
        while (T-- > 0) {
            // 주어지는 동전의 개수
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            // 동전의 종류를 저장할 배열
            coins = new int[N];
            for (int i = 0; i < N; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }

            // 만들고자 하는 금액
            price = Integer.parseInt(br.readLine());

            // dp 배열 초기화
            dp = new int[price + 1];
            dp[0] = 1;

            // 점화식 : dp[i] += dp[i - coin]
            for (int coin : coins) {
                for (int i = coin; i <= price; i++) {
                    dp[i] += dp[i - coin];
                }
            }

            bw.append(String.valueOf(dp[price])).append("\n");
        }

        bw.flush();
        br.close();
        bw.close();
    }
}
