package com.ala.elearning.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alaam on 12/31/2017.
 */

public class DateUtil {
    public static String formatDate(long time){
        Date date = new Date(time);
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = null;
        if(date.getYear() < nowDate.getYear()){
            dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a", Locale.getDefault());
        }else if(date.getMonth() == nowDate.getMonth()){
            if(date.getDay() - nowDate.getDay() > 7 ){
                dateFormat = new SimpleDateFormat("EEE, d MMM, h:mm a", Locale.getDefault());
            }else if (date.getDay() != nowDate.getDay()){
                dateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
            }else {
                dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            }
        }else {
            dateFormat = new SimpleDateFormat("EEE, d MMM, h:mm a", Locale.getDefault());
        }

        return dateFormat.format(date);
    }
}
