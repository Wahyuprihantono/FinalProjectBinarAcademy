package com.finalproject.secondhand.result;

public class SuccessResult extends Result {

    public SuccessResult(boolean success) {
        super(success);
    }

    public SuccessResult(String message) {
        super(true, message);
    }

}
