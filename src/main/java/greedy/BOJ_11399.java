package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : ATM (SIL4)
 * 시간 : 88 ms
 * 메모리 : 11832 KB
 * 링크 : https://www.acmicpc.net/problem/11399
 */
public class BOJ_11399 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // ATM에 줄 서 있는 사람의 수
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        // 인출하는데 걸리는 시간들
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int temp = 0;
        int result = 0;
        for (int i = 0; i < n; i++) {
            temp += arr[i];
            result += temp;
        }

        System.out.println(result);

    }
}
