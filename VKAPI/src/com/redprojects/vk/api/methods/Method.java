package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

public abstract class Method {

    protected String name = this.getClass().getSimpleName().toLowerCase();

    protected static VKAPI vkapi = null;

    public Method() {
    }

    public Method(VKAPI vkapi) {
        Method.vkapi = vkapi;
    }

    public VKAPI getVkapi() {
        return vkapi;
    }

    protected static JSONObject getResponse(String method, String[][] args) throws VKAPIException {
        if (vkapi != null)
            return vkapi.getResponse(method, args);

        return VKAPI.getResponseWithoutAuthorization(method, args);
    }
}
