package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

//TODO: Доделать
public class Docs extends Method {
    public Docs(VKAPI vkapi) {
        super(vkapi);
    }

    public JSONObject getUploadServer(int groupId) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"group_id", String.valueOf(groupId)}
        });
    }

    public JSONObject getWallUploadServer(int groupId) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"group_id", String.valueOf(groupId)}
        });
    }

    public JSONObject save(String file, String title, String tags) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"file", file},
                {"title", title},
                {"tags", tags}
        });
    }
}
