// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class BecknMultiApplication
{
    public static void main(final String[] args) throws ParseException {
        SpringApplication.run((Class)BecknMultiApplication.class, args);
               
        String dateString = "2023-02-03T11:00:00.000Z";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date inputDate = dateFormat.parse(dateString);

        Date today = new Date();

        long timeDiff = today.getTime() - inputDate.getTime();
        long dayDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        System.out.println("Gap in days: " + dayDiff);


       
        System.out.println("test --- " + ConnectionUtil.getConnection());
    }
}
