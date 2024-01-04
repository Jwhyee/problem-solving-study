package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 안전 영역(SIL1)
 * 시간 : 244 ms
 * 메모리 : 18544 KB
 * 링크 : https://www.acmicpc.net/problem/2468
 */
public class BOJ_2468 {
    static int[][] map;
    static boolean[][] visited;
    static int N, max;

    // 방향 전환 함수 선언 동 -> 북 -> 서 -> 남
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        max = 0;

        map = new int[N][N];

        // 지도 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 지도에서 지역의 최대 높이 구하기
        int maxValue = getMax();

        // 0(물에 잠기지 않는 경우)부터 최대 높이까지 진행
        for (int i = 0; i <= maxValue; i++) {

            // 비가 내린 높이마다 확인해야하므로, 방문 배열 초기화
            visited = new boolean[N][N];

            // 물에 잠기지 않은 지역을 카운트할 변수
            int areaCnt = 0;

            // j : map[j] 컨트롤
            for (int j = 0; j < N; j++) {
                // k : map[j][k] 컨트롤
                for (int k = 0; k < N; k++) {
                    // 방문하지 않았고, 지역의 높이가 비가 내린 높이보다 크면 dfs 진행
                    if (!visited[j][k] && map[j][k] > i) {
                        // 잠기지 않은 지역 누적합
                        areaCnt += dfs(i, j, k);
                    }
                }
            }
            // 비의 높이에 따라 가장 많이 잠기지 않은 영역 구하기
            max = Math.max(max, areaCnt);
        }
        System.out.println(max);
    }

    private static int getMax() {
        int max = Integer.MIN_VALUE;
        for (int[] ints : map) {
            for (int anInt : ints) {
                if (anInt > max) {
                    max = anInt;
                }
            }
        }
        return max;
    }

    private static int dfs(int rainHeight, int y, int x) {
        // 해당 지역 방문 처리
        visited[y][x] = true;

        // 4방향이기 때문에 0 ~ 3까지 반복
        for (int i = 0; i < 4; i++) {
            // 다음 좌표 nextX, nextY
            int nx = x + dx[i];
            int ny = y + dy[i];

            // map 안을 확인해야하므로,
            // 다음 좌표의 값이 0이상이고, N보다 작으면
            if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                // 방문하지 않았고, 비의 높이보다 크다면 dfs 진행
                if (!visited[ny][nx] && map[ny][nx] > rainHeight) {
                    dfs(rainHeight, ny, nx);
                }
            }
        }
        // dfs가 종료되면 1개의 지역이 잠기지 않는 것과 동일하기 때문에 1반환
        return 1;
    }
}
