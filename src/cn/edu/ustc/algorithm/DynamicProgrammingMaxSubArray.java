package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.MaxSubArrayResult;
import cn.edu.ustc.service.MaxSubArrayStrategy;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/9
 * @Description using dynamic programming to solve the maximum subarray problem
 */
public class DynamicProgrammingMaxSubArray implements MaxSubArrayStrategy {
    @Override
    public MaxSubArrayResult calc(int[] arr) {
        // check input
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        int maxSum = arr[0];
        int currentSum = arr[0];
        int startIdx = 0;
        int endIdx = 0;
        int tempStart = 0;


        for (int i = 1; i < arr.length; i++) {
            // update currentSum
            if (currentSum + arr[i] > arr[i]) {
                currentSum += arr[i];
            } else {
                currentSum = arr[i];
                tempStart = i;
            }

            // update maxSum
            if (currentSum > maxSum) {
                maxSum = currentSum;
                startIdx = tempStart;
                endIdx = i;
            }
        }

        return new MaxSubArrayResult(maxSum, startIdx, endIdx);
    }

}
