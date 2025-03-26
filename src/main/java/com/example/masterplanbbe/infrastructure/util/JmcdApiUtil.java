package com.example.masterplanbbe.infrastructure.util;

import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RequiredArgsConstructor
public class JmcdApiUtil {

    private WebClient.Builder webClientBuilder;

    public List<JmcdApiResponse.Item> fetchAndParseItems() throws ParserConfigurationException, SAXException, IOException {
        Flux<DataBuffer> flux = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/jmcd")
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
