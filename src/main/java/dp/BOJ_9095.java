package dp;

import java.io.*;

public class BOJ_9095 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        int[] dp;

        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine());
            dp = new int[11];
            dp[0] = 0;
            dp[1] = 1;
            dp[2] = 2;
            dp[3] = 4;

            for (int i = 4; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
            }

            bw.append(String.valueOf(dp[n])).append("\n");

        }

        bw.flush();
        bw.close();
        br.close();
    }
}
