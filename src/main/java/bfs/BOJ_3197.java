package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 백조의 호수(PLA1)
 * 시간 : 784 ms
 * 메모리 : 212588 KB
 * 링크 : https://www.acmicpc.net/problem/3197
 */
public class BOJ_3197 {
    static int W, H;
    static char[][] map;
    static boolean[][] visited;
    static Node[] swarovski = new Node[2];
    static Queue<Node> waterQueue, swalQueue;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};
    static boolean flag = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        visited = new boolean[H][W];

        swalQueue = new LinkedList<>();
        waterQueue = new LinkedList<>();

        int idx = 0;
        for (int y = 0; y < H; y++) {
            char[] charArray = br.readLine().toCharArray();
            for (int x = 0; x < W; x++) {
                map[y][x] = charArray[x];
                if (charArray[x] != 'X') {
                    waterQueue.add(new Node(x, y));
                }
                if (charArray[x] == 'L') {
                    swarovski[idx++] = new Node(x, y);
                }
            }
        }

        int cnt = 0;
        swalQueue.offer(swarovski[0]);
        while (true) {
            moveSwarovski();
            if (flag) {
                break;
            }
            meltIce();
            cnt++;
        }
        System.out.println(cnt);
    }

    private static void moveSwarovski() {
        Queue<Node> nextDayQueue = new LinkedList<>();

        while (!swalQueue.isEmpty()) {
            Node cur = swalQueue.poll();

            visited[cur.y][cur.x] = true;

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (nx == swarovski[1].x && ny == swarovski[1].y) {
                    flag = true;
                    return;
                }

                if (nx >= 0 && ny >= 0 && nx < W && ny < H) {
                    if (!visited[ny][nx]) {
                        if (map[ny][nx] == 'X') {
                            nextDayQueue.add(new Node(nx, ny));
                            continue;
                        }
                        swalQueue.add(new Node(nx, ny));
                    }
                }
            }
        }
        swalQueue = nextDayQueue;
    }

    private static void meltIce() {
        int size = waterQueue.size();
        for (int loop = 0; loop < size; loop++) {
            Node cur = waterQueue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < W && ny < H) {
                    if (map[ny][nx] == 'X') {
                        map[ny][nx] = '.';
                        waterQueue.add(new Node(nx, ny));
                    }
                }
            }
        }
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
