package collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_17952 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        Stack<Homework> stack = new Stack<>();
        int result = 0;
        Homework cur = null;

        while (T-- > 0) {

            // 과제는 받자마자 시작한다. == min -= 1
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            if (n == 0) {
                if (cur != null) {
                    cur.min -= 1;
                    if (cur.min == 0) {
                        result += cur.score;
                        cur = null;
                        if(!stack.isEmpty()) cur = stack.pop();
                    }
                }
                continue;
            }


            if (cur != null) {
                stack.push(cur);
            }

            int score = Integer.parseInt(st.nextToken());
            int min = Integer.parseInt(st.nextToken()) - 1;

            cur = new Homework(score, min);

            if (cur.min == 0) {
                result += cur.score;
                cur = null;
                if(!stack.isEmpty()) cur = stack.pop();
            }

        }

        stack = null;
        cur = null;
        br.close();

        System.out.println(result);

    }

    static class Homework {
        int score, min;

        public Homework(int score, int min) {
            this.score = score;
            this.min = min;
        }

    }
}
