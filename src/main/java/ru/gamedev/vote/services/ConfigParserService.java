package ru.gamedev.vote.services;

import org.springframework.stereotype.Service;
import ru.gamedev.vote.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.09.2014
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConfigParserService {
    private static final String CONFIG_MARKER = "@config";
    private static final String WORD_FILTER_MARKER = "wordFilter:";
    private static final String USER_FILTER_MARKER = "userFilter:";
    private static final String LEVEL_FILTER_MARKER = "levelFilter:";
    private static final String FILTER_DELIMITER = ",";
    private static final String CONFIG_END = ";";

    public ConfigDTO parse(String text) {
        ConfigDTO dto = new ConfigDTO();
        int configIndx = text.indexOf(CONFIG_MARKER);
        if (configIndx != -1) {
            int configStartIndx = text.indexOf("{", configIndx);
            int configEndIndex = text.indexOf("}", configIndx);
            if (validateConfigIndexesPos(configStartIndx, configEndIndex)) {
                dto.setMessageFilter(parseFilters(text, configStartIndx, configEndIndex));
            }
        }
        return dto;
    }

    private boolean validateConfigIndexesPos(int start, int end) {
        return (start > 0 && end > 0 && end > start);
    }

    private MessageFilter parseFilters(String text, int start, int end) {
        FilterCollection messageFilter = new FilterCollection();

        String levelFilterString = parseFilter(text, LEVEL_FILTER_MARKER, start, end);
        if (levelFilterString != null) {
            String[] filters = levelFilterString.split(FILTER_DELIMITER);
            boolean isExcludeFilter = extractFilterType(filters);
            List<AuthorLevel> filterList = AuthorLevel.listFromNamesList(Arrays.asList(filters));
            if (filterList.size() > 0) {
                messageFilter.setLevelFilter(new LevelFilter(isExcludeFilter, filterList));
            }
        }

        String userFilterString = parseFilter(text, USER_FILTER_MARKER, start, end);
        if (userFilterString != null) {
            String[] filters = userFilterString.split(FILTER_DELIMITER);
            boolean isExcludeFilter = extractFilterType(filters);
            List<Long> filterList = new ArrayList<Long>();
            for (String filter : filters) {
                Long id = Long.parseLong(filter);
                filterList.add(id);
            }
            if (filterList.size() > 0) {
                messageFilter.setUserFilter(new UserFilter(isExcludeFilter, filterList));
            }
        }

        String wordFilterString = parseFilter(text, WORD_FILTER_MARKER, start, end);

        if (wordFilterString != null) {
            String[] filters = wordFilterString.split(FILTER_DELIMITER);
            boolean isExcludeFilter = extractFilterType(filters);
            List<String> filterList = Arrays.asList(filters);
            if (filterList.size() > 0) {
                messageFilter.setWordFilter(new WordFilter(isExcludeFilter, filterList));
            }
        }

        return messageFilter;
    }

    private boolean extractFilterType(String[] strings) {
        boolean isExcludeFilter = strings[0].charAt(0) == '-';
        if (strings[0].charAt(0) == '+' || strings[0].charAt(0) == '-') {
            strings[0] = strings[0].substring(1);
        }
        return isExcludeFilter;
    }

    private String parseFilter(String text, String filter, int start, int end) {
        int filterStart = text.indexOf(filter, start);
        if (filterStart != -1) {
            int filterEnd = text.indexOf(CONFIG_END, filterStart);
            if (validateConfigIndexesPos(filterStart, filterEnd) && filterEnd < end) {
                return text.substring(filterStart + filter.length(), filterEnd).trim();
            }
        }

        return null;
    }
}
