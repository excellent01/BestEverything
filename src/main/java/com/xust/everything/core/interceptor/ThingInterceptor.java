package com.xust.everything.core.interceptor;

import com.xust.everything.core.model.Thing;

public interface ThingInterceptor {
    void apply(Thing thing);
}
