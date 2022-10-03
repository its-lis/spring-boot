package com.autumn.weather.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
 *
 * 示例：
 * 输入： nums = [2,7,11,15]
 * 输出： [0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 */
public class No1 {

    @Test
    void twoSum() {
        int[] nums = new  int[]{2,7,11,15};
        int target = 9;
        long t1 = System.currentTimeMillis();
        int[] twoSum1 = twoSum1(nums, target);
        long t2 = System.currentTimeMillis();
        int[] twoSum2 = twoSum2(nums, target);
        long t3 = System.currentTimeMillis();
        System.out.println(Arrays.toString(twoSum1));
        System.out.println(Arrays.toString(twoSum2));
        System.out.println(t2 - t1);
        System.out.println(t3 - t2);
    }

    public static int[] twoSum1(int[] nums, int target) {
        int[] report = new int[2];
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    report[0] = i;
                    report[1] = j;
                }
            }
        }
        return report;
    }

    public static int[] twoSum2(int[] nums, int target) {
        int[] report = new int[2];
        int len = nums.length;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            if (map.containsKey(target -nums[i])) {
                return new int[] {map.get(target -nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return report;
    }
}
