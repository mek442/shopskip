package com.skip.resource;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.skip.entity.social.Social;
import com.skip.entity.token.Token;
import com.skip.entity.user.User;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
public class ExternalUser implements Principal {

    private String id;

    @Length(max=50)
    private String firstName;

    @Length(max=50)
    private String lastName;

    @NotNull
    @Email
    private String emailAddress;

     

    private List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();

    public ExternalUser() {}

    public ExternalUser(String userId) {
        this.id = userId;
    }

    public ExternalUser(User user) {
        this.id = user.getUuid().toString();
        this.emailAddress = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
      
        for(Social socialUser: user.getSocials()) {
            SocialProfile profile = new SocialProfile();
            profile.setProvider(socialUser.getProviderId());
            profile.setProviderUserId(socialUser.getProviderUserId());
            socialProfiles.add(profile);
        }
       
    }

    public ExternalUser(User user, Token activeSession) {
        this(user);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public String getId() {
        return id;
    }

    

    public String getName() {
        return emailAddress;
    }

    
}
