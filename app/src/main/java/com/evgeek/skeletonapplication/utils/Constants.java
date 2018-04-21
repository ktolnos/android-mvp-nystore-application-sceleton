package com.evgeek.skeletonapplication.utils;

import java.util.regex.Pattern;

/**
 * Created by evgeek on 10/9/17.
 */

public class Constants {
    public final static String BASE_URL = "https://example.com/";
    public final static String BASE_API_URL = BASE_URL + "api/v1/";

    public final static String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public final static String TOKEN_SHARED_PREFS = "user-token";
    public final static String EMAIL_SHARED_PREFS = "user-email";

    //public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{1,6}$", Pattern.CASE_INSENSITIVE);
}
