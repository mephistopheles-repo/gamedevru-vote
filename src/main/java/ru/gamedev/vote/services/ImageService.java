package ru.gamedev.vote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gamedev.vote.models.ColorDTO;
import ru.gamedev.vote.models.ImageDTO;
import ru.gamedev.vote.models.VoteChoice;
import ru.gamedev.vote.models.VotingDTO;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * Date: 07.09.2014
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ImageService {
    private static final int IMAGE_WIGHT = 300;
    private static final int PIXEL_PER_ANSWER = 20;
    private static final int ANSWER_INTERVAL = 1;
    private static final int STUB_IMAGE_HEIGHT = 100;
    private static final int CLEAR_CACHE_INTERVAL = 1000 * 60 * 1;

    private ByteArrayOutputStream stubImage;

    private ConcurrentMap<Long, ImageDTO> imageCache;

    @Scheduled(fixedDelay = CLEAR_CACHE_INTERVAL)
    private void clearCache() {
        long now = new Date().getTime();
        for (Long key : imageCache.keySet()) {
            ImageDTO dto = imageCache.get(key);
            if (dto.getLastUpdated().getTime() + CLEAR_CACHE_INTERVAL < now) {
                imageCache.remove(key);
            }
        }
    }

    @Autowired
    protected VoteService voteService;

    @PostConstruct
    private void postConstruct() {
        imageCache = new ConcurrentHashMap<Long, ImageDTO>();
        BufferedImage bi = new BufferedImage(IMAGE_WIGHT, STUB_IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bi.createGraphics();
        g.setPaint(new Color(0xD4D4D4));
        g.fillRect(0, 0, IMAGE_WIGHT, STUB_IMAGE_HEIGHT);
        g.setPaint(Color.BLACK);
        g.drawString("Данные обрабатываются...Обновите страницу.", 20, 50);
        stubImage = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "PNG", stubImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ByteArrayOutputStream getVoteAsImage(Long id, int size, ColorDTO color) {
        VotingDTO votingDTO = voteService.getVoting(id);
        return votingDTO == null ? stubImage : getImage(votingDTO, size, color);
    }

    private ByteArrayOutputStream getImage(VotingDTO votingDTO, int size, ColorDTO color) {
        if (!imageCache.containsKey(votingDTO.getVoteId())) {
            int fullCount = 0;
            List<VoteChoice> list = new ArrayList<VoteChoice>();
            for (String voteChoice : votingDTO.getChoices().keySet()) {
                VoteChoice innerChoice = votingDTO.getChoices().get(voteChoice);
                list.add(innerChoice);
                fullCount += innerChoice.getCount();
            }

            Collections.sort(list, new Comparator<VoteChoice>() {
                @Override
                public int compare(VoteChoice o1, VoteChoice o2) {
                    return o2.getCount().compareTo(o1.getCount());
                }
            });

            if (list.size() > size) {
                list = list.subList(0, size);
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

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setImageData(drawImage(list, color, fullCount));
            imageDTO.setLastUpdated(new Date());
            imageCache.put(votingDTO.getVoteId(), imageDTO);
        }

        return imageCache.get(votingDTO.getVoteId()).getImageData();
    }

    private ByteArrayOutputStream drawImage(List<VoteChoice> list, ColorDTO color, int fullCount) {
        Color backColor = new Color(Integer.parseInt(color.getBack(), 16));
        Color completeColor = new Color(Integer.parseInt(color.getComplete(), 16));
        Color textColor = new Color(Integer.parseInt(color.getText(), 16));

        int imageHeight = list.size() * (PIXEL_PER_ANSWER + ANSWER_INTERVAL);

        BufferedImage bi = new BufferedImage(IMAGE_WIGHT, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bi.createGraphics();
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, IMAGE_WIGHT, imageHeight);
        for (int i = 0; i < list.size(); i++) {
            int y = i * (ANSWER_INTERVAL + PIXEL_PER_ANSWER);
            g.setPaint(backColor);
            g.fillRect(0, y, IMAGE_WIGHT, PIXEL_PER_ANSWER);
            int wight = (int) (IMAGE_WIGHT / fullCount * list.get(i).getCount());
            g.setPaint(completeColor);
            g.fillRect(0, y, wight, PIXEL_PER_ANSWER);

            g.setPaint(textColor);
            g.drawString(list.get(i).getChoice(), 5, y + 15);

            g.drawString(list.get(i).getCount().toString(), IMAGE_WIGHT - 30, y + 15);
        }

        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            ImageIO.write(bi, "PNG", result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
