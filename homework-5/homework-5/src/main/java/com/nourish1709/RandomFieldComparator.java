package com.nourish1709;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private final Class<T> clazz;
    private final Field randomField;

    public RandomFieldComparator(Class<T> targetType) {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        this.clazz = targetType;

        List<Field> fields = Arrays.stream(targetType.getDeclaredFields())
                .filter(accessFields(compareOnlyAccessibleFields))
                .peek(field -> field.setAccessible(true))
                .toList();

        if (fields.size() == 0) {
            throw new IllegalArgumentException("There isn't any field available to compare by. " +
                    "The field should either be primitive or Comparable");
        }

        var randomFieldIndex = ThreadLocalRandom.current().nextInt(fields.size());
        this.randomField = fields.get(randomFieldIndex);
    }

    private Predicate<? super Field> accessFields(boolean compareOnlyAccessibleFields) {
        if (compareOnlyAccessibleFields) {
            Predicate<Field> isPublicComparableOfPrimitive = this::isPublicComparableOrPrimitive;
            return isPublicComparableOfPrimitive.or(this::hasPublicGetter);
        }

        return this::isComparableOrPrimitive;
    }

    private boolean isPublicComparableOrPrimitive(Field field) {
        return Arrays.stream(this.clazz.getFields())
                .filter(publicField -> Comparable.class.isAssignableFrom(publicField.getType()) ||
                        publicField.getType().isPrimitive())
                .anyMatch(publicField -> publicField.equals(field));
    }

    private boolean hasPublicGetter(Field field) {
        return Arrays.stream(this.clazz.getMethods())
                .anyMatch(method -> method.getName()
                        .equalsIgnoreCase("get" + field.getName()));
    }

    private boolean isComparableOrPrimitive(Field field) {
        return Comparable.class.isAssignableFrom(field.getType()) ||
                field.getType().isPrimitive();
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @Override
    @SneakyThrows
    public int compare(T o1, T o2) {
        var f1 = (Comparable) this.randomField.get(o1);
        var f2 = (Comparable) this.randomField.get(o2);

        if (f1 == null || f2 == null) {
            if (f1 == null && f2 == null) {
                return 0;
            }
            return f1 == null ? 1 : -1;
        }

        return f1.compareTo(f2);
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'",
                this.clazz, this.randomField.getName());
    }
}
