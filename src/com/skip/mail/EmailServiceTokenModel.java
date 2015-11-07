package com.skip.mail;


import org.apache.commons.codec.binary.Base64;

import com.skip.entity.token.Token;
import com.skip.entity.user.User;

import java.io.Serializable;


public class EmailServiceTokenModel implements Serializable {

    private final String emailAddress;
    private final String token;
    private final String tokenType;
    private final String hostNameUrl;


    public EmailServiceTokenModel(User user, Token token, String hostNameUrl)  {
        this.emailAddress = user.getEmail();
        this.token = token.getVerificationToken();
        this.tokenType = token.getVertype();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEncodedToken() {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getHostNameUrl() {
        return hostNameUrl;
    }
}
