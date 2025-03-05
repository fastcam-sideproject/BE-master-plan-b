package com.example.masterplanbbe.common.util;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import io.jsonwebtoken.lang.Assert;

import java.util.List;
import java.util.function.LongSupplier;

public abstract class CustomPageUtils {
    private CustomPageUtils() {
    }

    public static <T> CustomPage<T> getPage(List<T> content, CustomPageRequest pageRequest, LongSupplier totalSupplier) {
        Assert.notNull(content, "content must not be null");
        Assert.notNull(totalSupplier, "totalSupplier must not be null");
        if (pageRequest.getOffset()  == 0) {
            if (pageRequest.size() > content.size()) {
                return new CustomPage<>(0, pageRequest.size(), content.size(), content);
            }
            return new CustomPage<>(0, pageRequest.size(), totalSupplier.getAsLong(), content);
        }

        if (!content.isEmpty() && pageRequest.size() > content.size()) {
            return new CustomPage<>(pageRequest.page(), pageRequest.size(), pageRequest.getOffset() + content.size(), content);
        }

        return new CustomPage<>(pageRequest.page(), pageRequest.size(), totalSupplier.getAsLong(), content);
    }
}
