package impl;

import java.io.*;
import java.util.StringTokenizer;

/*

11 7
XXX.XXX
X.XXX.X
X..X..X
X.....X
.......
.#.....
.#...#.
.#...#.
.##.##.
.#####.
#######

9 7
XXXXXXX
X..X..X
X.XXX.X
X.....X
.......
.#...#.
.##.##.
.#####.
#######

6 8
...X....
........
.......#
.......#
.......#
...#####

5 5
..X..
.....
##.##
##.##
#####

*/
/**
 * 문제 이름(난이도) : 유성(SIL1)
 * 시간 : 시간 초과 ms
 * 메모리 : ... KB
 * 링크 : https://www.acmicpc.net/problem/10703
 */
public class BOJ_10703 {
    static char[][] map, copy;
    static int[] downCnt;
    static int R, S, airCnt, line = 0, total, xCnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        // 지도 배열, 지도 복사 배열, 몇 칸 내려갈 수 있는지 확인하는 배열 초기화
        map = new char[R][S];
        copy = new char[R][S];
        downCnt = new int[S];

        // 값 입력 받기
        for (int i = 0; i < R; i++) {
            char[] arr = br.readLine().toCharArray();
            airCnt = 0;
            for (int j = 0; j < S; j++) {
                char c = arr[j];
                // 공기일 경우 카운트 증가
                if (c == '.') {
                    airCnt++;
                }
                map[i][j] = c;
                copy[i][j] = c;
            }
            // 공기의 수가 한 라인을 다 잡고 있다면 해당 라인 저장
            if (airCnt == S) {
                line = i;
            }
        }

        int downCnt = getDownCnt();
        meteor(downCnt);

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < S; j++) {
                bw.append(map[i][j]);
            }
            bw.append("\n");
        }
        bw.flush();
        bw.close();
    }

    private static int getDownCnt() {
        int floor = 0;
        // 총 몇 칸을 내려갈 수 있는지 확인
        for (int i = 0; i < S; i++) {
            total = xCnt = 0;
            char c = map[floor][i];
            if (c == 'X') {
                xCnt++;
                dfs(floor, i);
            } else {
                dfs(floor, i);
            }

            // 해당 줄에 유성이 없다면 최대값으로 초기화
            if (xCnt == 0) {
                downCnt[i] = Integer.MAX_VALUE;
            } else {
                // 유성이 있다면 결과값 저장
                downCnt[i] = total;
            }
        }

        // 최소값 탐색 후 반환
        int min = Integer.MAX_VALUE;
        for (int j : downCnt) {
            min = Math.min(min, j);
        }

        return min;
    }

    private static void dfs(int y, int x) {
        int ny = y + 1;
        // 유성으로부터 바닥을 만날 때까지 총 몇 칸 내려갈 수 있는지 확인
        if (ny >= 0 && ny < R && x >= 0 && x < S) {
            char nc = map[ny][x];
            if (nc == 'X') {
                xCnt++;
                total = 0;
                dfs(ny, x);
            } else if (nc == '.') {
                total++;
                dfs(ny, x);
            }
        }
    }

    private static void meteor(int cnt) {
        // (배열의 길이 - 중간 공기의 길이 == 유성이 존재할 수 있는 라인)부터 시작
        for (int i = ((R - 1) - line); i >= 0; i--) {
            for (int j = S - 1; j >= 0; j--) {
                char c = map[i][j];
                if (c == 'X') {
                    if (map[i + cnt][j] != '#') {
                        map[i][j] = '.';
                        map[i + cnt][j] = c;
                    }
                }
            }
        }
    }

}
