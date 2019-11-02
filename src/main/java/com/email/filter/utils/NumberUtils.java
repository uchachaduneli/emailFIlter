/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email.filter.utils;

import java.text.DecimalFormat;

/**
 * @author
 */
public class NumberUtils {

    public static String twoDigitAfterPoint(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat.format(number);
    }

    public static String oneDigitAfterPoint(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        decimalFormat.setMaximumFractionDigits(1);

        return decimalFormat.format(number);
    }
}
