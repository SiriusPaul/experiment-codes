package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.MaxSubArrayResult;
import cn.edu.ustc.service.MaxSubArrayStrategy;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */
public class EnumerationMaxSubArray implements MaxSubArrayStrategy {
    @Override
    public MaxSubArrayResult calc(int[] arr) {
        // check input
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        int maxSum = arr[0];
        int startIdx = 0;
        int endIdx = 0;

        for (int i = 0; i < arr.length; i++) {
            int currentSum = 0;
            for (int j = i; j < arr.length; j++) {
                currentSum += arr[j];
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    startIdx = i;
                    endIdx = j;
                }
            }
        }

        return new MaxSubArrayResult(maxSum, startIdx, endIdx);
    }
}
