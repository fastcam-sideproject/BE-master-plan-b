package com.example.masterplanbbe.common.util;

import com.example.masterplanbbe.common.enums.SortOption;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.util.Pair;

import java.util.List;

public class SortUtil {
    public static OrderSpecifier<?> getOrderSpecifier(SortOption sortOption, boolean isAsc) {
        Order order = isAsc ? Order.ASC : Order.DESC;
        PathBuilder<?> path = new PathBuilder<>(Object.class, sortOption.getEntityAlias());

        return new OrderSpecifier<>(order, path.getNumber(sortOption.getSortField(), Integer.class));
    }

    public static OrderSpecifier<?>[] getOrderSpecifiers(List<Pair<SortOption, Boolean>> sortOptions) {
        return sortOptions.stream()
                .map(sortOption -> getOrderSpecifier(sortOption.getFirst(), sortOption.getSecond()))
                .toArray(OrderSpecifier[]::new);
    }
}
