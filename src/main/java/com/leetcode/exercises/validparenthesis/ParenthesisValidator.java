package com.leetcode.exercises.validparenthesis;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

/*
19 Valid Parentheses

Given a string containing just the characters ’(’, ’)’, ’’, ’’, ’[’ and ’]’, determine if the
input string is valid. The brackets must close in the correct order, "()" and "()[]" are all
valid but "(]" and "([)]" are not.
 **/
public final class ParenthesisValidator {

    private static final Map<Character, Character> PARENTHESIS_GROUP = Map
            .of(')', '(', '}', '{', ']', '[');
    private static final Collection<Character> KEYS = PARENTHESIS_GROUP.keySet();

    private final String text;

    public ParenthesisValidator(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Parameter is empty or null");
        }
        if (!text.replaceAll("[\\[\\](){}]", "").trim().isEmpty()) {
            throw new IllegalArgumentException("Only '[', ']', '(', ')', '{' and '}' are valid input!");
        }
        this.text = text;
    }

    public boolean isValid() {
        Stack<Character> stack = new Stack<>();
        for (char c : text.toCharArray()) {
            if (KEYS.contains(c)) {
                if (stack.size() == 0) {
                    return false;
                }
                char stackItem = stack.pop();
                char openParenthesisEquivalent = PARENTHESIS_GROUP.get(c);
                if (openParenthesisEquivalent != stackItem) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.empty();
    }
}
