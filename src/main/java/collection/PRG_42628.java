package collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PRG_42628 {
    public int[] solution(String[] operations) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        StringTokenizer st;

        for(String operation : operations) {
            st = new StringTokenizer(operation);
            String cmd = st.nextToken();
            int n = Integer.parseInt(st.nextToken());

            if(cmd.equals("I")) {
                min.offer(n);
                max.offer(n);
            } else if (cmd.equals("D")) {
                if (n == 1) {
                    // 최대값 삭제
                    min.remove(max.poll());
                } else {
                    // 최소값 삭제
                    min.poll();
                }
            }
        }

        if (min.size() == 0) {
            return new int[]{0, 0};
        } else {
            return new int[]{max.poll(), min.poll()};
        }
    }

    public static void main(String[] args) {
        PRG_42628 p = new PRG_42628();

        System.out.println(Arrays.toString(p.solution(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"})));
        System.out.println(Arrays.toString(p.solution(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"})));
    }
}
