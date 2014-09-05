package ru.gamedev.vote.services;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
@Service
public class VoteService {
    private ScheduledExecutorService executorService;

    @PostConstruct
    private void postConstruct(){
        executorService = Executors.newScheduledThreadPool(3);
    }

}
