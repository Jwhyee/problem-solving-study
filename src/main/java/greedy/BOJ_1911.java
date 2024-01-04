package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


/**
 * 문제 이름(난이도) : 흙길 보수하기 (GOL5)
 * 시간 : 428 ms
 * 메모리 : 22536 KB
 * 링크 : https://www.acmicpc.net/problem/1911
 */
public class BOJ_1911 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        // 시작과 끝을 저장할 배열 생성
        int[][] points = new int[N][2];

        // 값 입력 및 배열에 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            points[i] = new int[]{s, e};
        }

        // 배열 정렬
        // 시작 값으로 2차원 배열 정렬
        Arrays.sort(points, Comparator.comparingInt(o -> o[0]));

        // 널판지로 덮을 범위 및 정답 값 초기화
        int range = 0, answer = 0;

        // 좌표 배열을 돌면서 널판지로 덮어야할 범위 지정
        for (int[] point : points) {
            // 시작 값이 범위보다 크다면 범위를 시작값으로 지정
            if (point[0] > range) {
                range = point[0];
            }
            // 끝 값이 범위보다 클 경우
            if (point[1] > range) {
                // 널판지로 덮어야할 범위가 웅덩이 끝 부분까지 될 때까지 반복
                while (point[1] > range) {
                    range += L;
                    answer++;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }
}
