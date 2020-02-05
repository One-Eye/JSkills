package de.gesundkrank.jskills;

@FunctionalInterface
public interface IPlayer<T> {

    T getId();

    boolean equals(Object o);

    int hashCode();
}
