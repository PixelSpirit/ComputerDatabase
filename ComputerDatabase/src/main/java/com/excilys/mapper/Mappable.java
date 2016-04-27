package com.excilys.mapper;

/**
 * Permits to map a T object into a U object and vice versa.
 *
 * @param <T> The type to map into a U object
 * @param <U> The type to unmap into a T object
 */
interface Mappable<T, U> {

    /**
     * Converts a T object into a U object.
     * @param object The T object to convert into a U
     * @return The resulting U object
     */
    U map(T object);

    /**
     * Converts a U object into a U object.
     * @param object The U object to convert into a U
     * @return The resulting U object
     */
    T unmap(U object);

}
