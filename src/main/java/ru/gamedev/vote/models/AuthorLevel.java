package ru.gamedev.vote.models;

import ru.gamedev.vote.exeptions.AuthorLevelUnknownException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
public enum AuthorLevel {
    NOOBIE("НОВИЧОК", 1),
    USER("ПОЛЬЗОВАТЕЛЬ", 2),
    OLD_USER("ПОСТОЯЛЕЦ", 4),
    DONATOR("УЧАСТНИК", 8),
    MODERATOR("МОДЕРАТОР", 16),
    WAT("ВЕДУЩИЙ", 32),
    BANNED("ЗАБАНЕН", 64),
    REMOVED("УДАЛЕН", 128);
    private static Map<String, AuthorLevel> map = new HashMap<String, AuthorLevel>();

    static {
        AuthorLevel[] authorLevels = AuthorLevel.values();
        for (AuthorLevel authorLevel : authorLevels) {
            map.put(authorLevel.getTextName(), authorLevel);
        }
    }

    private String textName;
    private int intLevel;

    private AuthorLevel(String textName, int intLevel) {
        this.textName = textName;
        this.intLevel = intLevel;
    }

    public static AuthorLevel fromString(String text) throws AuthorLevelUnknownException {
        text = text.toUpperCase();
        if (map.containsKey(text)) {
            return map.get(text);
        }
        throw new AuthorLevelUnknownException();
    }

    public static List<AuthorLevel> listFromMask(int mask) {
        List<AuthorLevel> list = new ArrayList<AuthorLevel>();

        for (Map.Entry<String, AuthorLevel> levelEntry : map.entrySet()) {
            AuthorLevel authorLevel = levelEntry.getValue();
            if ((mask & authorLevel.getIntLevel()) != 0) {
                list.add(levelEntry.getValue());
            }
        }

        return list;
    }

    public static List<AuthorLevel> listFromNamesList(List<String> names) {
        List<AuthorLevel> list = new ArrayList<AuthorLevel>();

        for (String name : names) {
            name = name.toUpperCase();
            if (map.containsKey(name)) {
                list.add(map.get(name));
            }
        }

        return list;
    }

    public String getTextName() {
        return textName;
    }

    public int getIntLevel() {
        return intLevel;
    }
}
