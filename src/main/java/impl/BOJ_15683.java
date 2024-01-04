package impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 감시(GOL4)
 * 시간 : 212 ms
 * 메모리 : 44472 KB
 * 링크 : https://www.acmicpc.net/problem/15683
 */
public class BOJ_15683 {
    static int H, W, cnt = 0, result = Integer.MAX_VALUE;
    static int emptyAreaCnt = 0, objectAreaCnt = 0;
    static int[][] map, originCopy;
    static boolean[][] visited;

    // turnableCnt = 방향을 총 몇 번 회전할 수 있는가
    // monitoringDirCnt = CCTV 당 총 몇 방향을 감시하는가
    static int[] turnableCnt = {4, 2, 4, 4, 1}, monitoringDirCnt = {1, 2, 2, 3, 4};
    static Node[] pos;
    static boolean[] posVisited;
    static List<Node> cctv = new ArrayList<>();
    static int[][][] dirPos = {
            {
                    {0}, {1},
                    {0}, {-1},
                    {-1}, {0},
                    {1}, {0}
            },
            {
                    {0, 0}, {1, -1},
                    {-1, 1}, {0, 0}
            },
            {
                    {-1, 0}, {0, 1},
                    {1, 0}, {0, 1},
                    {1, 0}, {0, -1},
                    {-1, 0}, {0, -1}
            },
            {
                    {-1, 0, 0}, {0, 1, -1},
                    {-1, 0, 1}, {0, 1, 0},
                    {1, 0, 0}, {0, 1, -1},
                    {-1, 0, 1}, {0, -1, 0},
            },
            {
                    {0, 0, -1, 1}, {1, -1, 0, 0}
            }
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        // 지도 및 방문 배열 초기화
        map = new int[H][W];
        visited = new boolean[H][W];

        // 경우의 수를 확인할 때마다 배열을 다시 돌려줘야하기 때문에 원본을 저장할 배열 초기화
        originCopy = new int[H][W];

        // 입력 받기
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                int n = Integer.parseInt(st.nextToken());
                // CCTV의 좌표를 리스트에 저장
                if (n != 0 && n != 6) {
                    cctv.add(new Node(j, i, n, 0));
                }
                // 비어있는 공간의 크기 구하기
                if (n == 0) {
                    emptyAreaCnt++;
                }
                map[i][j] = n;
            }
        }

        // CCTV 및 벽이 차지하는 공간 구하기
        objectAreaCnt = (H * W) - emptyAreaCnt;

        // 초기화된 지도 배열을 복사
        originCopy = copyArray(map);

        // CCTV의 좌표를 저장하는 배열
        pos = new Node[cctv.size()];
        posVisited = new boolean[cctv.size()];

        // 리스트를 배열로 변환
        for (int i = 0; i < pos.length; i++) {
            pos[i] = cctv.get(i);
        }

        // 메모리 해제
        cctv = null;

        // 백트래킹 시작
        backDfs(0);

        bw.append(result + "");
        bw.flush();
        br.close();
        bw.close();
    }

    public static int[][] copyArray(int[][] original) {
        if (original == null) return null;

        int numRows = original.length;
        int[][] copy = new int[numRows][];

        for (int i = 0; i < numRows; i++) {
            int numCols = original[i].length;
            copy[i] = new int[numCols];
            System.arraycopy(original[i], 0, copy[i], 0, numCols);
        }

        return copy;
    }

    private static void backDfs(int depth) {
        // 깊이가 CCTV 개수랑 같아질 경우 (더 이상 탐색 불가)
        if (depth == pos.length) {
            // 지도를 원본 상태로 다시 복구
            map = copyArray(originCopy);

            // 총 몇 칸을 감시하는지 셀 변수 초기화
            cnt = 0;

            // CCTV 감시 영역 탐색
            for (Node node : pos) {
                // 현재 CCTV의 번호
                int cctvNum = node.cctvNum;

                // 해당 CCTV가 감시하는 방향만큼 감시 영역 체크
                for (int i = 0; i < monitoringDirCnt[cctvNum - 1]; i++) {
                    fillDfs(node.y, node.x, cctvNum, node.turn, i);
                }

            }

            // 전체 영역에서 (감시 영역의 칸 + 기존 물체들의 개수)를 뺀 값 = 사각지대 영역의 개수
            int totalArea = (H * W);
            totalArea -= (cnt + objectAreaCnt);
            result = Math.min(result, totalArea);

            return;
        }

        // 현재 깊이에 있는 CCTV의 방향 지정
        Node cur = pos[depth];

        // 해당 CCTV가 회전할 수 있는 만큼 반복
        for (int turn = 0; turn < turnableCnt[cur.cctvNum - 1]; turn++) {
            // 아직 회전한 상태를 보지 않았다면 백트래킹 시작
            if (!posVisited[depth]) {
                // 현재 CCTV가 감시할 방향 지정
                cur.turn = turn;

                // 현재 CCTV 방문 처리
                posVisited[depth] = true;

                // 다음 CCTV의 방향을 지정하기 위해 백트래킹 재귀
                backDfs(depth + 1);

                // 현재 방향 탐색이 끝났다면 다시 방문하지 않은 것으로 처리
                posVisited[depth] = false;
            }
        }
    }

    /**
     * @param turn 현재 감시중인 CCTV의 회전 수
     * @param dirPosIdx 현재 감시중인 CCTV의 방향 인덱스
     * */
    private static void fillDfs(int y, int x, int cctvNum, int turn, int dirPosIdx) {
        // 현재 영역(CCTV가 감시하는 영역)을 -1로 처리
        map[y][x] = -1;

        // 다음 좌표 구하기
        // 3차원 배열[CCTV번호][회전수 * 2][방향]
        /* 1번 CCTV 기준으로 동, 서, 남, 북 총 4번을 탐색
        * 회전수 * 2 -> Y의 좌표 / (회전수 * 2) + 1 -> X의 좌표
        {0}, {1},
        {0}, {-1},
        {-1}, {0},
        {1}, {0}
        */
        int ny = y + dirPos[cctvNum - 1][turn * 2][dirPosIdx];
        int nx = x + dirPos[cctvNum - 1][(turn * 2) + 1][dirPosIdx];

        if (ny >= 0 && ny < H && nx >= 0 && nx < W) {
            if (map[ny][nx] != 6) {
                if (map[ny][nx] == 0) {
                    cnt++;
                }
                fillDfs(ny, nx, cctvNum, turn, dirPosIdx);
            }
        }
    }

    static class Node {
        int x, y, cctvNum, turn;

        public Node(int x, int y, int cctvNum, int turn) {
            this.x = x;
            this.y = y;
            this.cctvNum = cctvNum;
            this.turn = turn;
        }
    }
}
