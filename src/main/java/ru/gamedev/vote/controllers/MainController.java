package ru.gamedev.vote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.gamedev.vote.exeptions.PermissionException;
import ru.gamedev.vote.models.VotingDTO;
import ru.gamedev.vote.services.CrawlerService;
import ru.gamedev.vote.services.ParserService;
import ru.gamedev.vote.services.VoteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MainController {

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected ParserService parserService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getMainPage() {
        return new ModelAndView("indexPage");
    }

    @RequestMapping(value = "/vote-page", method = RequestMethod.GET)
    public ModelAndView getVote(@RequestParam String url,
                                @RequestParam(required = false, defaultValue = "false") Boolean skipCheck) throws IOException, PermissionException {

        if (!skipCheck && !url.startsWith(CrawlerService.URL)) {
            throw new PermissionException();
        }

        Long threadId = parserService.parseId(url);

        ModelAndView mav = new ModelAndView("votePage");

        VotingDTO dto = voteService.getVoting(threadId);
        mav.addObject("voting", null);

        return mav;
    }

    @ExceptionHandler(PermissionException.class)
    private void permissionHandler(HttpServletRequest request, HttpServletResponse response, PermissionException ex) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
