package back_tracking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 스도쿠 (GOL4)
 * 시간 : 336 ms
 * 메모리 : 14712 KB
 * 링크 : https://www.acmicpc.net/problem/2580
 */
public class BOJ_2580 {
    static int map[][];
    static List<Node> emptyNodeList;
    static final int MAX_LENGTH = 9;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 스도쿠 판 초기화
        map = new int[MAX_LENGTH][MAX_LENGTH];
        // 비어있는 칸 저장할 리스트
        emptyNodeList = new ArrayList<>();

        // 입력 받기
        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                int n = Integer.parseInt(st.nextToken());
                map[i][j] = n;
                if (n == 0) {
                    emptyNodeList.add(new Node(j, i));
                }
            }
        }

        // 백트래킹 빈 칸 찾기
        findNumber(0);

        for (int[] ints : map) {
            for (int anInt : ints) {
                bw.append(anInt + " ");
            }
            bw.append("\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean findNumber(int depth) {
        // 모든 칸을 다 찾았을 경우 true 반환
        if (depth == emptyNodeList.size()) {
            return true;
        }

        // 현재 0의 좌표
        Node cur = emptyNodeList.get(depth);
        int x = cur.x;
        int y = cur.y;

        // 1 ~ 9까지 들어갈 수 있는 수 탐색
        for (int i = 1; i <= 9; i++) {
            if (isPossible(x, y, i)) {
                // 해당 칸을 i 값으로 가정하고 값 대입
                map[y][x] = i;
                // i 값으로 스도쿠를 만들 수 있다면 true 반환
                if(findNumber(depth + 1)) return true;
                // 만들 수 없을 경우 다시 0으로 초기화
                else map[y][x] = 0;
            }
        }
        return false;
    }

    private static boolean isPossible(int x, int y, int num) {
        // 현재 좌표의 상하, 좌우에 같은 수가 있는지 판단
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (map[y][i] == num || map[i][x] == num) return false;
        }

        // 가로 및 세로를 세 블럭으로 나눴을 때, 몇 번째 칸에 있는지 확인
        int blockOfX = (x / 3) * 3;
        int blockOfY = (y / 3) * 3;

        // 사각형에 값이 들어있다면 false 반환
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (map[blockOfY + i][blockOfX + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }


    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
