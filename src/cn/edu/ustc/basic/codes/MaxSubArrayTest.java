package cn.edu.ustc.basic.codes;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/11
 * @Description The test class for MaxSubArray
 */
public class MaxSubArrayTest {
    public static void main(String[] args) {
        int[] nums = {-1,20,-5,30,-4};
        MaxSubArray maxSubArray = new MaxSubArray();
        System.out.println(maxSubArray.calc(nums).getMaxSum());
        System.out.println(maxSubArray.calc(nums).getStartIndex());
        System.out.println(maxSubArray.calc(nums).getEndIndex());
    }
}
