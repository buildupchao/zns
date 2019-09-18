package com.buildupchao.zns.common.util;

import com.buildupchao.zns.common.serialize.ISerializer;
import com.buildupchao.zns.common.serialize.impl.JsonSerializer;

/**
 * @author buildupchao
 * @date 2019/1/31 22:03
 * @since JDK 1.8
 */
public class SerializationUtil {

    private static final ISerializer serializer = new JsonSerializer();

    public static <T> byte[] serialize(T t) {
        return serializer.serialize(t);
    }

    public static <T> T deserialize(byte[] bytes, Class<T> cls) {
        return serializer.deserialize(bytes, cls);
    }
}
