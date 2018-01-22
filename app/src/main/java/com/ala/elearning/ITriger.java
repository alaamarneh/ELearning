package com.ala.elearning;

/**
 * Created by alaam on 11/30/2017.
 */

public interface ITriger<T> {
    void onResponse(T response);
    void onError(String error);
}
