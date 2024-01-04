package dfs;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 전쟁 - 전투(SIL1)
 * 시간 : 84 ms
 * 메모리 : 12436 KB
 * 링크 : https://www.acmicpc.net/problem/1303
 */
public class BOJ_1303 {
    static int N, M;
    static char[][] map;
    static int[] maxArr, cntArr;
    static int[] dx = {1, 0, -1, 0}, dy = {0, -1, 0, 1};
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[M][N];
        visited = new boolean[M][N];

        // 0 : 아군, 1 : 적군
        maxArr = new int[2];
        cntArr = new int[2];

        for (int i = 0; i < M; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 0; j < N; j++) {
                map[i][j] = charArray[j];
            }
        }

        // 아군 W, 적군 B
        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (!visited[y][x]) {
                    char curTeam = map[y][x];
                    int team = findTeam(curTeam);
                    // 카운트 배열 초기화
                    cntArr[team] = 0;

                    dfs(y, x, curTeam);

                    maxArr[team] += (int) Math.pow(cntArr[team], 2);
                }
            }
        }

        bw.append(maxArr[0] + " ").append(maxArr[1] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void dfs(int y, int x, char team) {
        visited[y][x] = true;
        cntArr[findTeam(team)] += 1;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
                if (!visited[ny][nx] && map[ny][nx] == team) {
                    dfs(ny, nx, team);
                }
            }
        }


    }

    private static int findTeam(char team) {
        return team == 'W' ? 0 : 1;
    }

    private static void print() {
        Arrays.stream(map)
                .map(Arrays::toString)
                .forEach(System.out::println);
    }
}
