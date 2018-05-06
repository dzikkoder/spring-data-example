package com.interview.demo.exception;

public class CountryNotFoundException extends Throwable {
    public CountryNotFoundException(String s) {
        super(s);
    }
}
