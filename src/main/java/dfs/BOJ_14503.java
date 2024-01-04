package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 제목 : 로봇 청소기 [골드5]
 * 시간 : 80 ms
 * 메모리 : 11832 KB
 * 링크 : https://www.acmicpc.net/problem/14503
 * */
public class BOJ_14503 {
    static int H, W, cnt = 1;
    static int[] dx = {0, 1, 0, -1}, dy = {-1, 0, 1, 0};
    static int[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visited = new boolean[H][W];

        st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // 0 : 북 / 1 : 동 / 2 : 남 / 3 : 서
        // 위 방향을 보고 시작함
        int dir = Integer.parseInt(st.nextToken());

        // 지도 배열 초기화
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(y, x, dir);


        System.out.println(cnt);


    }

    public static void dfs(int y, int x, int dir) {

        // 현재 위치를 청소하지 않았다면 청소 처리
        if (map[y][x] == 0) {
            map[y][x] = -1;
        }

        // 현재 바라보는 방향의 반시계(-90도)로 회전하며 탐색
        for(int i=0; i<4; i++) {
            // 북 -> 서 -> 남 -> 동
            dir = (dir + 3) % 4;
            int ny = y + dy[dir];
            int nx = x + dx[dir];

            // 청소가 안된 곳이 있으면 회전한 방향으로 탐색
            if (ny >= 0 && ny < H && nx >= 0 && nx < W && map[ny][nx] == 0) {
                cnt++;
                dfs(ny, nx, dir);
                // 청소가 끝나고 다시 돌아오는 것을 방지
                return;
            }
        }

        // 현재 칸 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우
        int reverseDir = (dir + 2) % 4;
        int ny = y + dy[reverseDir];
        int nx = x + dx[reverseDir];

        // 바라보는 방향을 유지한 채로 한 칸 후진한다.
        if (isPossible(nx, ny) && map[ny][nx] != 1) {
            dfs(ny, nx, dir);
        }
        // 바라보는 방향의 뒤 쪽이 벽이라 후진할 수 없을 경우 작동을 멈춘다.
        else if (isWall(nx, ny)) {
            return;
        }
    }

    private static boolean isWall(int x, int y) {
        return x == W - 1 || y == H - 1 || map[y][x] == 1;
    }

    private static boolean isPossible(int x, int y) {
        return y >= 0 && y < H && x >= 0 && x < W;
    }

}
