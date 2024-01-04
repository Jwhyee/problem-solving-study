package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 테트로미노 (GOL4)
 * 시간 : 696 ms
 * 메모리 : 33396 KB
 * 링크 : https://www.acmicpc.net/problem/14500
 */
public class BOJ_14500 {
    static int max = Integer.MIN_VALUE, H, W;
    static int[][] map;
    static int[] dx = {1, 0, -1, 0}, dy = {0, 1, 0, -1};
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                visited[i][j] = true;
                dfs(i, j, map[i][j], 1);
                visited[i][j] = false;
            }
        }

        System.out.println(max);
    }

    private static void dfs(int y, int x, int sum, int cnt) {
        // 테트로미노 완성할 경우 최대값 갱신
        if (cnt == 4) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 범위가 맞는지 확인
            if (nx >= 0 && nx < W && ny >= 0 && ny < H) {
                // 아직 방문하지 않은 곳일 경우
                if (!visited[ny][nx]) {
                    // 보라색 테트로미노(블록할 철)을 만들기 위해 두 번째 칸에서 탐색진행
                    if (cnt == 2) {
                        visited[ny][nx] = true;
                        dfs(y, x, sum + map[ny][nx], cnt + 1);
                        visited[ny][nx] = false;
                    }

                    visited[ny][nx] = true;
                    dfs(ny, nx, sum + map[ny][nx], cnt + 1);
                    visited[ny][nx] = false;
                }
            }
        }
    }
}
