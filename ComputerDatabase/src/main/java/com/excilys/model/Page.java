package com.excilys.model;

import java.util.List;

/**
 * Represents a page that groups elements.
 * @param <T> The type of page's elements
 */
public class Page<T> {

    /**
     * The number of the page.
     */
    private int number;

    /**
     * The max number of pages.
     */
    private int maxNumber;

    /**
     * The number of elements presents in the page.
     */
    private int size;

    /**
     * The elements of the page.
     */
    private List<T> content;

    /**
     * Constructs a page.
     * @param number The number of the page
     * @param maxNumber The maximum number of pages
     * @param size The number of elements presents in the page
     * @param content The elements of the page
     */
    public Page(int number, int maxNumber, int size, List<T> content) {
        this.number = number;
        this.maxNumber = maxNumber;
        this.size = size;
        this.content = content;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the content
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * @return the maxNumber
     */
    public int getMaxNumber() {
        return maxNumber;
    }

}
