package com.finalproject.secondhand.enums;

public enum EStatusResponse {
    /**
     * If an operation is successful.
     */
    SUCCESS("Success"),

    /**
     * If an operation is unsuccessful.
     */
    FAILURE("Failure"),
    /**
     * If an operation is completed.
     */
    COMPLETED("Completed"),
    /**
     * If an operation is not completed.
     */
    NOT_COMPLETED("Not Completed"),
    /**
     * If an operation is Found.
     */
    NOT_FOUND("Not Found"),
    /**
     * If an operation is not Found.
     */
    FOUND("Found");

    private final String name;

    EStatusResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
