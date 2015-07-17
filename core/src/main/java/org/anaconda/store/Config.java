package org.anaconda.store;

import java.lang.reflect.Constructor;

import org.anaconda.common.settings.Settings;

public interface Config {

    public static <C extends Config> C loadConfig(Settings settings, Class<C> configClass) {
        try {
            Constructor<C> constructor = configClass.getConstructor(settings.getClass());
            return constructor.newInstance(settings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
