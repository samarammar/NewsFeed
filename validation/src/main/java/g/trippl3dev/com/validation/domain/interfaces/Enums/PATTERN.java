package g.trippl3dev.com.validation.domain.interfaces.Enums;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Mahmoud Abdelaal on 2/20/2018.
 * Common Patterns Class
 */
@StringDef({PATTERN.EMAIL, PATTERN.PHONE, PATTERN.PHONEHOME
        , PATTERN.PASSWORD
        , PATTERN.arabicChars
        , PATTERN.USERNAME
        , PATTERN.DESCRIPTION
        , PATTERN.ARABICDESCRIPTION
        , PATTERN.BIRTHDATE
        , PATTERN.ARABICUSERNAME
        , PATTERN.NUMBERS
        , PATTERN.ANYPATTERN
        , PATTERN.USERNAME
        , PATTERN.PRICES
})
@Retention(RetentionPolicy.SOURCE)
public @interface PATTERN {
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
//    String PHONE = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{8})$";
    String PHONE = "^(5)([0-9]{8})$";
    String PHONEMin = "^[0-9]{8,15}$";
//    String PHONE ="5.{9}$";
    String PHONEHOME = "^(01)([0-9]{7,20})$";
    String PASSWORD = "^[0-9a-zA-Z\u0621-\u064A\u0660-\u0669](?!.*\\.\\.)[a-zA-Z\u0621-\u064A\u0660-\u0669.\\d\\s]{5,15}$";
    String arabicChars = "\u0621-\u064A\u0660-\u0669";
    String USERNAME = "^[a-zA-Z\u0621-\u064A\u0660-\u0669](?!.*\\.\\.)[a-zA-Z\u0621-\u064A\u0660-\u0669.\\d\\s]{2,25}$";
    String DESCRIPTION = ".{15,1000}$";//"^[a-zA-Z\u0621-\u064A\u0660-\u06690-9](?!.*\\.\\.)[a-zA-Z\u0621-\u064A\u0660-\u0669.\\d\\s]{3,140}$";
    String BIRTHDATE = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";
    String ARABICUSERNAME = "(^[\u0621-\u064A\u0660-\u06690-9\\s]{3,30}$)";
    String ARABICDESCRIPTION = "^[\u0621-\u064A\u0660-\u06690-9](?!.*\\.\\.)[\u0621-\u064A\u0660-\u0669.\\d\\s]{3,140}$";
    String NUMBERS = "[0-9\\.]{1,50}$";
    String ANYPATTERN = ".{3,1000}$";
    String PRICES="[1-9][0-9]*";
    String EmptyOnly=".{1,1000}$";
}