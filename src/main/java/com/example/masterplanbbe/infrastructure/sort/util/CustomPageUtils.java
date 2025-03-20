package com.example.masterplanbbe.infrastructure.sort.util;

import com.example.masterplanbbe.infrastructure.sort.enums.SortOption;
import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;
import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import io.jsonwebtoken.lang.Assert;

import java.util.List;
import java.util.function.LongSupplier;

public abstract class CustomPageUtils {
    private CustomPageUtils() {
    }

    public static <T, U extends SortOption> CustomPage<T> getPage(List<T> content, CustomPageRequest<U> pageRequest, LongSupplier totalSupplier) {
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
