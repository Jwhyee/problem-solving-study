package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 벽 부수고 이동하기 (GOL3)
 * 시간 : 708 ms
 * 메모리 : 161304 KB
 * 링크 : https://www.acmicpc.net/problem/2206
 */
public class BOJ_2206 {
    static int N, M;
    static char[][] map;
    static int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0};
    static boolean[][][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M][2];

        for (int i = 0; i < N; i++) {
            char[] charArray = br.readLine().toCharArray();
            if (M >= 0) System.arraycopy(charArray, 0, map[i], 0, M);
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(0, 0, 1, false));

        while (!queue.isEmpty()) {
            Pos cur = queue.poll();
            int cx = cur.x;
            int cy = cur.y;
            int cnt = cur.cnt;
            int nc = cnt + 1;

            if (cy == N - 1 && cx == M - 1) {
                return cnt;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && ny >= 0 && nx < M && ny < N) {
                    if (map[ny][nx] == '0') {
                        if (!cur.destroyed && !visited[ny][nx][0]) {
                            queue.offer(new Pos(nx, ny, nc, false));
                            visited[ny][nx][0] = true;
                        } else if (cur.destroyed && !visited[ny][nx][1]) {
                            queue.offer(new Pos(nx, ny, nc, true));
                            visited[ny][nx][1] = true;
                        }
                    } else if(map[ny][nx] == '1'){
                        if (!cur.destroyed) {
                            queue.offer(new Pos(nx, ny, nc, true));
                            visited[ny][nx][1] = true;
                        }
                    }
                }
            }

        }

        return -1;
    }

    static class Pos {
        int x, y, cnt;
        boolean destroyed;

        public Pos(int x, int y, int cnt, boolean destroyed) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.destroyed = destroyed;
        }
    }

}
