package com.tonyjev93.junit5.ex.junitexample.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Study {
    private String name = "JUN";

    Member owner;

    public Study(int i, String java) {

    }
}
