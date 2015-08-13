package com.redprojects.vk.api.objects;

import com.redprojects.vk.api.methods.Method;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public abstract class Object {

    public static Object[] getObjectsForMethod(Class<? extends Method> methodClass, JSONArray items) {
        Object[] objects = new Object[items.length()];
        for (int i = 0; i < items.length(); i++)
            objects[i] = getObjectForMethod(methodClass, items.getJSONObject(i));
        return objects;
    }

    public static Object getObjectForMethod(Class<? extends Method> methodClass, JSONObject item) {
        try {
            return (Object) Class.forName(Object.class.getName().replace(Object.class.getSimpleName(), methodClass.getSimpleName())).getConstructor(item.getClass()).newInstance(item);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new Object() {
        };
    }
}
