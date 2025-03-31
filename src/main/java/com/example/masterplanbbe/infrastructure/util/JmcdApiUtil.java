package com.example.masterplanbbe.infrastructure.util;

import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.SAXException;
import reactor.core.publisher.Flux;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

public class JmcdApiUtil {
    private final WebClient.Builder webClientBuilder;
    private final String serviceKey;

    private final String jmcdApiUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryListNationalQualifcationSVC/getList";

    public JmcdApiUtil(WebClient.Builder webClientBuilder, @Value("${jmcd.serviceKey}") String serviceKey) {
        this.webClientBuilder = webClientBuilder;
        this.serviceKey = serviceKey;
    }

    public List<JmcdApiResponse.Item> fetchAndParseItems() throws ParserConfigurationException, SAXException, IOException {
        StringBuilder urlBuilder = new StringBuilder(jmcdApiUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey);

        Flux<DataBuffer> flux = webClientBuilder.build()
                .get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        InputStream xmlInputStream = DataBufferUtils.join(flux)
                .map(dataBuffer -> {
                    try {
                        return dataBuffer.asInputStream(true);
                    } catch (Exception e){
                        throw new RuntimeException("failed to convert DataBuffer to InputStream", e);
                    }
                })
                .block();

        if (xmlInputStream == null) {
            throw new RuntimeException("failed to fetch items");
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        JmcdItemHandler handler = new JmcdItemHandler();

        saxParser.parse(xmlInputStream, handler);

        return handler.getItems();
    }

}
