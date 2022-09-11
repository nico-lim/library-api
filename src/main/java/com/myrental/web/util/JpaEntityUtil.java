package com.myrental.web.util;

import javax.persistence.Table;
import java.util.Optional;

public class JpaEntityUtil {
    private JpaEntityUtil() {}

    public static Optional<Table> getJpaTableAnnotation(Class<?> entityAnotatedClass)  {
        try {
            return Optional.ofNullable(entityAnotatedClass.getAnnotation(Table.class));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

}
