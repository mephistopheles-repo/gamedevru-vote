package ru.gamedev.vote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.gamedev.vote.exeptions.PermissionException;
import ru.gamedev.vote.models.ColorDTO;
import ru.gamedev.vote.models.VoteChoice;
import ru.gamedev.vote.models.VotingDTO;
import ru.gamedev.vote.services.CrawlerService;
import ru.gamedev.vote.services.ParserService;
import ru.gamedev.vote.services.VoteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @RequestMapping(value = "/stub-page", method = RequestMethod.GET)
    public ModelAndView getStubPage(@RequestParam(required = false, defaultValue = "F8F8FF") String text,
                                    @RequestParam(required = false, defaultValue = "C7C7C7") String back,
                                    @RequestParam(required = false, defaultValue = "239723") String complete) {

        ModelAndView mav = new ModelAndView("votePage");
        mav.addObject("votingLength", 0);
        mav.addObject("color", new ColorDTO(text, back, complete));
        mav.addObject("isStubPage", true);
        return mav;
    }

    @RequestMapping(value = "/vote-page", method = RequestMethod.GET)
    public ModelAndView getVote(@RequestParam String url,
                                @RequestParam(required = false, defaultValue = "false") Boolean skipCheck,
                                @RequestParam(required = false, defaultValue = "F8F8FF") String text,
                                @RequestParam(required = false, defaultValue = "C7C7C7") String back,
                                @RequestParam(required = false, defaultValue = "239723") String complete) throws IOException, PermissionException {

        url = URLDecoder.decode(url, "UTF-8");

        if (!skipCheck && !url.startsWith(CrawlerService.URL)) {
            throw new PermissionException();
        }

        Long threadId = parserService.parseId(url);

        ModelAndView mav = new ModelAndView("votePage");

        VotingDTO dto = voteService.getVoting(threadId);
        if (dto != null) {
            int fullCount = 0;
            List<VoteChoice> list = new ArrayList<VoteChoice>();
            for (String voteChoice : dto.getChoices().keySet()) {
                VoteChoice innerChoice = dto.getChoices().get(voteChoice);
                list.add(innerChoice);
                fullCount += innerChoice.getCount();
            }

            Collections.sort(list, new Comparator<VoteChoice>() {
                @Override
                public int compare(VoteChoice o1, VoteChoice o2) {
                    return o2.getCount().compareTo(o1.getCount());
                }
            });

            if (list.size() > 5) {
                list = list.subList(0, 5);
            }

            Long viewCount = 0l;
            for (VoteChoice voteChoice : list) {
                viewCount += voteChoice.getCount();
            }
            Long otherCount = fullCount - viewCount;
            if (otherCount != 0) {
                VoteChoice others = new VoteChoice();
                others.setChoice("Другие варианты");
                others.setCount(otherCount);
                list.add(others);
            }

            mav.addObject("fullCount", fullCount);
            mav.addObject("voting", list);
            mav.addObject("votingLength", list.size());
        } else {
            mav.addObject("votingLength", 0);
        }

        mav.addObject("color", new ColorDTO(text, back, complete));
        return mav;
    }

    @ExceptionHandler(PermissionException.class)
    private void permissionHandler(HttpServletRequest request, HttpServletResponse response, PermissionException ex) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
