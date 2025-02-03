package com.example.masterplanbbe.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestUtils {
    public static <T> T withSetup(Supplier<T> supplier, Consumer<T> setup) {
        T instance = supplier.get();
        setup.accept(instance);
        return instance;
    }
}
