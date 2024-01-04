package back_tracking;

import java.io.*;
import java.util.Arrays;

public class BOJ_6443 {
    static BufferedWriter bw;
    static int N, length;
    static char[] alphas, ana, mx;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            alphas = br.readLine().toCharArray();
            length = alphas.length;

            visited = new boolean[length];
            mx = new char[length];
            ana = new char[length];

            Arrays.sort(alphas);
            backTracking(0);
        }

        br.close();
        alphas = null;
        visited = null;

        bw.flush();
        bw.close();
    }

    private static void backTracking(int depth) throws IOException {
        if (depth == length) {
            bw.write(ana);
            bw.write('\n');
            return;
        }

        mx[depth] = 0;
        for (int i = 0; i < length; i++) {
            if(mx[depth] >= alphas[i]) continue;
            if (!visited[i]) {
                visited[i] = true;
                mx[depth] = ana[depth] = alphas[i];
                backTracking(depth + 1);
                visited[i] = false;
            }
        }
    }
}
