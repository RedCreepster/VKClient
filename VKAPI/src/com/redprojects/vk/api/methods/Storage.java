package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

//TODO: Доделать
@SuppressWarnings("UnusedDeclaration")
public class Storage extends Method {

    public Storage(VKAPI vkapi) {
        super(vkapi);
    }

    private JSONObject get() {
        return null;
    }

    public JSONObject set() {
        return null;
    }

    public JSONObject getKeys(int user_id, boolean global, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"global", String.valueOf(global ? 1 : 0)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
        });
    }
}
