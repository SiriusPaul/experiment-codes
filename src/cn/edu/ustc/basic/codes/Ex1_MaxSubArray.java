
package cn.edu.ustc.basic.codes;

import java.util.Scanner;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/9
 * @Description
 */
public class Ex1_MaxSubArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the array's length");
        int length = sc.nextInt();
        int[] nums = new int[length];
        System.out.println("Please enter the array's elements");
        for (int i = 0; i < length; i++) {
            nums[i] = sc.nextInt();
        }
        System.out.println("The maximum subarray sum is: " + maxSubArray(nums));
    }

    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int sum = 0;
        for(int num : nums){
            sum = Math.max(sum + num, num);
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
}
