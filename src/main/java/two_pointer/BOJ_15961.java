package two_pointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 회전초밥(골드4)
 * 시간 : 504 ms
 * 메모리 : 168,552 KB
 * 링크 : https://www.acmicpc.net/problem/15961
 */
public class BOJ_15961 {
    public static void main(String[] args) throws IOException {
        // N 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 접시, 가짓수, 연속해서 먹는 접시 수, 쿠폰 번호
        int N = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 회전 초밥 배열
        int[] sushi = new int[N];

        // 먹은 초밥 배열
        int[] ate = new int[d + 1];

        for (int i = 0; i < N; i++) {
            sushi[i] = Integer.parseInt(br.readLine());
        }


        int cnt = 0, left = 0, right = 0, result = 0;

        // 벨트의 임의의 한 위치부터 k개의 접시를 연속해서 먹을 경우 할인된 정액 가격으로 제공한다.
        // 첫 벨트를 돌면서 먹은 초밥 개수 확인
        for (int i = 0; i < k; i++) {
            ate[sushi[right]]++;
            if (ate[sushi[right]] == 1) {
                cnt++;
            }
            right++;
        }

        result = cnt;

        // 왼쪽 인덱스가 N이 될 때까지 반복
        while (left != N) {

            // 첫 벨트에서 먹음 처리를 했기 때문에 -1을 해서 처음 먹는 초밥인지 확인
            if (--ate[sushi[left]] == 0) {
                cnt--;
            }
            left++;

            // 오른쪽 인덱스는 처음 확인하는 초밥이므로 +1을 통해 먹은 초밥인지 확인
            if (++ate[sushi[right]] == 1) {
                cnt++;
            }
            right++;

            // 오른쪽 인덱스가 끝에 도달하면 0으로 초기화
            if (right == N) {
                right = 0;
            }

            // 쿠폰 초밥을 먹지 않았다면 cnt + 1 / 먹었다면 그대로 cnt
            // 각 고객에게 초밥의 종류 하나가 쓰인 쿠폰을 발행하고,
            // 1번 행사에 참가할 경우 이 쿠폰에 적혀진 종류의 초밥 하나를 추가로 무료로 제공한다.
            // 만약 이 번호에 적혀진 초밥이 현재 벨트 위에 없을 경우, 요리사가 새로 만들어 손님에게 제공한다.
            if (ate[c] == 0) {
                result = Math.max(result, cnt + 1);
            } else {
                result = Math.max(result, cnt);
            }
        }
        br.close();
        System.out.println(result);
    }
}
