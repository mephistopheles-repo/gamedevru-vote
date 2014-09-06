package ru.gamedev.vote.services;

import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gamedev.vote.models.PageDTO;
import ru.gamedev.vote.test.AbstractTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrawlerServiceTest extends AbstractTest {

    @Autowired
    CrawlerService crawlerService;

    @Autowired
    ParserService parserService;

    @Test
    //test detect weather on mars, enable brain to use this
    @Ignore
    public void testGetPage() throws Exception {
        Document doc = crawlerService.getPage(192900l);
        PageDTO page = parserService.parse(doc);
        assertTrue(page.getIsFirstPage());

        doc = crawlerService.getPage(192900l, 2l);
        page = parserService.parse(doc);
        assertFalse(page.getIsFirstPage());
    }

    @Test
    public void testIdParser() {
        assertTrue(parserService.parseId("http://www.gamedev.ru/flame/forum/?id=192900&page=4") == 192900l);
        assertTrue(parserService.parseId("http://www.gamedev.ru/flame/forum/?id=192900") == 192900l);
        assertTrue(parserService.parseId("http://www.gamedev.ru/flame/forum/?page=4&id=192900") == 192900l);
    }
}