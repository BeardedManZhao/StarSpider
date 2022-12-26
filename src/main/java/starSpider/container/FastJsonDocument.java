package starSpider.container;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.filter.NameFilter;
import com.alibaba.fastjson2.filter.ValueFilter;
import com.alibaba.fastjson2.schema.JSONSchema;
import starSpider.ConstantRegion;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Json文档对象，由json解析器返回的json对象
 *
 * @author zhao
 */
public class FastJsonDocument extends JSONObject implements Container {

    public final JSONObject jsonObject;
    public final String name;
    public final String text;
    public final boolean isJObject;

    public FastJsonDocument(JSONObject jsonObject, String name) {
        this.name = name;
        if (jsonObject != null) {
            this.jsonObject = jsonObject;
            this.text = jsonObject.toString();
            isJObject = true;
        } else {
            this.jsonObject = null;
            this.text = ConstantRegion.STRING_NULL;
            isJObject = false;
        }

    }

    public FastJsonDocument(String data, String name) {
        this.jsonObject = null;
        this.name = name;
        this.text = data;
        isJObject = false;
    }

    /**
     * @return 获取到节点的字符串数据，在HTML中的实现为获取到当前节点的文本数据
     * <p>
     * Get the string data to the node
     */
    @Override
    public String getText() {
        return this.text;
    }

    /**
     * @return 获取到节点的名称
     * <p>
     * Get the name of the node
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object get(String key) {
        if (isJObject) {
            return this.jsonObject.get(key);
        } else {
            return null;
        }
    }

    @Override
    public Object get(Object key) {
        if (isJObject) {
            return this.jsonObject.get(key);
        } else {
            return null;
        }
    }

    @Override
    public Object getByPath(String jsonPath) {
        if (isJObject) {
            return this.jsonObject.getByPath(jsonPath);
        } else {
            return null;
        }
    }

    @Override
    public boolean containsKey(String key) {
        if (isJObject) {
            return this.jsonObject.containsKey(key);
        } else {
            return false;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (isJObject) {
            return this.jsonObject.containsKey(key);
        } else {
            return false;
        }
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        if (isJObject) {
            return this.jsonObject.getOrDefault(key, defaultValue);
        } else {
            return false;
        }
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        if (isJObject) {
            return this.jsonObject.getOrDefault(key, defaultValue);
        } else {
            return false;
        }
    }

    @Override
    public JSONArray getJSONArray(String key) {
        if (isJObject) {
            return this.jsonObject.getJSONArray(key);
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> getList(String key, Class<T> itemClass, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.getList(key, itemClass, features);
        } else {
            return null;
        }
    }

    @Override
    public JSONObject getJSONObject(String key) {
        if (isJObject) {
            return this.jsonObject.getJSONObject(key);
        } else {
            return null;
        }
    }

    @Override
    public String getString(String key) {
        if (isJObject) {
            return this.jsonObject.getString(key);
        } else {
            return null;
        }
    }

    @Override
    public Double getDouble(String key) {
        if (isJObject) {
            return this.jsonObject.getDouble(key);
        } else {
            return null;
        }
    }

    @Override
    public double getDoubleValue(String key) {
        if (isJObject) {
            return this.jsonObject.getDoubleValue(key);
        } else {
            return 0;
        }
    }

    @Override
    public Float getFloat(String key) {
        if (isJObject) {
            return this.jsonObject.getFloat(key);
        } else {
            return 0.0f;
        }
    }

    @Override
    public float getFloatValue(String key) {
        if (isJObject) {
            return this.jsonObject.getFloatValue(key);
        } else {
            return 0.0f;
        }
    }

    @Override
    public Long getLong(String key) {
        if (isJObject) {
            return this.jsonObject.getLong(key);
        } else {
            return null;
        }
    }

    @Override
    public long getLongValue(String key) {
        if (isJObject) {
            return this.jsonObject.getLongValue(key);
        } else {
            return 0;
        }
    }

    @Override
    public long getLongValue(String key, long defaultValue) {
        if (isJObject) {
            return this.jsonObject.getLongValue(key, defaultValue);
        } else {
            return 0;
        }
    }

    @Override
    public Integer getInteger(String key) {
        if (isJObject) {
            return this.jsonObject.getInteger(key);
        } else {
            return null;
        }
    }

    @Override
    public int getIntValue(String key) {
        if (isJObject) {
            return this.jsonObject.getIntValue(key);
        } else {
            return 0;
        }
    }

    @Override
    public int getIntValue(String key, int defaultValue) {
        if (isJObject) {
            return this.jsonObject.getIntValue(key, defaultValue);
        } else {
            return 0;
        }
    }

    @Override
    public Short getShort(String key) {
        if (isJObject) {
            return this.jsonObject.getShort(key);
        } else {
            return null;
        }
    }

    @Override
    public short getShortValue(String key) {
        if (isJObject) {
            return this.jsonObject.getShortValue(key);
        } else return 0;
    }

    @Override
    public Byte getByte(String key) {
        if (isJObject) {
            return this.jsonObject.getByte(key);
        } else return 0;
    }

    @Override
    public byte getByteValue(String key) {
        if (isJObject) {
            return this.jsonObject.getByteValue(key);
        } else return 0;
    }

    @Override
    public Boolean getBoolean(String key) {
        if (isJObject) {
            return this.jsonObject.getBoolean(key);
        } else return null;
    }

    @Override
    public boolean getBooleanValue(String key) {
        if (isJObject) {
            return this.jsonObject.getBooleanValue(key);
        } else return false;
    }

    @Override
    public boolean getBooleanValue(String key, boolean defaultValue) {
        if (isJObject) {
            return this.jsonObject.getBooleanValue(key, defaultValue);
        } else return false;
    }

    @Override
    public BigInteger getBigInteger(String key) {
        if (isJObject) {
            return this.jsonObject.getBigInteger(key);
        } else return null;
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        if (isJObject) {
            return this.jsonObject.getBigDecimal(key);
        } else return null;
    }

    @Override
    public Date getDate(String key) {
        if (isJObject) {
            return this.jsonObject.getDate(key);
        } else return null;
    }

    @Override
    public Instant getInstant(String key) {
        if (isJObject) {
            return this.jsonObject.getInstant(key);
        } else return null;
    }

    @Override
    public String toString() {
        if (isJObject) {
            return this.jsonObject.toString();
        } else return null;
    }

    @Override
    public String toString(JSONWriter.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toString(features);
        } else return null;
    }

    @Override
    public String toJSONString(JSONWriter.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toJSONString(features);
        } else return null;
    }

    @Override
    public byte[] toJSONBBytes(JSONWriter.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toJSONBBytes(features);
        } else return null;
    }

    @Override
    public <T> T to(Function<JSONObject, T> function) {
        if (isJObject) {
            return this.jsonObject.to(function);
        } else return null;
    }

    @Override
    public <T> T to(Type type, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.to(type, features);
        } else return null;
    }

    @Override
    public <T> T to(TypeReference<?> typeReference, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.to(typeReference, features);
        } else return null;
    }

    @Override
    public <T> T to(Class<T> clazz, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.to(clazz, features);
        } else return null;
    }

    @Override
    public <T> T toJavaObject(Class<T> clazz, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toJavaObject(clazz, features);
        } else return null;
    }

    @Override
    public <T> T toJavaObject(Type type, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toJavaObject(type, features);
        } else return null;
    }

    @Override
    public <T> T toJavaObject(TypeReference<?> typeReference, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.toJavaObject(typeReference, features);
        } else return null;
    }

    @Override
    public <T> T getObject(String key, Class<T> type, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.getObject(key, type, features);
        } else return null;
    }

    @Override
    public <T> T getObject(String key, Type type, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.getObject(key, type, features);
        } else return null;
    }

    @Override
    public <T> T getObject(String key, TypeReference<?> typeReference, JSONReader.Feature... features) {
        if (isJObject) {
            return this.jsonObject.getObject(key, typeReference, features);
        } else return null;
    }

    @Override
    public <T> T getObject(String key, Function<JSONObject, T> creator) {
        if (isJObject) {
            return this.jsonObject.getObject(key, creator);
        } else return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isJObject) {
            return this.jsonObject.invoke(proxy, method, args);
        } else return null;
    }

    @Override
    public JSONArray putArray(String name) {
        if (isJObject) {
            return this.jsonObject.putArray(name);
        } else return null;
    }

    @Override
    public JSONObject putObject(String name) {
        if (isJObject) {
            return this.jsonObject.putObject(name);
        } else return null;
    }

    @Override
    public JSONObject fluentPut(String key, Object value) {
        if (isJObject) {
            return this.jsonObject.fluentPut(key, value);
        } else return null;
    }

    @Override
    public boolean isValid(JSONSchema schema) {
        if (isJObject) {
            return this.jsonObject.isValid(schema);
        } else return false;
    }

    @Override
    public void valueFilter(ValueFilter valueFilter) {
        if (isJObject) {
            this.jsonObject.valueFilter(valueFilter);
        }
    }

    @Override
    public void nameFilter(NameFilter nameFilter) {
        if (isJObject) {
            this.jsonObject.nameFilter(nameFilter);
        }
    }

    @Override
    public JSONObject clone() {
        if (isJObject) {
            return this.jsonObject.clone();
        } else return null;
    }

    @Override
    public Object eval(JSONPath path) {
        if (isJObject) {
            return this.jsonObject.eval(path);
        } else return null;
    }
}
