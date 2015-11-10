package com.github.mrrigby.trueinvoices.rest.domain;

/**
 * @author MrRigby
 */
public class DictionaryItem<T> {

    private T value;
    private String description;

    public DictionaryItem(T value, String description) {
        this.value = value;
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
