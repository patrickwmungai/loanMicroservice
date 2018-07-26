/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author patrick
 */
public class NativeFuncs {
    

    public static Timestamp AddSecondsToTimestamp(Timestamp timestamp, int sec) {

        return new Timestamp(timestamp.getTime() + sec * 1000);
    }

    public static Timestamp SubtractSecondsToTimestamp(Timestamp timestamp, int sec) {

        return new Timestamp(timestamp.getTime() - sec * 1000);
    }

    public static Timestamp convertNowToTimestamp(Date date) {
        return (new Timestamp(date.getTime()));
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        return (new Timestamp(date.getTime()));
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getFormatedTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        return sdf.format(date);
    }

    public static String getOracleTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S a");
        return sdf.format(date);
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private static Pattern emailNamePtrn = Pattern.compile(
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean validateEmailAddress(String userName) {

        Matcher mtch = emailNamePtrn.matcher(userName);
        if (mtch.matches()) {
            return true;
        }
        return false;
    }

    public static int generateNumber(int length) {
        String result = "";
        int random;
        while (true) {
            random = (int) ((Math.random() * (10)));
            if (result.length() == 0 && random == 0) {//when parsed this insures that the number doesn't start with 0
                random += 1;
                result += random;
            } else if (!result.contains(Integer.toString(random))) {//if my result doesn't contain the new generated digit then I add it to the result
                result += Integer.toString(random);
            }
            if (result.length() >= length) {//when i reach the number of digits desired i break out of the loop and return the final result
                break;
            }
        }

        return Integer.parseInt(result);
    }

    public static String formatPhoneNumber(String msisdn) {
        String start_char = String.valueOf(msisdn.charAt(0));

        int msisdn_length = msisdn.length();
        System.out.println("Starting char" + start_char);
        System.out.println("MSISDN Length" + msisdn_length);

        if (start_char.equals("+") && msisdn_length == 13) {
            msisdn = msisdn.substring(4);
        } else if (start_char.equals("2") && msisdn_length == 12) {
            msisdn = msisdn.substring(3);
        } else if (start_char.equals("0") && msisdn_length == 10) {
            msisdn = msisdn.substring(1);
        } else if (start_char.equals("7") && msisdn_length == 9) {
            msisdn = msisdn;
        } else {
            return "0";
        }
        return msisdn;
    }

    public static String hashWithMd5(String password) {
        StringBuilder sb = new StringBuilder();
        try {
            // password convert
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

        }
        catch (NoSuchAlgorithmException ex) {
        }

        password = sb.toString();

        return password;
    }
}
