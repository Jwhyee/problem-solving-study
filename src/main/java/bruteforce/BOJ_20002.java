package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 사과나무 (GOL5)
 * 시간 : 328 ms
 * 메모리 : 21156 KB
 * 링크 : https://www.acmicpc.net/problem/20002
 */
public class BOJ_20002 {
    static int N;
    static int[][] map, result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N + 1][N + 1];
        result = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                // 위쪽 칸 누적합 + 왼쪽 칸 누적합 - 좌측 상단 대각선 누적합 + 현재 값
                result[i][j] = result[i - 1][j] + result[i][j - 1] - result[i - 1][j - 1] + map[i][j];
            }
        }

        long max = -1001;

        // K는 1보다 크거나 같고 N보다 작거나 같은 정수
        for (int k = 1; k <= N; k++) {
            long temp = findMax(k);
            max = Math.max(max, temp);
        }

        // 메모리 해제
        map = result = null;
        br.close();

        System.out.println(max);
    }

    private static long findMax(int k) {
        long max = -1001;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // 구역 밖으로 넘어갈 경우 continue
                if (i + k - 1 > N || j + k - 1 > N) continue;
                // 영역에 대한 누적합 구하기
                int temp = result[i + k - 1][j + k - 1] - result[i - 1][j + k - 1] - result[i + k - 1][j - 1] + result[i - 1][j - 1];
                max = Math.max(temp, max);
            }
        }
        return max;
    }
}
