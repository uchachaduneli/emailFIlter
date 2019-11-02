/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email.filter.utils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class GeoSimpleDateFormat extends SimpleDateFormat {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GeoSimpleDateFormat() {
        super.setDateFormatSymbols(getGeoSymbols());
    }

    public GeoSimpleDateFormat(String pattern) {
        super(pattern);
        super.setDateFormatSymbols(getGeoSymbols());
    }

    private DateFormatSymbols getGeoSymbols() {

        DateFormatSymbols geoSymbols = new DateFormatSymbols();


        return geoSymbols;
    }

}
