package impl;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 웹 브라우저 1(GOL4)
 * 시간 : 176 ms
 * 메모리 : 18812 KB
 * 링크 : https://www.acmicpc.net/problem/16434
 */
public class BOJ_23294 {
    static int N, Q, C, backCache = 0, frontCache = 0;
    static Deque<Integer> back, front;
    static int[] pageSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        pageSize = new int[N + 1];
        back = new ArrayDeque<>();
        front = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            pageSize[i] = Integer.parseInt(st.nextToken());
        }
        int currentPage = -1;

        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            if (command.equals("A")) {
                // 1. 앞으로 가기 공간에 저장된 페이지가 모두 삭제된다. 페이지들이 차지하고 있던 크기만큼 현재 사용 캐시에서 줄어든다.
                front.clear();
                frontCache = 0;

                // 2. 현재 페이지를 뒤로 가기 공간에 추가
                // 단, 처음으로 웹페이지에 접속하는 경우라면, 현재 페이지를 뒤로 가기 공간에 추가하지 않는다.
                if (currentPage != -1) {
                    back.add(currentPage);
                    backCache += pageSize[currentPage];
                }

                // 다음에 접속할 페이지가 현재 페이지로 갱신된다.
                currentPage = Integer.parseInt(st.nextToken());

                // 3. 3번 과정은 2번 과정에서 최대 캐시 용량을 초과할 경우에만 실행된다.
                // 뒤로 가기 공간에서 방문한 지 가장 오래된 페이지 하나를 삭제하며, 그 페이지가 차지하고 있던 크기가 현재 사용 캐시 용량에서 줄어든다.
                // 이 과정은 현재 사용 캐시 용량이 최대 캐시 용량보다 작거나 같아질 때까지 여러번 수행될 수 있다.
                while (backCache + frontCache + pageSize[currentPage] > C) {
                    backCache -= pageSize[back.pollFirst()];
                }

            } else if (command.equals("B")) {
                // 뒤로 가기 공간에 1개 이상의 페이지가 저장되어 있을 때만 2,3번 과정이 실행된다. 0개일 때 이 작업은 무시된다.
                if (!back.isEmpty()) {
                    // 현재 보고 있던 웹페이지를 앞으로 가기 공간에 저장한다.
                    front.add(currentPage);
                    frontCache += pageSize[currentPage];

                    // 뒤로 가기 공간에서 방문한지 가장 최근의 페이지에 접속한다.
                    // 그리고 해당 페이지는 뒤로 가기 공간에서 삭제된다.
                    currentPage = back.pollLast();
                    backCache -= pageSize[currentPage];
                }

            } else if (command.equals("F")) {
                // 앞으로 가기 공간에 1개 이상의 페이지가 저장되어 있을 때만 2,3번 과정이 실행된다. 0개일 때 이 작업은 무시된다.
                if (!front.isEmpty()) {
                    // 현재 보고 있던 페이지를 뒤로 가기 공간에 저장한다.
                    back.add(currentPage);
                    backCache += pageSize[currentPage];

                    // 앞으로 가기 공간에서 방문한지 가장 최근의 페이지에 접속한다.
                    // 그리고 해당 페이지는 앞으로 가기 공간에서 삭제된다.
                    currentPage = front.pollLast();
                    frontCache -= pageSize[currentPage];
                }
            } else {
                // 뒤로 가기 공간에서 같은 번호의 페이지가 연속해서 2개 이상 등장할 경우, 가장 최근의 페이지 하나만 남기고 나머지는 모두 삭제한다.
                // 삭제된 페이지가 차지하고 있던 용량만큼 현재 사용 캐시에서 줄어든다.
                Deque<Integer> tempQueue = new ArrayDeque<>();
                for (int cur : back) {
                    if (tempQueue.isEmpty()) {
                        tempQueue.add(cur);
                    } else {
                        if (tempQueue.peekLast() != cur) {
                            tempQueue.add(cur);
                        } else {
                            backCache -= pageSize[cur];
                        }
                    }
                }
                back = tempQueue;
            }
        }
        bw.append(currentPage + "\n");
        if (back.size() == 0) {
            bw.append("-1");
        } else {
            while (!back.isEmpty()) {
                bw.append(back.pollLast() + " ");
            }
        }
        bw.append("\n");
        if (front.size() == 0) {
            bw.append("-1");
        } else {
            while (!front.isEmpty()) {
                bw.append(front.pollLast() + " ");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
