package graph;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 제목 : 소셜 네트워킹 어플리케이션 [골드5]
 * 시간 : 768 ms
 * 메모리 : 167196 KB
 * 링크 : https://www.acmicpc.net/problem/7511
 */
public class BOJ_7511 {
    static int[] parent;
    private static final String STR = "Scenario ";
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            int k = Integer.parseInt(br.readLine());

            parent = new int[n];
            Arrays.fill(parent, -1);
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a, b);
            }

            int m = Integer.parseInt(br.readLine());
            sb.append(STR).append(i + 1).append(":\n");

            for (int j = 0; j < m; j++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if(find(a) == find(b)) sb.append(1).append("\n");
                else sb.append(0).append("\n");
            }
            bw.append(sb.toString()).append("\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if(a == b) return;
        if (parent[a] < parent[b]) {
            parent[a] += parent[b];
            parent[b] = a;
        } else {
            parent[b] += parent[a];
            parent[a] = b;
        }
    }

    private static int find(int n) {
        if(parent[n] < 0) return n;
        return parent[n] = find(parent[n]);
    }
}
