/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email.filter.utils;

/**
 * @author
 */
public class StringUtils {

    public static boolean isNotEmptyAndNull(String s) {
        return s != null && !s.isEmpty();
    }
}
