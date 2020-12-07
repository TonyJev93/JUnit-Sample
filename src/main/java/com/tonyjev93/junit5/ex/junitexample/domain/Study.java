package com.tonyjev93.junit5.ex.junitexample.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Study {
    Member owner;
    String status;
    LocalDateTime opendDataTime;
    private String name = "JUN";

    public Study(int i, String java) {

    }

    public void open() {
        this.status = "OPENED";
        this.opendDataTime = LocalDateTime.now();
    }


}
