package com.myLab.corejava.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *leetcode 22题，括号生成，思路有些晦涩，需要多看看视频
 *数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合
 */
public class LCGenerateParenthesis {
    public List<String> generateUseRecur(int n) {
    List<String> result = new ArrayList<>();
    generateRecursion(new char[2 * n], 0, result);
    return result;
    }

    /**
     * 递归生成括号的思路就是，每一层生成括号时可以生成左括号，也可以生成右括号，因此左括号和右括号都需要分别向下递归
     * 退出逻辑就是数组下标移动到最后
     * @param chars
     * @param pos
     * @param result
     */
    private void generateRecursion(char[] chars, int pos, List<String> result) {
        //退出逻辑
        if (pos == chars.length ) {
            if (valid(chars)) {
                result.add(new String(chars));
                return;
            } else {
                return;
            }
        }
        chars[pos] = '(';
        generateRecursion(chars, pos + 1, result);
        chars[pos] = ')';
        generateRecursion(chars, pos + 1, result);
    }

    private boolean valid(char[] chars) {
        int match = 0;
        for (int i = 0; i < chars.length; i++) {
            if (match == 0 && chars[i] == ')') {
                return false;
            }
            if (chars[i] == '(') {
                match++;
            }
            if (chars[i] == ')') {
                match--;
            }
        }
        System.out.println(match);
        return match == 0 ? true : false;
    }

    public static void main(String[] args) {
//        char[] chars = {'(', '(', '(', ')', ')', ')'};
//        System.out.println(valid(chars));
        LCGenerateParenthesis test = new LCGenerateParenthesis();
        System.out.println(test.generateUseRecur(3));
    }

}
