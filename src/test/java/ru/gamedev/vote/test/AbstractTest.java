package ru.gamedev.vote.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.gamedev.vote.services.CrawlerService;
import ru.gamedev.vote.services.ParserService;
import ru.gamedev.vote.services.VoteService;

/**
 * Created with IntelliJ IDEA.
 * Date: 06.09.2014
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public abstract class AbstractTest {

    @Autowired
    protected CrawlerService crawlerService;

    @Autowired
    protected ParserService parserService;

    @Autowired
    protected VoteService voteService;

}
