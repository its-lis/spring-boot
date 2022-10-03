package com.autumn.weather.algorithm;

/**
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 例如，121 是回文，而 123 不是。
 *
 * 示例1：
 * 输入：x = 121
 * 输出：true
 *
 * 示例2：
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *
 * 示例 3：
 * 输入：x = 10
 * 输出：false
 * 解释：从右向左读, 为 01 。因此它不是一个回文数。
 */
public class No2 {
    public static void main(String[] args) {
        System.out.println(isPalindrome1(12340432));
    }

    public static boolean isPalindrome1(int x) {
        char[] chars = String.valueOf(x).toCharArray();
        int len = chars.length;
        for (int i = 0; i < len/2; i++) {
            if (chars[i] != chars[len-i-1]) {
                return false;
            }
        }
        return true;
    }
}
