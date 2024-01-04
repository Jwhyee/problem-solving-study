package impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 톱니바퀴(GOL5)
 * 시간 : 80 ms
 * 메모리 : 11608 KB
 * 링크 : https://www.acmicpc.net/problem/14891
 */
public class BOJ_14891 {
    static int[][] wheel = new int[4][8];
    static boolean[] isTurn;
    static int K;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for (int i = 0; i < wheel.length; i++) {
            char[] charArray = br.readLine().toCharArray();
            for (int j = 0; j < wheel[i].length; j++) {
                wheel[i][j] = charArray[j] - '0';
            }
        }

        K = Integer.parseInt(br.readLine());

        for (int i = 0; i < K; i++) {
            /* 설명 코드 */
            map = new int[4][3][3];
            /* 설명 코드 */

            // 돌려야할 톱니(num)와 돌릴 방향(dir) 초기화
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());

            /* 설명 코드 */
            System.out.println();
            initMap();
            System.out.println("BEFORE");
            printMap();
            /* 설명 코드 */

            // 어떤 톱니를 돌릴지 찾기
            findTurnWheel(num - 1);
            // num - 1번 휠은 dir 방향으로 돌리고, 나머지는 dir * -1 방향으로 돌리기
            // 연결된 톱니 찾기
            doTurnWheel(num - 1, dir);

            /* 설명 코드 */
            System.out.println();
            System.out.println("AFTER");
            initMap();
            printMap();
            System.out.println("\n----------------");
            /* 설명 코드 */
        }

        System.out.println(getScore());
    }

    private static int getScore() {
        int sum = 0;
        int score = 1;
        for (int i = 0; i < 4; i++) {
            if (i != 0) {
                score *= 2;
            }
            int n = wheel[i][0];
            sum += (n == 1 ? score : 0);
        }
        return sum;
    }

    private static void doTurnWheel(int num, int dir) {
        System.out.println();
        System.out.println(Arrays.toString(isTurn));
        System.out.print("돌릴 톱니의 인덱스 : " + num + ", 방향 : " + (dir == 1 ? "시계" : "반시계"));
        // 현재 톱니 먼저 돌리기
        turnWheel(num, dir);

        // 만약 1번이 시계 방향으로 돌고, 1, 2, 3번이 돌아야할 경우
        // 1번은 시계 방향, 2번은 반시계 방향, 3번은 시계 방향으로 돌아야함
        // 때문에 leftDir(rightDir) *= -1 을 해서 방향을 계속 바꿔줘야함

        // 왼쪽으로 연결된 톱니 찾기
        int leftDir = dir;
        for (int i = num; i > 0; i--) {
            if (isTurn[i - 1]) {
                leftDir *= -1;
                turnWheel(i - 1, leftDir);
            } else {
                break;
            }
        }
        // 오른쪽으로 연결된 톱니 찾기
        int rightDir = dir;
        for (int i = num; i < 3; i++) {
            if (isTurn[i + 1]) {
                rightDir *= -1;
                turnWheel(i + 1, rightDir);
            } else {
                break;
            }
        }
    }

    private static void findTurnWheel(int num) {
        isTurn = new boolean[4];
        // 현재 톱니의 동쪽, 서쪽 극 확인
        int curWest = wheel[num][6];
        int curEast = wheel[num][2];
        isTurn[num] = true;

        // 현재 톱니를 기준으로 왼쪽으로 탐색
        // 만약 현재 톱니의 서쪽과 다음 톱니의 동쪽 극이 서로 다르다면,
        // 다음 톱니를 돌려야할 톱니로 지정
        for (int i = num; i > 0; i--) {
            int nextWest = wheel[i - 1][6];
            int nextEast = wheel[i - 1][2];
            isTurn[i - 1] = (Math.abs(curWest - nextEast) == 1);
            curEast = nextEast;
            curWest = nextWest;
        }

        // 오른쪽도 돌려야하기 때문에 값 초기화
        curWest = wheel[num][6];
        curEast = wheel[num][2];

        for (int i = num; i < 3; i++) {
            int nextWest = wheel[i + 1][6];
            int nextEast = wheel[i + 1][2];
            isTurn[i + 1] = (Math.abs(curEast - nextWest) == 1);
            curEast = nextEast;
            curWest = nextWest;
        }
    }

    private static void turnWheel(int num, int dir) {
        // 배열 참조를 복사해서 진행
        int[] numberOfWheel = wheel[num];
        // 시계 방향
        if (dir == 1) {
            // 이전 값(끝)과 현재 값(처음)을 미리 저장 후 변경
            int prevVal = numberOfWheel[7], curVal = numberOfWheel[0];
            numberOfWheel[0] = prevVal;
            // 시계 방향으로 값을 계속해서 변경
            for (int i = 0; i < 7; i++) {
                prevVal = numberOfWheel[i + 1];
                numberOfWheel[i + 1] = curVal;
                curVal = prevVal;
            }
        }
        // 반시계 방향
        else {
            // 이전 값(처음)과 현재 값(끝)을 미리 저장 후 변경
            int prevVal = numberOfWheel[0], curVal = numberOfWheel[7];
            numberOfWheel[7] = prevVal;
            // 시계 방향으로 값을 계속해서 변경
            for (int i = 7; i > 0; i--) {
                prevVal = numberOfWheel[i - 1];
                numberOfWheel[i - 1] = curVal;
                curVal = prevVal;
            }
        }
    }

    static int[][][] map = new int[4][3][3];
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int dir = 0, cnt = 0, idx = 0;
    private static void initMap() {
        for (int i = 0; i < map.length; i++) {
            cnt = idx = 0;
            mapDfs(i, 0, 1);
        }
    }

    private static void mapDfs(int z, int y, int x) {
        if (cnt > 7) {
            return;
        }
        map[z][y][x] = wheel[z][idx++];
        cnt++;

        int nx = x + dx[dir];
        int ny = y + dy[dir];

        // 다음 좌표가 유효하지 않을 경우 방향 조정
        if (nx < 0 || nx >= 3 || ny < 0 || ny >= 3) {
            dir = (dir + 1) % 4;
            ny = y + dy[dir];
            nx = x + dx[dir];
        }
        mapDfs(z, ny, nx);
    }

    private static void printMap() {
        // map[n][0]
        System.out.println("N = 0 | S = 1(+)");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(map[i][0][j] + " ");
            }
            System.out.print("\t");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(map[i][1][j] + " ");
            }
            System.out.print("\t");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(map[i][2][j] + " ");
            }
            System.out.print("\t");
        }
    }
}
