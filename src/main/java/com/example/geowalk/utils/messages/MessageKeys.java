package com.example.geowalk.utils.messages;

public enum MessageKeys {
    LOGGER_GET_USER_FAILED,
    LOGGER_CREATE_USER_FAILED,
    LOGGER_UPDATE_USER_FAILED,
    LOGGER_DELETE_USER_FAILED,
    LOGGER_ASSIGN_ROLE_FAILED,

    LOGGER_GET_POST_FAILED,
    LOGGER_CREATE_POST_FAILED,
    LOGGER_UPDATE_POST_FAILED,
    LOGGER_VERIFY_POST_FAILED,
    LOGGER_DELETE_POST_FAILED,

    LOGGER_GET_COMMENT_FAILED,
    LOGGER_CREATE_COMMENT_FAILED,
    LOGGER_UPDATE_COMMENT_FAILED,
    LOGGER_DELETE_COMMENT_FAILED,
    LOGGER_VERIFY_COMMENT_FAILED,

    LOGGER_GET_TRAVEL_STOP_FAILED,
    LOGGER_CREATE_TRAVEL_STOP_FAILED,

    LOGGER_CREATE_TRAVEL_ROUTE_FAILED,

    USER_NOT_FOUND_ID,
    USER_NOT_FOUND_EMAIL,
    USER_NOT_AUTHORIZED,
    USER_BLOCKED_OR_DELETED,

    BLOG_COMMENT_NOT_FOUND,
    BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER,
    BLOG_COMMENT_INVALID_RATING_VALUE,

    BLOG_POST_NOT_FOUND,
    BLOG_POST_BAD_REQUEST,

    EMAIL_ALREADY_IN_USE,
    INVALID_RESULT_VALUE,
    MISSING_COUNTRY_VALUE,
    MISSING_CITY_VALUE,

    FIELD_CANNOT_BE_BLANK,
    FIELD_CANNOT_BE_NULL,
    INVALID_ROLE_VALUE,

    SWEAR_WORDS_FILTER_MESSAGE_COMMENT,
    SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST
}
