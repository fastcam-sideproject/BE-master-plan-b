package com.example.masterplanbbe.domain.infrastructure.util;

import com.example.masterplanbbe.infrastructure.util.JmcdApiUtil;
import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "jmcd.service-key=@Value(\"${jmcd.serviceKey}\")"
})
public class JmcdApiUtilTest {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${jmcd.serviceKey}")
    private String serviceKey;

    private JmcdApiUtil jmcdApiUtil;

    @BeforeEach
    void setUp() {
        jmcdApiUtil = new JmcdApiUtil(webClientBuilder, serviceKey);
    }

    @Test
    void fetch_and_parse_items() throws ParserConfigurationException, IOException, SAXException {
        List<JmcdApiResponse.Item> items = jmcdApiUtil.fetchAndParseItems();

        assertNotNull(items);
        assertFalse(items.isEmpty());

        // 디버깅용 출력
        items.stream()
                .limit(3)
                .forEach(item -> System.out.println(item.jmcd() + " : " + item.specName()));
    }
}
