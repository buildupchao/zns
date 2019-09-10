package com.buildupchao.zns.common.serialize.impl;

import com.buildupchao.zns.common.serialize.ISerializer;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buildupchao
 * @date 2019/1/31 18:23
 * @since JDK 1.8
 */
public class JsonSerializer implements ISerializer {

    private static Map<Class<?>, Schema<?>> classSchemaMap = new ConcurrentHashMap<>();
    private static Objenesis objenesis = new ObjenesisStd(true);

    @Override
    public <T> byte[] serialize(T t) {
        Class<T> cls = (Class<T>) t.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

        try {
            Schema<T> schema = getClassSchema(cls);
            return ProtobufIOUtil.toByteArray(t, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> cls) {
        try {
            Schema<T> schema = getClassSchema(cls);
            T message = objenesis.newInstance(cls);
            ProtobufIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private <T> Schema<T> getClassSchema(Class<T> cls) {
        Schema<T> classSchema = null;
        if (classSchemaMap.containsKey(cls)) {
            classSchema = (Schema<T>) classSchemaMap.get(cls);
        } else {
            classSchema = RuntimeSchema.getSchema(cls);
            if (classSchema != null) {
                classSchemaMap.put(cls, classSchema);
            }
        }
        return classSchema;
    }
}
