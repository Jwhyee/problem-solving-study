package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 치킨 배달 (GOL5)
 * 시간 : 204 ms
 * 메모리 : 15364 KB
 * 링크 : https://www.acmicpc.net/problem/15686
 */
public class BOJ_15686 {
    static int N, M, min;
    static List<Node> homeList, chickenList;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 지도의 크기
        N = Integer.parseInt(st.nextToken());

        // 치킨집의 수 M <= 13
        M = Integer.parseInt(st.nextToken());

        // 리스트 초기화
        homeList = new ArrayList<>();
        chickenList = new ArrayList<>();

        // 집, 치킨집 리스트에 좌표 추가
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                // 0 : 빈칸 / 1 : 집 / 2 : 치킨집
                int num = Integer.parseInt(st.nextToken());
                if (num == 1) homeList.add(new Node(j, i));
                else if (num == 2) chickenList.add(new Node(j, i));
            }
        }

        min = Integer.MAX_VALUE;

        // 한 집에 대해서 치킨집의 좌표를 빼기
        visited = new boolean[chickenList.size()];
        backTracking(0, 0);

        System.out.println(min);
        br.close();
    }

    private static void backTracking(int depth, int cnt) {
        // 깊이가 M일 경우 탐색 시작
        // cnt가 최대에 달하면, 선택된 치킨집을 대상으로 모든 집에 대한 거리를 확인해야함.
        if (cnt == M) {
            // 각 집에 대해서 치킨 집의 최소 거리들의 합
            int sum = 0;

            for (int i = 0; i < homeList.size(); i++) {
                int temp = Integer.MAX_VALUE;
                for (int j = 0; j < chickenList.size(); j++) {
                    if (visited[j]) {
                        int dx = Math.abs(homeList.get(i).x - chickenList.get(j).x);
                        int dy = Math.abs(homeList.get(i).y - chickenList.get(j).y);
                        int result = dx + dy;
                        temp = Math.min(temp, result);
                    }
                }
                sum += temp;
            }
            min = Math.min(sum, min);
            return;
        }

        for (int i = depth; i < chickenList.size(); i++) {
            visited[i] = true;

            backTracking(i + 1, cnt + 1);

            visited[i] = false;
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