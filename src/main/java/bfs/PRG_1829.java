package bfs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 문제 이름(난이도) : 카카오프렌즈 컬러링북(LV2)
 * 시간 : 8.07 ms
 * 메모리 : 75.6 MB
 * 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/1829
 */
public class PRG_1829 {
    int areaSize, areaMaxSize, currentColor;
    int H, W;
    boolean[][] visited;
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    int[][] map;
    public int[] solution(int m, int n, int[][] picture) {
        // 영역을 저장할 List
        List<Integer> areaList = new ArrayList<>();

        // 방문 배열 초기화
        visited = new boolean[m][n];

        // 지도 및 높이, 길이 복사
        map = picture;
        H = m;
        W = n;

        // 전체 지도를 돌면서 영역 확인
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 방문하지 않고, 0(빈칸)이 아니라면 dfs 실행
                if (!visited[i][j] && map[i][j] != 0) {
                    // 현재 색상 복사 및 영역 크기 초기화
                    currentColor = picture[i][j];
                    areaSize = 0;

                    // 한 좌표를 기준으로 어느정도 깊이가 있는지 확인해야하기 때문에 DFS 사용
                    dfs(i, j);

                    // 리스트에 영역 크기 저장
                    areaList.add(areaSize);

                    // 최대 영역 계산
                    areaMaxSize = Math.max(areaMaxSize, areaSize);
                }
            }
        }

        return new int[]{areaList.size(), areaMaxSize};
    }

    public void dfs(int y, int x) {
        visited[y][x] = true;
        areaSize++;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < W && ny >= 0 && ny < H) {
                if (!visited[ny][nx] && map[ny][nx] == currentColor) {
                    dfs(ny, nx);
                }
            }
        }
    }

    @Test
    void test1() {
        int m = 6, n = 4;
        int[][] picture = {{1, 1, 1, 0},
                            {1, 2, 2, 0},
                            {1, 0, 0, 1},
                            {0, 0, 0, 1},
                            {0, 0, 0, 3},
                            {0, 0, 0, 3}};
        int[] solution = solution(m, n, picture);
        System.out.println("solution = " + Arrays.toString(solution));
        assertTrue(Arrays.equals(solution, new int[]{4, 5}));
    }
}
