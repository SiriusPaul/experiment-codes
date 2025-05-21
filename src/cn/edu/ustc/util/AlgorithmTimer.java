package cn.edu.ustc.util;

import java.util.function.Supplier;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/19
 * @Description
 */
public class AlgorithmTimer {
    public static <T> TimingResult<T> measure(Supplier<T> algorithm) {
        long startTime = System.nanoTime();
        T result = algorithm.get();
        long endTime = System.nanoTime();
        return new TimingResult<>(result, endTime - startTime);
    }

    /// 测量算法执行时间的结果类
        public record TimingResult<T>(T result, long executionTimeNanos) {

        public double getExecutionTimeMillis() {
                return executionTimeNanos / 1_000_000.0;
            }
        }
}
