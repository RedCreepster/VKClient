package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

//TODO: Доделать
public class Accounts extends Method {

    public enum Filter {
        friends, messages, photos,
        videos, notes, gifts, events,
        groups, notifications, sdk
    }

    public Accounts(VKAPI vkapi) {
        super(vkapi);
    }

    public JSONObject getCounters(Filter[] filters) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFilters = "";
        for (Filter filter : filters)
            bufFilters += String.valueOf(filter) + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"filters", bufFilters}
        });
    }
}
