package back_tracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2023 {
    static int n;
    static int[] primes = {1, 2, 3, 5, 7, 9}, numbers;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        numbers = new int[n];
        sb = new StringBuilder();
        br.close();

        backTracking(0, 0);

        System.out.println(sb);
        sb = null;
    }

    private static void backTracking(int depth, int curNum) {
        if (depth == n) {

            sb.append(curNum).append("\n");

            return;
        }

        for (int prime : primes) {
            if (depth == 0 && prime == 1) {
                continue;
            }

            numbers[depth] = prime;
            int nextNum = curNum(depth);
            if(isPrime(nextNum))
                backTracking(depth + 1, nextNum);
        }
    }

    private static int curNum(int depth) {
        int num = 0;
        for (int i = depth, j = 1; i >= 0; i--, j *= 10) {
            num += (numbers[i] * j);
        }
        return num;
    }

    private static boolean isPrime(int num) {
        for (int i = 2; i <= (int) Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}