package com.myLab.corejava.algorithm;

import java.util.Stack;

/**
 * leetcode:有效的括号  第20题
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 */
public class LCEffectiveBracket {
    /**
     * 典型思路是用栈来解决
     * @param s
     * @return
     */
    public boolean solution(String s) {
        char[] chars = s.toCharArray();
        //必须以左括号开头
        if(s.startsWith(")")||s.startsWith("}")||s.startsWith("]"))
            return false;
        if (chars.length > 1) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(' || chars[i] == '{' || chars[i] == '[')
                    stack.push(chars[i]);//1.遇见左括号就入栈
                else if (chars[i] == ')') {//2.遇见右括号就与栈顶元素进行匹配，如果能匹配到就出栈
                    if (!stack.isEmpty() && stack.peek() == '(')//解决这种情况"(){}}{" 的匹配
                        stack.pop();
                    else
                        return false;
                } else if (chars[i] == '}') {
                    if (!stack.isEmpty() && stack.peek() == '{')
                        stack.pop();
                    else
                        return false;
                } else if (chars[i] == ']') {
                    if (!stack.isEmpty() && stack.peek() == '[')
                        stack.pop();
                    else
                        return false;
                }
            }
            if (stack.isEmpty())//解决这种情况"()("的匹配
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    /**
     * 更好的一种解法，代码更加简洁
     * @param s
     * @return
     */
    public boolean solution1(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c: s.toCharArray()) {
            if(c == '{') stack.push('}');
            else if(c == '(') stack.push(')');
            else if(c == '[') stack.push(']');
            else if(stack.isEmpty() || stack.pop() != c) return false;
        }
        return stack.isEmpty();
    }

    /**
     * 一种替换字符的思路也值得借鉴
     * @param s
     * @return
     */
    public boolean solution2(String s) {
        while(s.contains("()")||s.contains("{}")||s.contains("[]")){
            s.replace("{}","");
            s.replace("()", "");
            s.replace("[]", "");
        }
        return s.equalsIgnoreCase("");
    }

}
