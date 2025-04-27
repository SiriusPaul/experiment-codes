package cn.edu.ustc.service;

import cn.edu.ustc.model.MaxSubArrayResult;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/14
 * @Description
 */
public interface Strategy {
    MaxSubArrayResult calc(int[] arr);
}
