package com.example.masterplanbbe.presentation.response;

import java.util.List;

public record JmcdApiResponse(

) {
    public record Response(
            Header header,
            Body body
    ) {
    }

    public record Header(
            String resultCode,
            String resultMessage
    ) {
    }

    public record Body(
            List<Item> items
    ) {
    }

    public record Item(
            Integer jmcd,
            String specName
    ) {
    }
}
