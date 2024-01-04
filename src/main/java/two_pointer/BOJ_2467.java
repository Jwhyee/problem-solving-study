package two_pointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 용액(골드5)
 * 시간 : 272 ms
 * 메모리 : 31292 KB
 * 링크 : https://www.acmicpc.net/problem/2467
 */
public class BOJ_2467 {
    public static void main(String[] args) throws IOException {
        // N 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 용액 배열 생성 및 초기화
        int[] liquid = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            liquid[i] = Integer.parseInt(st.nextToken());
        }

        // 가장 작은 값, 가장 큰 값의 index 초기화
        int leftIdx = 0;
        int rightIdx = N - 1;

        // 산성 + 알칼리성의 최소 값을 저장할 변수
        int min = Integer.MAX_VALUE;

        // 최소값의 인덱스를 저장할 변수
        int minLeftIdx = 0, minRightIdx = 0;

        // 탐색 중 왼쪽 인덱스가 오른쪽을 초과하기 전까지 진행
        while (leftIdx < rightIdx) {

            // 두 용액의 합 저장
            int sum = liquid[leftIdx] + liquid[rightIdx];

            // 기존 최소값보다 더한 값의 절댓값이 더 작을 경우
            if (min > Math.abs(sum)) {
                // 최소값 및 최소값의 인덱스 초기화
                min = Math.abs(sum);
                minLeftIdx = leftIdx;
                minRightIdx = rightIdx;
            }
            // 두 용액의 합이 0 이상일 경우 오른쪽 인덱스만 줄이기
            if (sum >= 0) {
                rightIdx--;
            }
            // 0 미만일 경우 왼쪽 인덱스만 증가
            else {
                leftIdx++;
            }
        }

        System.out.println(liquid[minLeftIdx] + " " + liquid[minRightIdx]);
    }
}
