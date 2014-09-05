package ru.gamedev.vote.services;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ParserService {
    private static  final String MESSAGE_SELECTOR = "div.mes";
    private static  final String MESSAGE_HEADER_SELECTOR = MESSAGE_SELECTOR + " table.mes";
    private static  final String AUTHOR_ELEMENT_SELECTOR = MESSAGE_HEADER_SELECTOR + " th a";
    private static  final String AUTHOR_LEVEL_SELECTOR = MESSAGE_HEADER_SELECTOR + " .level";
    private static  final String MESSAGE_BODY_SELECTOR = MESSAGE_SELECTOR + " .block";
    private Pattern votePattern;

    @PostConstruct
    private void postConstruct(){
        votePattern = Pattern.compile("\\@vote (.*)");
    }

}
