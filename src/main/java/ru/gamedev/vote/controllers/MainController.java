package ru.gamedev.vote.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.gamedev.vote.exeptions.PermissionException;
import ru.gamedev.vote.services.CrawlerService;
import ru.gamedev.vote.services.ParserService;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getMainPage(){
        return new ModelAndView("indexPage");
    }

    @RequestMapping(value = "/vote-page", method = RequestMethod.GET)
    public ModelAndView getVote(@RequestParam String url) throws IOException, PermissionException {

        if (!url.startsWith(CrawlerService.URL)) {
            throw new PermissionException();
        }


        ModelAndView mav = new ModelAndView("votePage");


        return mav;
    }

    @ExceptionHandler(PermissionException.class)
    private void permissionHandler(HttpServletRequest request, HttpServletResponse response, PermissionException ex) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
