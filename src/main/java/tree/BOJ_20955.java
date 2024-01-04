package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 민서의 응급 수술(GOL4)
 * 시간 : 316 ms
 * 메모리 : 37884 KB
 * 링크 : https://www.acmicpc.net/problem/20955
 */
public class BOJ_20955 {
    static int N, M;
    static int[] parents;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 배열 -1로 초기화
        parents = new int[N + 1];
        Arrays.fill(parents, -1);


        int cnt = 0;
        // 시냅스의 개수가 0이 될 때까지 반복
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            // 두 뉴런을 가지고 유니온 파인드 진행
            if (!union(u, v)) {
                cnt++;
            }
        }

        for (int i = 1; i <= N; i++) {
            if (parents[i] < 0) {
                cnt++;
            }
        }

        System.out.println(--cnt);
    }

    private static boolean union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a == b) return false;

        int h = parents[a] < parents[b] ? a : b;
        int l = parents[a] < parents[b] ? b : a;

        parents[h] += parents[l];
        parents[l] = h;

        return true;
    }

    private static int find(int x) {
        if (parents[x] < 0) {
            return x;
        }
        return parents[x] = find(parents[x]);
    }
}
