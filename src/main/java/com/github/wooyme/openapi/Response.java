package com.github.wooyme.openapi;

import io.vertx.core.json.JsonObject;

public class Response<T> {
    private T body;
    private JsonObject session;

    Response(T body,JsonObject session){
        this.body = body;
        this.session = session;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public JsonObject getSession() {
        return session;
    }

    public void setSession(JsonObject session) {
        this.session = session;
    }
}
