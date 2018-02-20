package com.jmengxy.utildroid.utils.eventbus;

/**
 * Created by zywang on 08/09/2017.
 */

public class RemoteEvent {
    private String message;

    private RemoteEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static AuthTokenInvalidEvent authTokenInvalidEvent(String errorMessage) {
        return new AuthTokenInvalidEvent(errorMessage);
    }

    public static ForceUpdateEvent forceUpdateEvent(String message) {
        return new ForceUpdateEvent(message);
    }

    static final class AuthTokenInvalidEvent extends RemoteEvent {
        private AuthTokenInvalidEvent(String message) {
            super(message);
        }
    }

    static final class ForceUpdateEvent extends RemoteEvent {
        private ForceUpdateEvent(String message) { super(message); }
    }
}
