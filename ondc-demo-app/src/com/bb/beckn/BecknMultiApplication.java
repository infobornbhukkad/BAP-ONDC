package com.bb.beckn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class BecknMultiApplication
{
    public static void main(final String[] args) throws ParseException {
        SpringApplication.run((Class)BecknMultiApplication.class, args);   
        
        //System.out.println("test --- " + ConnectionUtil.getConnection());
    }
}
