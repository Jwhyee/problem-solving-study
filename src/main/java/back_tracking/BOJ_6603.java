package back_tracking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 로또 (SIL2)
 * 시간 : 100 ms
 * 메모리 : 12936 KB
 * 링크 : https://www.acmicpc.net/problem/6603
 */
public class BOJ_6603 {
    static int n;
    static int[] arr;
    static boolean[] visited;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st;

        while (true) {
            String s = br.readLine();
            if(s.equals("0")) break;

            st = new StringTokenizer(s);
            n = Integer.parseInt(st.nextToken());

            arr = new int[n];
            visited = new boolean[n];

            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            backTracking(0, 0);

            bw.append("\n");
        }

        bw.flush();
        bw.close();
        br.close();

    }
    private static void backTracking(int depth, int cnt) throws IOException {
        if (cnt == 6) {
            for (int i = 0; i < n; i++) {
                if (visited[i]) {
                    bw.append(arr[i] + " ");
                }
            }
            bw.append("\n");
        }

        for (int i = depth; i < n; i++) {
            visited[i] = true;

            backTracking(i + 1, cnt + 1);

            visited[i] = false;
        }
    }
}
