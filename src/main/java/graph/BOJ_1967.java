package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 트리의 지름 (GOL4)
 * 시간 : 176 ms
 * 메모리 : 20624 KB
 * 링크 : https://www.acmicpc.net/problem/1967
 */
public class BOJ_1967 {
    static int N, leafNode, result;
    static ArrayList<int[]>[] lists;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        // 그래프 선언 및 초기화
        lists = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            lists[i] = new ArrayList<>();
        }

        // 부모 및 자식 노드 그래프에 추가
        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int point = Integer.parseInt(st.nextToken());

            lists[child].add(new int[]{parent, point});
            lists[parent].add(new int[]{child, point});
        }

        visited = new boolean[N + 1];

        // 최대값 탐색
        dfs(1, 0);

        visited = new boolean[N + 1];

        dfs(leafNode, 0);

        System.out.println(result);

        br.close();
    }

    private static void dfs(int nodeNum, int sum) {
        // 노드 방문 처리
        visited[nodeNum] = true;

        for (int[] node : lists[nodeNum]) {
            // 방문하지 않은 자식 노드 탐색
            if (!visited[node[0]]) {
                dfs(node[0], sum + node[1]);
            }
        }

        // 거리가 결과 값보다 더 클 경우(마지막 노드까지 탐색한 경우)
        if (result < sum) {
            // 최대값 갱신
            result = sum;
            // 리프노드 지정
            leafNode = nodeNum;
        }

    }

//    private static class Node {
//        int linked, point;
//
//        public Node(int linked, int point) {
//            this.linked = linked;
//            this.point = point;
//        }
//    }
}

/*
(O)
5
1 2 3
1 3 3
2 4 5
2 5 7
=> 13

(O)
4
1 2 1
1 3 5
1 4 2
=> 7

(O)
5
1 2 100
1 3 100
2 4 100
3 5 1
=> 301

(O)
4
1 2 100
1 3 100
2 4 57
=> 257

(O)
5
1 2 100
1 3 100
2 4 100
3 5 100
=> 400

(O)
5
1 3 2
3 4 3
4 2 4
4 5 6
=> 11

(O)
3
1 2 3
1 3 50
=> 53

(O)
1
=> 0

(X)
4
1 3 1
2 4 1
3 2 1
=> 3
*/