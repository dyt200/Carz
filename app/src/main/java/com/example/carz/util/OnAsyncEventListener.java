package com.example.carz.util;

/**
 * Async listener
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
