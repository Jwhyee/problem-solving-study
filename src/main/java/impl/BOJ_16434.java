package impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 드래곤 앤 던전(GOL4)
 * 시간 : 376 ms
 * 메모리 : 52768 KB
 * 링크 : https://www.acmicpc.net/problem/16434
 */
public class BOJ_16434 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());

        // 시작 공격력
        long curAtk = Long.parseLong(st.nextToken());

        // 체력, 최대 체력
        long curHp = 0, maxHp = 0;

        // 방의 개수 만큼 반복
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int sort = Integer.parseInt(st.nextToken());

            // 1일 경우 : 몬스터의 생명력
            // 2일 경우 : 용사의 체력 증가 포션
            int attack = Integer.parseInt(st.nextToken());

            // 1일 경우 : 몬스터의 생명력
            // 2일 경우 : 용사의 체력 증가 포션
            int hp = Integer.parseInt(st.nextToken());

            if (sort == 1) {
                // 현재 체력 = 현재 체력 + (몬스터 공격력 * (용사가 몬스터를 패는 횟수 - 잔여 턴)
                // 용사가 몬스터를 패는 횟수 => hp / curAtk
                long fightCnt = hp / curAtk;

                // 잔여 턴(몬스터가 죽으면 전투가 종료) => (health % curAtk) != 0 ? 0 : 1
                // 현재 공격력이 3이고, 몬스터의 체력이 20일 경우 마지막 티키타가 용사 - 몬스터(공) 이후 한 번 더 용사가 때리고 끝냄
                // 1 : 17, 2 : 14, 3 : 11, 4 : 8, 5 : 5, 6 : 2, 7 : -1(사망)
                // 다음 턴에 용사는 맞지 않기 때문에 0을 빼줌

                // 현재 공격력이 5이고, 몬스터의 체력이 20일 경우 마지막 티키타카를 끝으로 맞짱이 끝남
                // 1 : 15, 2 : 10, 3 : 5, 4 : 0(사망)
                // 마지막 턴에서 용사는 맞지 않기 때문에 1을 빼줌
                long totalCnt = (fightCnt) - (hp % curAtk != 0 ? 0 : 1);

                // 현재 체력에 몬스터의 공격력 * 최종으로 떄린 횟수를 곱해줌
                curHp += attack * (totalCnt);

                // 최대 체력 최신화
                maxHp = Math.max(maxHp, curHp);
            } else {
                // 공격 포션 먹고 현재 공격력 증가
                curAtk += attack;

                // 최소한의 최대 HP를 구해야하기 때문에 현재 체력에서 포션 체력을 빼줌
                // 용사가 죽을 경우에 대한 탈출 조건이 없기 때문에 결과가 음수가 나올 경우 현재 체력을 0으로 맞춰줌
                curHp = Math.max(curHp - hp, 0);
            }
        }

        maxHp++;

        System.out.println(maxHp);
        br.close();
    }

}
