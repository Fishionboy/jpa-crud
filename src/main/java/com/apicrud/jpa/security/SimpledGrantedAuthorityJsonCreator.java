package com.apicrud.jpa.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpledGrantedAuthorityJsonCreator {
    
    @JsonCreator
    public SimpledGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){}
}
