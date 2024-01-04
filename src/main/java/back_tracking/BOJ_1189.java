package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1189 {
    static int H, W, K, result;
    static char[][] map;
    static boolean[][] visited;
    static int[] dx = {1, 0, -1, 0}, dy = {0, -1, 0, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new char[H][W];
        for (int i = 0; i < H; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 0; j < W; j++) {
                map[i][j] = charArray[j];
            }
        }

        // 방문 배열 초기화 및 시작 위치 방문 처리
        visited = new boolean[H][W];
        visited[H - 1][0] = true;

        dfs(H - 1, 0, 1);

        System.out.println(result);
    }

    private static void dfs(int y, int x, int cnt) {
        if (cnt == K) {
            if(y == 0 && x == W - 1) result++;
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < W && ny >= 0 && ny < H) {
                if (!visited[ny][nx] && map[ny][nx] == '.') {
                    visited[ny][nx] = true;
                    dfs(ny, nx, cnt + 1);
                    visited[ny][nx] = false;
                }
            }
        }
    }
}
