package com.example.masterplanbbe.application.batch.job;

import com.example.masterplanbbe.infrastructure.repository.SpecJmcdBatchRepository;
import com.example.masterplanbbe.infrastructure.util.JmcdApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SpecJmcdScheduler {
    private final SpecJmcdBatchRepository specJmcdBatchRepository;
    private final JmcdApiUtil jmcdApiUtil;

//    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void updateSpecJmcd() throws ParserConfigurationException, IOException, SAXException {
        specJmcdBatchRepository.batchUpsert(jmcdApiUtil.fetchAndParseItems());
    }

}
