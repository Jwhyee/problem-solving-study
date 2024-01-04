package graph;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 색칠하기 (GOL5)
 * 시간 : 632 ms
 * 메모리 : 143340 KB
 * 링크 : https://www.acmicpc.net/problem/13265
 */
public class BOJ_13265 {
    static int T, n, m;
    static boolean isPossible;
    static int[] colors;
    static ArrayList<Integer>[] graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        // T만큼 반복
        while (T-- > 0) {
            // n, m 입력
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            isPossible = true;

            // 테케마다 그래프 및 색 배열 초기화
            graph = new ArrayList[n + 1];
            colors = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            // 이어진 선을 그래프에 저장
            while (m-- > 0) {
                st = new StringTokenizer(br.readLine());
                int p1 = Integer.parseInt(st.nextToken());
                int p2 = Integer.parseInt(st.nextToken());

                graph[p1].add(p2);
                graph[p2].add(p1);
            }

            // 각 원(노드)을 돌면서 색 지정
            for (int i = 1; i <= n; i++) {
                if(!isPossible) break;
                if (colors[i] == 0) {
                    colors[i] = 1;
                    dfs(i);
                }
            }

            bw.append(isPossible ? "possible" : "impossible").append('\n');
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void dfs(int cur) {
        if(!isPossible) return;

        ArrayList<Integer> list = graph[cur];

        // 현재 노드와 다음 노드의 색을 비교한다.
        for (Integer next : list) {
            // 아직 초기화되지 않았다면, 현재 색과 반대의 색으로 칠해줌
            if (colors[next] == 0) {
                colors[next] = 3 - colors[cur];
                dfs(next);
            }
            // 다음 노드가 현재 노드와 같은 색이라면, 종료
            if (colors[next] == colors[cur]) {
                isPossible = false;
                return;
            }
        }
    }
}
