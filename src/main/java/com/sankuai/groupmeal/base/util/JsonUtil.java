package com.sankuai.groupmeal.base.util;

import com.google.common.base.CaseFormat;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 14:16
 */
public class JsonUtil {
    /**
     * 全局Gson对象，Thread-safe
     */
    private static final Gson GSON;

    /**
     * serialize排除策略
     * 规则：属性标注Expose注解 & serialize=false
     */
    private static final ExclusionStrategy SERIALIZATION_EXCLUSION_STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            /**
             * 按注解排除，serialize=true排除
             */
            Expose expose = f.getAnnotation(Expose.class);
            return expose != null && !expose.serialize();
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    /**
     * deserialize排除策略
     * 规则：属性标注Expose注解 & deserialize=false
     */
    private static final ExclusionStrategy DESERIALIZATION_EXCLUSION_STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            /**
             * 按注解排除，deserialize=true排除
             */
            Expose expose = f.getAnnotation(Expose.class);
            return expose != null && !expose.deserialize();
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    static {
        GSON = gsonBuilder().create();
    }

    /**
     * 获取gsonBuilder实例，用于个性设置序列化/反序列化
     *
     * @return gsonBuilder实例
     */
    private static GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(SERIALIZATION_EXCLUSION_STRATEGY)
                .addDeserializationExclusionStrategy(DESERIALIZATION_EXCLUSION_STRATEGY);
    }

    /**
     * 对象 序列化为 json字符串
     *
     * @param obj 待序列化对象，如果为null会直接返回null
     * @return json串
     */
    public static String encode(Object obj) {
        if (obj == null) {
            return null;
        }
        return GSON.toJson(obj);
    }

    /**
     * 对象 序列化为 json字符串
     *
     * @param obj 待序列化对象，如果为obj=null则直接return null
     * @return json串
     * json属性格式：
     * <li>someFieldName ---> some_field_name</li>
     */
    public static String encode2UnderScore(Object obj) {
        if (obj == null) {
            return null;
        }
        return gsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create().toJson(obj);
    }

    /**
     * json字符串 反序列化为 对象
     *
     * @param json  待反序列化json串，如果json=null 则直接return null
     * @param clazz 目标对象class
     * @param <R>   目标对象class泛型
     * @return 反序列化对象
     */
    public static <R> R decode(String json, Class<R> clazz) {
        if (json == null || "null".equals(json)) {
            return null;
        }
        return GSON.fromJson(json, clazz);
    }

    /**
     * json字符串 反序列化为 对象
     *
     * @param json  待反序列化json串，如果json=null 则直接return null
     * @param clazz 目标class类型
     * @param <R>   返回值类型
     * @return 反序列化对象
     * json属性格式：<li>some_field_name ---> someFieldName</li>
     */
    public static <R> R decode2Camel(String json, Class<R> clazz) {
        if (StringUtils.equals(json, "null") || StringUtils.isBlank(json)) {
            return null;
        }
        return gsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName());
                    }
                })
                .create()
                .fromJson(json, clazz);
    }

    /**
     * json字符串 反序列化为 list
     *
     * @param json  待反序列化json串，如果json=null或"null"，则直接return emptyList()
     * @param clazz 目标list泛型class
     * @param <R>   list泛型类型
     * @return 反序列化list
     * <p>
     * 示例：
     * String jsonStr = "[{"id":1,"name":"test"},{"id":2,"name":"test2"}]";
     * List<User> users = JsonUtils.decode2List(jsonStr, User.class);
     */
    public static <R> List<R> decode2List(String json, final Class<R> clazz) {
        if (json == null || "null".equals(json) || "".equals(json)) {
            return Collections.emptyList();
        }
        return GSON.fromJson(json, new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{clazz};
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        });
    }

    /**
     * json字符串 反序列化为 执行类型的对象
     * <p>
     * 注：此方法支持最复杂的json反序列化形式，例如：toMap、toList、toListMap、toMapList、to对象
     * 一般情况为了简化使用，推荐直接使用默认的方式：
     *
     * @param json          待反序列化json串，如果json=null或"null"，则直接return null
     * @param typeReference 参数化类型引用
     * @param <R>           结果泛型
     * @return 指定类型返回结果
     * <p>
     * 使用示例：
     * String jsonStr = "{\"7200\":[230600,370300],\"3600\":[110600,110300]}";
     * Map<String, List<Integer>> mapList = JsonUtils.decode(jsonStr, new ParameterizedTypeReference<Map<String, List<Integer>>>() {
     * }));
     * <p>
     * 或
     * String jsonStr = "{\"desc\":\"测试1\",\"value\":10.01,\"adult\":true}";
     * Map<String, Object> map = JsonUtils.decode(jsonStr, new ParameterizedTypeReference<Map<String, Object>>(){});
     * <p>
     * 或
     * String jsonStr = "[{"id":1,"name":"test"},{"id":2,"name":"test2"}]";
     * JsonUtils.decode(USER_LIST_JSON, new ParameterizedTypeReference<List<User>>(){})
     */
    public static <R> R decode(String json, ParameterizedTypeReference<R> typeReference) {
        if (StringUtils.equals(json, "null") || StringUtils.isBlank(json)) {
            return null;
        }
        return gsonBuilder()
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                }.getType(), new ObjectTypeAdapter())
                .create()
                .fromJson(json, typeReference.getType());
    }


    /**
     * 参数化类型引用
     * 一般用于复杂类型的引用传值封装
     *
     * @param <R>
     */
    public abstract static class ParameterizedTypeReference<R> {
        private final Type type;

        public ParameterizedTypeReference() {
            Type clazz = getClass().getGenericSuperclass();
            type = ((ParameterizedType) clazz).getActualTypeArguments()[0];
        }

        public Type getType() {
            return this.type;
        }
    }


    /**
     * primitive/Map/List 对象类型的适配
     * 从 拷贝ObjectTypeAdapter出来，重写数值型处理防止出现整数默认处理为double的问题
     *
     * @see com.google.gson.internal.bind.ObjectTypeAdapter
     */
    public static class ObjectTypeAdapter extends TypeAdapter<Object> {
        @Override
        public void write(JsonWriter jsonWriter, Object o) throws IOException {
            //无须实现
        }

        @Override
        public Object read(JsonReader jsonReader) throws IOException {
            JsonToken token = jsonReader.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        list.add(read(jsonReader));
                    }
                    jsonReader.endArray();
                    return list;
                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<>();
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        map.put(jsonReader.nextName(), read(jsonReader));
                    }
                    jsonReader.endObject();
                    return map;
                case STRING:
                    return jsonReader.nextString();
                case NUMBER:
                    String dbNumStr = jsonReader.nextString();
                    if (dbNumStr.contains(".") || dbNumStr.contains("e") || dbNumStr.contains("E")) {
                        return NumberUtils.toDouble(dbNumStr);
                    }
                    return NumberUtils.toLong(dbNumStr);
                case BOOLEAN:
                    return jsonReader.nextBoolean();
                case NULL:
                    jsonReader.nextNull();
                    return null;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}