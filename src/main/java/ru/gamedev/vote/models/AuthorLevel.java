package ru.gamedev.vote.models;

import ru.gamedev.vote.exeptions.AuthorLevelUnknownException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
public enum AuthorLevel {
    NOOBIE("НОВИЧОК"),
    USER("ПОЛЬЗОВАТЕЛЬ"),
    OLD_USER("ПОСТОЯЛЕЦ"),
    DONATOR("УЧАСТНИК"),
    MODERATOR("МОДЕРАТОР"),
    WAT("ВЕДУЩИЙ"),
    BANNED("ЗАБАНЕН"),
    REMOVED("УДАЛЕН");
    private static Map<String, AuthorLevel> map = new HashMap<String, AuthorLevel>();

    static {
        AuthorLevel[] authorLevels = AuthorLevel.values();
        for (AuthorLevel authorLevel : authorLevels) {
            map.put(authorLevel.getTextName(), authorLevel);
        }
    }

    private String textName;

    private AuthorLevel(String textName) {
        this.textName = textName;
    }

    public static AuthorLevel fromString(String text) throws AuthorLevelUnknownException {
        text = text.toUpperCase();
        if (map.containsKey(text)) {
            return map.get(text);
        }
        throw new AuthorLevelUnknownException();
    }

    private String getTextName() {
        return textName;
    }
}
