package com.ala.elearning;

/**
 * Created by alaam on 12/28/2017.
 */

public interface IResponseTriger<T> {
    void onResponse(T value);
    void onError(String err);
}
