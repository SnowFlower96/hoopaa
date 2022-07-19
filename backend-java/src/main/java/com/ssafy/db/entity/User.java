package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User{
    String position;
    String department;
    String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
