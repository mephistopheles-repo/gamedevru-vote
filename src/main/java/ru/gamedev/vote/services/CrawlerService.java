package ru.gamedev.vote.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CrawlerService {
    public static final String URL = "http://www.gamedev.ru/flame/forum/?id=";
    public static final String PAGER_PARAM = "&page=";

    public Document getPage(Long id) throws IOException {
        return getPage(id, 1l);
    }

    public Document getPage(Long id, Long page) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        sb.append(id);
        sb.append(PAGER_PARAM);
        sb.append(page);

        return Jsoup.connect(sb.toString()).get();
    }
}
