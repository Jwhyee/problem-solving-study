package back_tracking;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 암호 만들기 (GOL5)
 * 시간 : 80 ms
 * 메모리 : 11888 KB
 * 링크 : https://www.acmicpc.net/problem/1759
 */
public class BOJ_1759 {
    static int L, C;
    static char[] letters, values;
    static boolean[] visited;
    private static BufferedWriter bw;
    private static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        values = new char[L];
        letters = new char[C];
        visited = new boolean[C];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < C; i++) {
            letters[i] = st.nextToken().charAt(0);
        }
        Arrays.sort(letters);

        backDfs(0, 0);

        bw.flush();
        bw.close();
        br.close();
    }

    private static void backDfs(int depth, int cnt) throws IOException {
        if (cnt == L) {
            sb = new StringBuilder();
            // 모음과 자음의 수
            int vowel = 0, consonant = 0;

            // 방문처리된 문자들만 골르고 모음과 자음의 구성이 알맞으면 bw에 추가
            for (int i = 0; i < C; i++) {
                if (visited[i]) {
                    char value = letters[i];
                    sb.append(value);

                    if (value == 'a' || value == 'e' || value == 'i' || value == 'o' || value == 'u') {
                        vowel++;
                    } else consonant++;

                }
            }
            if(vowel >= 1 && consonant >= 2) bw.append(sb.toString()).append("\n");
        }

        // 각 문자를 돌면서 백트래킹 진행
        for (int i = depth; i < C; i++) {

            visited[i] = true;

            backDfs(i + 1, cnt + 1);

            visited[i] = false;
        }


    }
}
