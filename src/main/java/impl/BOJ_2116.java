package impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 주사위 쌓기(GOL5)
 * 시간 : 21252 ms
 * 메모리 : 188 KB
 * 링크 : https://www.acmicpc.net/problem/2116
 */
public class BOJ_2116 {
    static int N, MAX;
    static int[][] dices, sortedDices, dicesMatchCnt;
    static int[] faceDice = {5, 3, 4, 1, 2, 0};
    static boolean[] diceMatch;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        // A, B, C, D, E, F
        // A : 윗면
        // B, C, D, E : 옆면
        // F : 아랫면
        dices = new int[N][6];
        sortedDices = new int[N][6];
        dicesMatchCnt = new int[N][6];
        diceMatch = new boolean[N];

        // 주사위 배열 초기화
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 6; j++) {
                dices[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        MAX = Integer.MIN_VALUE;

        // 아래층부터 쌓아올리기 위해 윗 면을 정하기
        for (int cur = 0; cur < 6; cur++) {
            int max = 0;

            // 옆면 중에서 가장 큰 값 찾기
            for (int compare = 0; compare < 6; compare++) {
                if(cur == compare || compare == faceDice[cur]) continue;
                max = Math.max(max, dices[0][compare]);
            }

            // 윗면의 값, 옆면 중 가장 큰 값, 쌓은 개수
            buildDice(dices[0][cur], max, 1);
        }
        System.out.println(MAX);
    }

    private static void buildDice(int prevTop, int sum, int cnt) {
        // 최대 층까지 쌓았을 경우 최대값을 확인하고, 종료
        if (cnt == N) {
            MAX = Math.max(sum, MAX);
            return;
        }


        // 이전 주사위의 Top에 해당하는 값을 현재 주사위에서 찾기
        int curTop = 0;
        for (int cur = 0; cur < 6; cur++) {
            if (dices[cnt][cur] == prevTop) {
                curTop = cur;
                break;
            }
        }

        // 해당 주사위가 마주보는 값이 다음 주사위의 Top
        int nextTop = faceDice[curTop];

        // 옆면 중에서 가장 큰 값 탐색
        int max = 0;
        for (int cur = 0; cur < 6; cur++) {
            if(cur == nextTop || cur == curTop) continue;
            max = Math.max(max, dices[cnt][cur]);
        }

        buildDice(dices[cnt][nextTop], sum + max, cnt + 1);

    }
}