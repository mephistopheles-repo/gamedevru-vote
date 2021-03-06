package ru.gamedev.vote.services;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gamedev.vote.models.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
@Service
public class VoteService {
    private static final long UPDATE_INTERVAL = 1000 * 60 * 2;
    //private static final long UPDATE_INTERVAL = 0;
    private static final long CLEAR_INTERVAL = 1000 * 60 * 15;

    @Autowired
    protected CrawlerService crawlerService;

    @Autowired
    protected ParserService parserService;

    private ScheduledExecutorService executorService;
    private ConcurrentMap<Long, VotingDTO> votingDTOMap;
    private ConcurrentMap<Long, Object> runningUpdates;

    @PostConstruct
    private void postConstruct() {
        executorService = Executors.newScheduledThreadPool(3);
        votingDTOMap = new ConcurrentHashMap<Long, VotingDTO>();
        runningUpdates = new ConcurrentHashMap<Long, Object>();
    }

    @Scheduled(fixedDelay = CLEAR_INTERVAL)
    private void clearCache() {
        Long now = new Date().getTime();
        for (Long key : votingDTOMap.keySet()) {
            VotingDTO dto = votingDTOMap.get(key);
            if (dto.getLastUpdated().getTime() + CLEAR_INTERVAL < now) {
                votingDTOMap.remove(key);
            }
        }
    }

    public VotingDTO getVoting(final Long id) {
        VotingDTO dto = null;
        Long lastUpdated = 0l;
        if (votingDTOMap.containsKey(id)) {
            dto = votingDTOMap.get(id);
            lastUpdated = dto.getLastUpdated().getTime();
        }

        Long current = new Date().getTime();
        if (lastUpdated + UPDATE_INTERVAL < current) {

            if (!runningUpdates.containsKey(id)) {
                synchronized (runningUpdates) {
                    if (!runningUpdates.containsKey(id)) {
                        runningUpdates.put(id, new Object());
                    } else {
                        return dto;
                    }
                }
            } else {
                return dto;
            }


            executorService.schedule(new Runnable() {
                private Long tryCount = 0l;
                private final Long maxTryes = 10l;
                private final Long tryInterval = 10000l;

                @Override
                public void run() {
                    long t = new Date().getTime();
                    try {
                        Document doc;
                        try {
                            doc = crawlerService.getPage(id);
                        } catch (IOException e) {
                            if (++tryCount < maxTryes) {
                                executorService.schedule(this, tryInterval, TimeUnit.MILLISECONDS);
                            }
                            return;
                        }
                        PageDTO pageDTO = parserService.parse(doc);
                        ConfigDTO config = pageDTO.getConfigDTO();
                        VotingDTO tempVotingDTO = collectResult(pageDTO);
                        VotingDTO votingDTO = new VotingDTO();
                        aggregateData(votingDTO, tempVotingDTO);
                        if (!pageDTO.getIsLastPage()) {
                            Long pageCounter = 2l;
                            while (true) {
                                try {
                                    doc = crawlerService.getPage(id, pageCounter);
                                } catch (IOException ignored) {
                                    break;
                                }

                                pageDTO = parserService.parse(doc);
                                pageDTO.setConfigDTO(config);
                                tempVotingDTO = collectResult(pageDTO);
                                aggregateData(votingDTO, tempVotingDTO);
                                pageCounter++;
                                if (pageDTO.getIsLastPage() || pageCounter > 1000) {
                                    break;
                                }
                            }
                        }

                        votingDTO.setVoteId(id);
                        votingDTO.setLastUpdated(new Date());
                        if (votingDTOMap.containsKey(id)) {
                            votingDTOMap.replace(id, votingDTO);
                        } else {
                            votingDTOMap.put(id, votingDTO);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        runningUpdates.remove(id);
                    }
                    System.out.println(new Date().getTime() - t);
                }
            }, 0, TimeUnit.MILLISECONDS);
        }
        return dto;
    }

    private VotingDTO collectResult(PageDTO page) {
        VotingDTO dto = new VotingDTO();
        List<VoteDTO> votes = page.getMessageDTOList();
        Map<String, VoteChoice> map = dto.getChoices();
        MessageFilter messageFilter = page.getConfigDTO().getMessageFilter();
        for (VoteDTO vote : votes) {
            if (vote.getVote() == null) {
                continue;
            }

            if (!messageFilter.filter(vote)) {
                continue;
            }

            VoteChoice choice = new VoteChoice();
            if (map.containsKey(vote.getVote())) {
                choice = map.get(vote.getVote());
            } else {
                choice.setChoice(vote.getVote());
                map.put(vote.getVote(), choice);
            }

            choice.getVoters().add(vote);
            choice.addCount(1l);
        }

        return dto;
    }

    private VotingDTO aggregateData(VotingDTO dest, VotingDTO source) {
        Map<String, VoteChoice> destMap = dest.getChoices();
        Map<String, VoteChoice> sourceMap = source.getChoices();

        for (String sourceChoiceKey : sourceMap.keySet()) {
            VoteChoice sourceChose = sourceMap.get(sourceChoiceKey);

            VoteChoice destChoice;
            if (destMap.containsKey(sourceChoiceKey)) {
                destChoice = destMap.get(sourceChoiceKey);

                List<VoteDTO> destVoters = destChoice.getVoters();
                List<VoteDTO> sourceVoters = sourceMap.get(sourceChoiceKey).getVoters();

                for (VoteDTO voteDTO : sourceVoters) {
                    if (!destVoters.contains(voteDTO)) {
                        destVoters.add(voteDTO);
                        destChoice.addCount(1l);
                    }
                }
            } else {
                destChoice = sourceChose;
                destChoice.setChoice(sourceChoiceKey);
                destMap.put(sourceChoiceKey, destChoice);
            }
        }

        return dest;
    }
}
