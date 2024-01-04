package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 헌내기는 친구가 필요해 (SIL2)
 * 시간 : 224 ms
 * 메모리 : 46260 KB
 * 링크 : https://www.acmicpc.net/problem/21736
 */
public class BOJ_21736 {
    static int h, w, cnt;
    static int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0};
    static char[][] map;
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        visited = new boolean[h][w];
        map = new char[h][w];

        int y = 0, x = 0;

        for (int i = 0; i < h; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 0; j < w; j++) {
                if (charArray[j] == 'I') {
                    y = i;
                    x = j;
                }
                map[i][j] = charArray[j];
            }
        }

        dfs(y, x);

        System.out.println(cnt != 0 ? cnt : "TT");

    }

    private static void dfs(int y, int x) {
        if (map[y][x] == 'P') cnt++;

        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                if (!visited[ny][nx] && map[ny][nx] != 'X') {
                    dfs(ny, nx);
                }
            }
        }
    }
}
