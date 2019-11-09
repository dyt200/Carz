package com.example.carz.util;

/**
 * Async listener
 */
public interface OnAsyncInsertEventListener {
    void onSuccessResult(Long id);
    void onSuccess();
    void onFailure(Exception e);
}
