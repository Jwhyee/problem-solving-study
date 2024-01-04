package back_tracking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 제목 : NxM 보드 완주하기 [골드3]
 * 시간 : 412 ms
 * 메모리 : 34192 KB
 * 링크 : https://www.acmicpc.net/problem/9944
 * */
public class BOJ_9944 {
    static int H, W, count, min;
    static int[] dx = {1, 0, -1, 0}, dy = {0, 1, 0, -1};
    static char[][] map;
    static boolean[][] visited;
    static boolean flag;
    static List<Node> emptyAreaList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int num = 1;
        String input = "";
        while ((input = br.readLine()) != null) {
            st = new StringTokenizer(input);
            if (!st.hasMoreTokens()) {
                break;
            }
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];
            emptyAreaList = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                char[] charArray = br.readLine().toCharArray();
                for (int j = 0; j < W; j++) {
                    char c = charArray[j];
                    map[i][j] = c;
                    if (c == '.') {
                        emptyAreaList.add(new Node(j, i));
                    }
                }
            }
            min = Integer.MAX_VALUE;
            // 모든 빈칸에서 탐색

            if (emptyAreaList.size() == 1) {
                min = 0;
            } else if (emptyAreaList.size() == 0) {
                min = -1;
            } else {
                for (Node node : emptyAreaList) {
                    for (int i = 0; i < 4; i++) {
                        visited = new boolean[H][W];
                        count = 1;
                        flag = false;

                        if (isPossible(node.y + dy[i], node.x + dx[i])) {
                            backTracking(node.x, node.y, 0, i);
                            if (flag) {
                                min = Math.min(min, count);
                            }
                        }

                    }
                }
            }

            if (min == Integer.MAX_VALUE) {
                min = -1;
            }


            bw.append("Case ").append(String.valueOf(num++)).append(": ").append(String.valueOf(min)).append("\n");

        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void backTracking(int x, int y, int depth, int dir) {
        // 탈출 조건 추가
        if (depth == emptyAreaList.size() - 1) {
            min = Math.min(min, count);
            return;
        }

        // 현재 좌표 방문처리
        visited[y][x] = true;

        // 다음 방향 탐색
        int ny = y + dy[dir];
        int nx = x + dx[dir];

        // 기존 방향으로 갈 수 있을 경우 계속 진행
        if (isPossible(ny, nx)) {
            backTracking(nx, ny, depth + 1, dir);
            // 다른 방향으로도 탐색할 수 있도록 false
            visited[ny][nx] = false;
        }
        // 방향을 꺾어야 할 경우 count 증가
        else {
            // 어느 방향으로 갈지 탐색
            for (int i = 0; i < 4; i++) {
                int nd = (dir + i) % 4;
                nx = x + dx[nd];
                ny = y + dy[nd];

                // 이동하다가 벽 혹은 이미 방문한 곳이면 방향 전환
                if (isPossible(ny, nx)) {
                    count++;
                    visited[ny][nx] = true;
                    backTracking(nx, ny, depth + 1, nd);
                    visited[ny][nx] = false;
                    count--;
                }

            }
        }

        return;
    }

    private static boolean isPossible(int y, int x) {
        if (y >= 0 && y < H && x >= 0 && x < W) {
            if (!visited[y][x] && map[y][x] == '.') {
                return true;
            }
        }
        return false;
    }

    private static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}
