package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 진우의 민트초코우유 (GOL5)
 * 시간 : 764 ms
 * 메모리 : 12532 KB
 * 링크 : https://www.acmicpc.net/problem/20208
 */
public class BOJ_20208 {
    static int N, HP, POTION, max;
    static boolean[] visited;
    static List<Node> milkList;
    static Node homeNode;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 지도의 크기 N * N
        N = Integer.parseInt(st.nextToken());

        // 초기 HP / 민초 마시고 회복되는 양
        HP = Integer.parseInt(st.nextToken());
        POTION = Integer.parseInt(st.nextToken());

        // 우유가 있는 위치 리스트
        milkList = new ArrayList<>();

        // init
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int n = Integer.parseInt(st.nextToken());
                if (n == 1) {
                    homeNode = new Node(j, i);
                } else if (n == 2) {
                    milkList.add(new Node(j, i));
                }
            }
        }

        // 방문 배열 초기화
        visited = new boolean[milkList.size()];

        // 백트래킹
        backTracking(0, homeNode.x, homeNode.y, 0, HP);

        // 출력
        System.out.println(max);
        br.close();
    }

    /**
     * 백트래킹
     * @param depth 백트래킹 깊이
     * @param prevX 이전 x 좌표
     * @param prevY 이전 y 좌표
     * @param cnt 이동 횟수
     * @param curHp 현재 체력
     * */
    private static void backTracking(int depth, int prevX, int prevY,
                                     int cnt, int curHp) {

        int distance = getDistance(homeNode.x, prevX, homeNode.y, prevY);

        // 현재 위치에서 집까지 돌아갈 수 있다면 최대값 갱신
        if (distance <= curHp) {
            max = Math.max(max, cnt);
        }

        // 모든 좌표 중에서 도달할 수 있는 우유 위치 탐색
        for (int i = 0; i < milkList.size(); i++) {
            Node node = milkList.get(i);

            // 이전 위치에서 가고자하는 우유의 위치 까지의 거리 계산
            distance = getDistance(prevX, node.x, prevY, node.y);

            // 만약 이동해야하는 거리가 체력보다 작고, 방문하지 않았을 경우
            if (distance <= curHp && !visited[i]) {
                // 기존 체력 저장
                int temp = curHp;

                // 현재 위치 방문 처리
                visited[i] = true;

                // 이동하고자하는 좌표까지의 체력 계산
                curHp = (curHp - distance) + POTION;

                // 백트래킹
                backTracking(depth + 1, node.x, node.y, cnt + 1, curHp);

                // 탐색 이후 원래 체력으로 복귀
                curHp = temp;

                // 방문 해제 처리
                visited[i] = false;
            }
        }
    }

    private static int getDistance(int x1, int x2, int y1, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}