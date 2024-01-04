package impl;

import java.io.*;

/**
 * 문제 이름(난이도) : 사다리타기 (GOL5)
 * 시간 : 88 ms
 * 메모리 : 12584 KB
 * 링크 : https://www.acmicpc.net/problem/2469
 */
public class BOJ_2469 {
    static int K, N, line = -1;
    static char[] player, result;
    static char[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 플레이어 수
        K = Integer.parseInt(br.readLine());
        // 사다리 라인
        N = Integer.parseInt(br.readLine());

        player = new char[K];

        // 플레이어
        for (int i = 0; i < K; i++) {
            player[i] = (char) (65 + i);
        }

        result = br.readLine().toCharArray();

        // 지도 초기화
        map = new char[N][K - 1];
        for (int i = 0; i < N; i++) {
            char[] charArray = br.readLine().toCharArray();

            if (charArray[0] == '?') {
                line = i;
                continue;
            }

            map[i] = charArray;
        }

        // Top - Down 탐색
        for (int i = 0; i < line; i++) {
            changePlayer(i, player);
        }
        System.out.println(player);

        // Down - Top 탐색
        for (int i = N - 1; i > line; i--) {
            changePlayer(i, result);
        }
        System.out.println(result);

        // player[i]와 result[i]가 일치하지 않을 경우 자리 바꾸기
        boolean isFail = false;
        for (int i = 0; i < K - 1; i++) {
            // 자리가 일치할 경우 *
            if (player[i] == result[i]) {
                bw.append("*");
            }
            // 일치하지 않을 경우 현재 위치와 다음 위치가 일치한지 확인
            else if (player[i] == result[i + 1]) {
                bw.append("-");
                // 자리 미리 바꾸기
                char p = player[i];
                player[i] = player[i + 1];
                player[i + 1] = p;
            }
            // 아예 일치하지 않을 경우 노답
            else {
                isFail = true;
                break;
            }
        }

        if (isFail) {
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int i = 0; i < K - 1; i++) {
                bw.append("x");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static void changePlayer(int i, char[] arr) {
        for (int j = 0; j < K - 1; j++) {
            if (map[i][j] == '-') {
                char p = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = p;
            }
        }
    }
}
