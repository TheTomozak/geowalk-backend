package com.example.geowalk.utils.messages;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.geowalk.utils.messages.MessageKeys.*;

@Component
public class MessagesUtil {

    private Map<MessageKeys, String> messageDictionary;

    public MessagesUtil() {
        this.messageDictionary = new HashMap<>() {{
            put(LOGGER_GET_USER_FAILED, "Get user failed: ");
            put(LOGGER_CREATE_USER_FAILED, "Create user failed: ");
            put(LOGGER_UPDATE_USER_FAILED, "Update user failed: ");
            put(LOGGER_DELETE_USER_FAILED, "Delete user failed: ");

            put(LOGGER_GET_POST_FAILED, "Getting posts failed: ");
            put(LOGGER_CREATE_POST_FAILED, "Creating post failed: ");
            put(LOGGER_UPDATE_POST_FAILED, "Updating post failed: ");
            put(LOGGER_VERIFY_POST_FAILED, "Verifying post failed: ");

            put(LOGGER_GET_COMMENT_FAILED, "Getting comments failed: ");
            put(LOGGER_CREATE_COMMENT_FAILED, "Creating comment failed: ");
            put(LOGGER_UPDATE_COMMENT_FAILED, "Updating comment failed: ");
            put(LOGGER_DELETE_COMMENT_FAILED, "Deleting comment failed: ");
            put(LOGGER_VERIFY_COMMENT_FAILED, "Verifying comment failed: ");

            put(LOGGER_GET_TRAVEL_STOP_FAILED, "Getting travel stops failed: ");
            put(LOGGER_CREATE_TRAVEL_STOP_FAILED, "Creating travel stops failed: ");

            put(LOGGER_CREATE_TRAVEL_ROUTE_FAILED, "Creating travel route failed");

            put(USER_NOT_FOUND_ID, "User with given id not found");
            put(USER_NOT_FOUND_EMAIL, "User with given email not found");
            put(USER_NOT_AUTHORIZED, "User is not authenticated/authorized");
            put(USER_BLOCKED_OR_DELETED, "User already deleted/blocked");

            put(BLOG_COMMENT_NOT_FOUND, "Blog comment with given id not found");
            put(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER, "User cannot delete comment others users");
            put(BLOG_COMMENT_INVALID_RATING_VALUE, "Rating value is invalid");

            put(BLOG_POST_NOT_FOUND, "BlogPost with given id not found");
            put(BLOG_POST_BAD_REQUEST, "BlogPost request has bad body");

            put(EMAIL_ALREADY_IN_USE, "Email is already in use");
            put(INVALID_RESULT_VALUE, "Invalid result value");
            put(MISSING_COUNTRY_VALUE, "Missing country value");
            put(MISSING_CITY_VALUE, "Missing city value");

            put(FIELD_CANNOT_BE_BLANK, "Field cannot be empty");
            put(FIELD_CANNOT_BE_NULL, "Field cannot be null");

            put(SWEAR_WORDS_FILTER_MESSAGE_COMMENT, "Comment moved to verification due to profanity");
            put(SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST, "Blog post moved to verification due to profanity");
        }};
    }

    public Map<MessageKeys, String> getDict() {
        return messageDictionary;
    }
}