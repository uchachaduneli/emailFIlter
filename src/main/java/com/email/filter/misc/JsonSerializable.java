
package com.email.filter.misc;

import com.google.gson.Gson;

/**
 * @author
 */
public abstract class JsonSerializable {

    public String serialize() {
        Gson g = new Gson();
        return g.toJson(this, getClass());
    }
}