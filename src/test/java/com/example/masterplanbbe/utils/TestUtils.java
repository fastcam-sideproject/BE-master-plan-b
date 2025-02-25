package com.example.masterplanbbe.utils;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestUtils {
    public static <T> T createExistingEntity(Supplier<T> supplier) {
        return withSetup(
                supplier,
                entity -> setId(entity, 1L)
        );
    }

    public static <T> T simulateSavingEntity(InvocationOnMock invocation) {
        return withSetup(
                () -> invocation.getArgument(0),
                entity -> setId(entity, 1L)
        );
    }

    private static void setId(Object entity, Long id) {
        ReflectionTestUtils.setField(entity, "id", id);
    }

    private static <T> T withSetup(Supplier<T> supplier, Consumer<T> setup) {
        T instance = supplier.get();
        setup.accept(instance);
        return instance;
    }
}
