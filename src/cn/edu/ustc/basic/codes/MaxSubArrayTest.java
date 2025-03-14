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
        //create a context and the strategy
        MaxSubArray maxSubArray = new MaxSubArray();
        Context context = new Context(maxSubArray);
        //execute the strategy
        System.out.println(context.executeStrategy(nums).getMaxSum());
        System.out.println(context.executeStrategy(nums).getStartIndex());
        System.out.println(context.executeStrategy(nums).getEndIndex());

        int nums2[] = {-1,2,-5,3,-4};
        context.setStrategy(new MaxSubArray());
        System.out.println(context.executeStrategy(nums2).getMaxSum());
        System.out.println(context.executeStrategy(nums2).getStartIndex());
        System.out.println(context.executeStrategy(nums2).getEndIndex());
    }
}
