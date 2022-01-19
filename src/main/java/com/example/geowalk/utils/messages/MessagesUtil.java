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
            put(LOGGER_ASSIGN_ROLE_FAILED, "Assign user role failed: ");

            put(LOGGER_GET_POST_FAILED, "Getting posts failed: ");
            put(LOGGER_CREATE_POST_FAILED, "Creating post failed: ");
            put(LOGGER_UPDATE_POST_FAILED, "Updating post failed: ");
            put(LOGGER_VERIFY_POST_FAILED, "Verifying post failed: ");
            put(LOGGER_DELETE_POST_FAILED, "Deleting post failed: ");

            put(LOGGER_GET_COMMENT_FAILED, "Getting comments failed: ");
            put(LOGGER_CREATE_COMMENT_FAILED, "Creating comment failed: ");
            put(LOGGER_UPDATE_COMMENT_FAILED, "Updating comment failed: ");
            put(LOGGER_DELETE_COMMENT_FAILED, "Deleting comment failed: ");
            put(LOGGER_VERIFY_COMMENT_FAILED, "Verifying comment failed: ");

            put(LOGGER_GET_TRAVEL_STOP_FAILED, "Getting travel stops failed: ");
            put(LOGGER_CREATE_TRAVEL_STOP_FAILED, "Creating travel stops failed: ");

            put(LOGGER_CREATE_TRAVEL_ROUTE_FAILED, "Creating travel route failed");

            put(USER_NOT_FOUND_ID, "Nie znaleziono użytkownika o podanym identyfikatorze");
            put(USER_NOT_FOUND_EMAIL, "Nie znaleziono użytkownika o podanym emailu");
            put(USER_NOT_AUTHORIZED, "Brak uprawnień");
            put(USER_BLOCKED_OR_DELETED, "Użytkownik jest zablokowany/usunięty");

            put(BLOG_COMMENT_NOT_FOUND, "Nie znaleziono komentarza o podanym identyfikatorze");
            put(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER, "Użytkownik nie może usuwać komentarza innych użytkowników");
            put(BLOG_COMMENT_INVALID_RATING_VALUE, "Nieprawidłowa wartość oceny");

            put(BLOG_POST_NOT_FOUND, "Nie znaleziono postu o podanym identyfikatorze");
            put(BLOG_POST_BAD_REQUEST, "Niepoprawne wypełnione pola dla postu");

            put(EMAIL_ALREADY_IN_USE, "Podany email jest już zajęty");
            put(INVALID_RESULT_VALUE, "Niepoprawny wynik resultu");
            put(MISSING_COUNTRY_VALUE, "Brakuje wartości parametru country");
            put(MISSING_CITY_VALUE, "Brakuje wartości parametru city");

            put(FIELD_CANNOT_BE_BLANK, "Pole nie może być puste");
            put(FIELD_CANNOT_BE_NULL, "Pole nie może być puste");
            put(INVALID_ROLE_VALUE, "Niepoprawna wartość roli");

            put(SWEAR_WORDS_FILTER_MESSAGE_COMMENT, "Komentarz przeniesiono do weryfikacji z powodu potencjalnych wulgaryzmów");
            put(SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST, "Post przeniesiono do weryfikacji z powodu potencjalnych wulgaryzmów");
        }};
    }

    public Map<MessageKeys, String> getDict() {
        return messageDictionary;
    }
}