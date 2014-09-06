package ru.gamedev.vote.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.gamedev.vote.exeptions.AuthorLevelUnknownException;
import ru.gamedev.vote.models.AuthorLevel;
import ru.gamedev.vote.models.PageDTO;
import ru.gamedev.vote.models.VoteDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ParserService {
    public static final String ID_MARK = "id=";
    private static final String MESSAGE_SELECTOR = "div.mes";
    private static final String MESSAGE_HEADER_SELECTOR = "table.mes";
    private static final String AUTHOR_ELEMENT_SELECTOR = "th a";
    private static final String AUTHOR_LEVEL_SELECTOR = ".level";
    private static final String MESSAGE_BODY_SELECTOR = ".block";
    private static final String ZERO_POST_SELECTOR = "#m0";
    private static final String PAGINATION_SELECTOR = "#main_body p a";
    private static final String NEXT_PAGE_BUTTON_TEXT = "Следующая";
    private static final String QUOTE_SELECTOR = ".q";
    private static final String POTENCIAL_VOTE_ELEMENT = "p";
    private static final String VOTE_MARK = "@vote";

    public PageDTO parse(Document doc) {
        Elements messagesOnPage = doc.select(MESSAGE_SELECTOR);
        PageDTO page = new PageDTO();
        page.setIsFirstPage(!messagesOnPage.select(ZERO_POST_SELECTOR).isEmpty());
        page.setIsLastPage(isLastPage(doc));

        List<VoteDTO> messageDTOList = new ArrayList<VoteDTO>(messagesOnPage.size());
        for (Element element : messagesOnPage) {
            Element messageHeader = element.select(MESSAGE_HEADER_SELECTOR).first();
            VoteDTO dto = new VoteDTO();
            Element authorEl = messageHeader.select(AUTHOR_ELEMENT_SELECTOR).first();
            dto.setAuthorId(parseId(authorEl.attr("href")));
            dto.setAuthorName(authorEl.text());
            Element authorLevel = messageHeader.select(AUTHOR_LEVEL_SELECTOR).first();
            dto.setAuthorLevel(parseAuthorLevel(authorLevel.text()));

            Element body = element.select(MESSAGE_BODY_SELECTOR).first();
            body.select(QUOTE_SELECTOR).remove();
            Elements paragraphsElements = body.select(POTENCIAL_VOTE_ELEMENT);
            if (paragraphsElements.size() == 0) {
                dto.setVote(parseVote(body.text()));
            } else {
                for (Element paragraphsElement : paragraphsElements) {
                    String vote = parseVote(paragraphsElement.text());
                    if (vote != null) {
                        dto.setVote(vote);
                        break;
                    }
                }
            }
            messageDTOList.add(dto);
        }

        messagesOnPage.clear();

        page.setMessageDTOList(messageDTOList);
        return page;
    }

    private String parseVote(String body) {
        int start = body.indexOf(VOTE_MARK);
        if (start == -1) {
            return null;
        }
        int offset = start + VOTE_MARK.length();
        if (body.length() < offset) {
            return null;
        }
        int end = body.indexOf("\n", offset);
        String returnString;
        if (end == -1) {
            returnString = body.substring(offset).trim();
        } else {
            returnString = body.substring(offset, end).trim();
        }

        if (returnString.length() > 48) {
            returnString = returnString.substring(0, 48);
        }
        return returnString;
    }

    private AuthorLevel parseAuthorLevel(String level) {
        try {
            return AuthorLevel.fromString(level);
        } catch (AuthorLevelUnknownException e) {
            return AuthorLevel.REMOVED;
        }
    }

    public Long parseId(String url) {
        int start = url.indexOf(ID_MARK);
        if (start == -1) {
            return 0l;
        }

        int startOffset = start + ID_MARK.length();
        int end = url.indexOf("&", startOffset);
        if (end == -1) {
            end = url.length();
        }

        if (startOffset == end) {
            return 0l;
        }

        return Long.valueOf(url.substring(startOffset, end).trim());
    }


    private boolean isLastPage(Document doc) {
        Elements elements = doc.select(PAGINATION_SELECTOR);
        for (Element element : elements) {
            if (element.text().equals(NEXT_PAGE_BUTTON_TEXT)) {
                return false;
            }
        }
        return true;
    }
}
