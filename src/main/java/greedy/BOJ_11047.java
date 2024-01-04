package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 동전 (SIL4)
 * 시간 : 76 ms
 * 메모리 : 11508 KB
 * 링크 : https://www.acmicpc.net/problem/11047
 */
public class BOJ_11047 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(br.readLine());
        }

        int result = 0;
        for (int i = n - 1; i >= 0; i--) {
            int v = values[i];
            if (v <= k) {
                result += (k / v);
                k %= v;
            }
        }

        System.out.println(result);

    }
}
