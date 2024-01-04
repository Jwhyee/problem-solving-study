package collection;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : AC (GOL5)
 * 시간 : 636 ms
 * 메모리 : 94084 KB
 * 링크 : https://www.acmicpc.net/problem/5430
 */
public class BOJ_5430 {
    static int T, N;
    static final char REVERSE = 'R';
    static int[] numbers;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            char[] commands = br.readLine().toCharArray();
            N = Integer.parseInt(br.readLine());
            numbers = new int[N];
            String arrStr = br.readLine();
            stringToArr(arrStr);
            doCmd(commands);
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void stringToArr(String str) {
        str = str.substring(1, str.length() - 1);
        StringTokenizer st = new StringTokenizer(str, ",");
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }
    }

    private static void doCmd(char[] commands) throws IOException {
        Deque<Integer> deque = new ArrayDeque<>();
        for (int number : numbers) {
            deque.offer(number);
        }
        boolean isRight = true, isError = false;

        for (char command : commands) {
            if (command == REVERSE) {
                isRight = !isRight;
                continue;
            }

            if (isRight) {
                if (deque.pollFirst() == null) {
                    isError = true;
                    break;
                }
            } else {
                if (deque.pollLast() == null) {
                    isError = true;
                    break;
                }
            }

        }

        if (isError) {
            bw.append("error");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');

            if (deque.size() > 0) {
                if (isRight) {
                    sb.append(deque.pollFirst());
                    while (!deque.isEmpty()) {
                        sb.append(',').append(deque.pollFirst());
                    }
                } else {
                    sb.append(deque.pollLast());
                    while (!deque.isEmpty()) {
                        sb.append(',').append(deque.pollLast());
                    }
                }
            }

            sb.append(']');

            bw.append(sb);
        }
        bw.append('\n');
    }
}
