package exceptions;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public enum ErrorMessages {

    INVALID_TOKEN,
    INVALID_LOGIN,
    INVALID_PASSWORD,
    USER_ACCOUNT_NOT_ACTIVE,
    USER_ACCOUNT_LOCKED_OUT,

    INVALID_LOCATION_NAME,
    INVALID_LOCATION_ADDRESS,
    INVALID_LOCATION_LONGITUDE,
    INVALID_LOCATION_LATITUDE,
    INVALID_LOCATION_STATUS,

    INVALID_ADDRESS_CITY,
    INVALID_ADDRESS_COUNTRY,

    INVALID_INDIVIDUAL_FIRST_NAME,
    INVALID_INDIVIDUAL_LAST_NAME,

    INVALID_USER_LOGIN,
    INVALID_USER_LOGIN_EXISTS_IN_SYSTEM,
    INVALID_USER_PASSWORD,
    INVALID_USER_STATUS,
    INVALID_USER_TOKEN,
    INVALID_USER_EMAIL,
    INVALID_USER_EMAIL_EXISTS_IN_SYSTEM,

    INVALID_LOCATION_PREVIOUS_STATUS,
    INVALID_LOCATION_CURRENT_STATUS,

    INVALID_GOOGLE_GEOCODE_ADDRESS,

    INVALID_USER_ACCOUNT,
    INVALID_LOCATION,
    INVALID_RATING,
    INVALID_DATE,

    SERVER_SIDE_ERROR;

}
