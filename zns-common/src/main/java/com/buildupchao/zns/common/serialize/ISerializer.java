package com.buildupchao.zns.common.serialize;

/**
 * @author buildupchao
 *         Date: 2019/1/31 18:21
 * @since JDK 1.8
 */
public interface ISerializer {

    <T> byte[] serialize(T t);

    <T> T deserialize(byte[] bytes, Class<T> cls);
}
