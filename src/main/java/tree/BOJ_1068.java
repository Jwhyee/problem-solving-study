package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 트리(GOL5)
 * 시간 : 80 ms
 * 메모리 : 11572 KB
 * 링크 : https://www.acmicpc.net/problem/1068
 */
public class BOJ_1068 {
    static int N, target, cnt;
    static int[] parent;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 트리의 노드 수
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 부모 배열 및 방문 배열 초기화
        parent = new int[N + 1];
        visited = new boolean[N + 1];

        // 최상단 노드
        int root = 0;
        for (int i = 0; i < N; i++) {
            int cur = Integer.parseInt(st.nextToken());
            parent[i] = cur;
            if(cur == -1) root = i;
        }

        // 제거할 타겟 노드 번호
        target = Integer.parseInt(br.readLine());

        // 타겟 노드와 부모가 타겟 노드인 자식 노드를 모두 제거 처리(-2)
        removeTargetNode(target);

        // 리프 노드의 수를 탐색
        countLeaf(root);

        System.out.println(cnt);

    }

    private static void removeTargetNode(int idx) {
        // -2로 제거 처리
        parent[idx] = -2;

        // 타겟의 자식 ~ 손자까지 탐색하면서 제거처리
        for (int i = 0; i < N; i++) {
            if (parent[i] == idx) {
                removeTargetNode(i);
            }
        }
    }

    private static void countLeaf(int idx) {
        // 탐색할 노드가 리프 노드라고 가정하고 시작
        boolean isLeaf = true;
        visited[idx] = true;

        // 부모 노드가 제거 처리되지 않았다면 탐색
        if (parent[idx] != -2) {
            for (int i = 0; i < N; i++) {
                // 부모가 같은 노드가 있을 경우 리프 노드가 아님
                if (parent[i] == idx && !visited[i]) {
                    // 가장 마지막 리프 노드까지 이동
                    countLeaf(i);
                    isLeaf = false;
                }
            }
            // 탐색 결과 리프 노드일 경우 cnt 증가
            if (isLeaf) {
                cnt++;
            }
        }
    }
}
