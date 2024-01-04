package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 빙산(GOL4)
 * 시간 : 396 ms
 * 메모리 : 46920 KB
 * 링크 : https://www.acmicpc.net/problem/2573
 */
public class BOJ_2573 {
    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int max = 0;
        for (int year = 0; year < 300; year++) {
            visited = new boolean[N][M];
            int cnt = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (!visited[i][j] && map[i][j] != 0) {
                        cnt += dfs(i, j);
                    }
                }
            }
            if (cnt >= 2) {
                sb.append(year);
                break;
            }
        }
        if (sb.length() != 0) {
            System.out.println(sb);
        } else {
            System.out.println(0);
        }
    }

    private static int dfs(int y, int x) {
        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < M && ny >= 0 && ny < N) {
                if (!visited[ny][nx]) {
                    if (map[ny][nx] == 0) {
                        if (map[y][x] > 0) {
                            map[y][x] -= 1;
                        }
                    } else {
                        dfs(ny, nx);
                    }
                }
            }
        }

        return 1;
    }

    public static void showMatrix(int[][] map) {
        Arrays.stream(map)
                .map(Arrays::toString)
                .forEach(System.out::println);
        System.out.println("----------------");
    }
}
