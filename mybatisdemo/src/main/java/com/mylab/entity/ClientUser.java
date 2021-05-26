package com.mylab.entity;

import java.util.Date;

public class ClientUser extends BaseEntity {
    private Long id;

    private String username;

    private String password;

    private String accessToken;

    private Date accessTokenValidity;

    private String refreshToken;

    public ClientUser(Long id, String username, String password, String accessToken, Date accessTokenValidity, String refreshToken) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshToken = refreshToken;
    }

    public ClientUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Date getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Date accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }
}