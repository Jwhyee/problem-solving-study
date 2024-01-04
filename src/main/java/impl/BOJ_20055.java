package impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 컨베이어 벨트 위의 로봇(GOL5)
 * 시간 : 184 ms
 * 메모리 : 13256 KB
 * 링크 : https://www.acmicpc.net/problem/20055
 */
public class BOJ_20055 {
    static int N, K, cnt = 0;
    static int[] belt;
    static boolean[] robot;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        belt = new int[N * 2];
        robot = new boolean[N * 2];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < belt.length; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }

        int turn = 0;
        while (cnt < K) {
            turn++;
            System.out.println(turn + "회차");
            // 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
            turnBelt();
            System.out.println("벨트 회전");
            System.out.println(Arrays.toString(belt));
            System.out.println(Arrays.toString(robot));
            System.out.println();

            // 2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
            // 2-1. 로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
            moveRobot();
            System.out.println("로봇 움직임");
            System.out.println(Arrays.toString(belt));
            System.out.println(Arrays.toString(robot));
            System.out.println();

            // 3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
            if (belt[0] >= 1) {
                robot[0] = true;
                belt[0] -= 1;
                if (belt[0] == 0) {
                    cnt++;
                }
            }
            System.out.println("로봇 올리기");
            System.out.println(Arrays.toString(belt));
            System.out.println(Arrays.toString(robot));
            System.out.println("------------");
            // 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
            if (cnt >= K) {
                break;
            }
        }

        System.out.println(turn);

    }

    private static void turnBelt() {
        int prevBeltVal = belt[(N * 2) - 1];
        int curBeltVal = belt[0];

        boolean prevRobotVal = robot[(N * 2) - 1];
        boolean curRobotVal = robot[0];

        belt[0] = prevBeltVal;
        robot[0] = prevRobotVal;

        for (int i = 0; i < (N * 2) - 1; i++) {
            prevBeltVal = belt[i + 1];
            belt[i + 1] = curBeltVal;
            curBeltVal = prevBeltVal;

            prevRobotVal = robot[i + 1];
            robot[i + 1] = curRobotVal;
            curRobotVal = prevRobotVal;
        }
    }

    private static void moveRobot() {
        int last = N - 1;

        for (int x = N - 1; x >= 0; x--) {
            // i값이 0층 마지막 좌표이고, 로봇이 있다면 내리기
            if (x == last && robot[x]) {
                robot[x] = false;
            }
            // 그게 아닐 경우 로봇을 다음 칸으로 옮기기
            else {
                int nx = x + 1;
                // 만약 현재 좌표에 로봇이 있고, 다음 칸에 로봇이 없고, 다음 좌표의 내구도가 1이상이라면
                // 로봇을 옮기고, 내구도 깎기
                if (!robot[nx] && robot[x] && belt[nx] > 0) {
                    robot[x] = false;
                    robot[nx] = true;
                    belt[nx] -= 1;
                    // 만약 옮긴 후 내구도가 0이라면 카운트 증가
                    if (belt[nx] == 0) {
                        cnt++;
                    }
                    // 내리는 위치에 도달할 경우 즉시 내리기
                    if (nx == last && robot[nx]) {
                        robot[nx] = false;
                    }
                }
            }
        }
    }
}
