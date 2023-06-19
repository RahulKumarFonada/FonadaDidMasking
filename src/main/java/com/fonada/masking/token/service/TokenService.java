package com.fonada.masking.token.service;

/**
 * Created by sbsingh on Oct/27/2020.
 */
public interface TokenService {

    void removeToken(String userName);
    void saveToken(String userName, String jwt);
    String getToken(String userName);
    String getToken(Long userId);
}
