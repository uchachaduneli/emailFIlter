/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email.filter.utils;

/**
 * @author
 */
public class StringMatcher {

    private static final String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    // returns true if the string is email
    public static boolean isEmail(String s) {
        return s.matches(EMAIL_PATTERN);
    }
}
