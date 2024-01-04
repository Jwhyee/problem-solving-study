package collection;

import java.io.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 문제 이름(난이도) : 큐 2(LV2)
 * 시간 : 1452 ms
 * 메모리 : 366232 MB
 * 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/18258
 */
public class BOJ_18258 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        Deque<Integer> queue = new LinkedList<>();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();

            if (s.equals("push")) {
                queue.offer(Integer.parseInt(st.nextToken()));
            } else {
                switch (s) {
                    case "front" :
                        if (queue.isEmpty()) sb.append(-1).append("\n");
                        else sb.append(queue.peek()).append("\n");
                        break;
                    case "back" :
                        if (queue.isEmpty()) sb.append(-1).append("\n");
                        else sb.append(queue.peekLast()).append("\n");
                        break;
                    case "empty" :
                        if(queue.isEmpty()) sb.append(1).append("\n");
                        else sb.append(0).append("\n");
                        break;
                    case "size" :
                        sb.append(queue.size()).append("\n");
                        break;
                    case "pop" :
                        if(queue.isEmpty()) sb.append(-1).append("\n");
                        else sb.append(queue.poll()).append("\n");
                        break;
                }
            }
        }
        bw.append(sb);
        bw.flush();

        bw.close();
        br.close();
    }
}
