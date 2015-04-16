package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;

public abstract class Method {

    protected String name = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1).toLowerCase();

    protected VKAPI vkapi = null;

    public Method(VKAPI vkapi) {
        this.vkapi = vkapi;
    }

    public VKAPI getVkapi() {
        return vkapi;
    }
}
