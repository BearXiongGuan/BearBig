package com.cykj.ssh.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
/**
 * �������� {@code JSON} ���ݵĳ��÷����Ĺ����ࡣ
 * <p />
 * �ù�����ʹ�õ� {@code JSON} ת�������� <a href="http://code.google.com/p/google-gson/" mce_href="http://code.google.com/p/google-gson/" target="_blank">
 * {@code Google Gson}</a>�� �����ǹ������ʹ�ð�����
 * 
 * <pre>
 * public class User { 
 *     @SerializedName("pwd")
 *     private String password; 
 *     @Expose
 *     @SerializedName("uname")
 *     private String username; 
 *     @Expose
 *     @Since(1.1)
 *     private String gender; 
 *     @Expose
 *     @Since(1.0)
 *     private String sex; 
 *      
 *     public User() {} 
 *     public User(String username, String password, String gender) { 
 *         // user constructor code... ... ... 
 *     } 
 *      
 *     public String getUsername() 
 *     ... ... ... 
 * }
 * List<User> userList = new LinkedList<User>(); 
 * User jack = new User("Jack", "123456", "Male"); 
 * User marry = new User("Marry", "888888", "Female"); 
 * userList.add(jack); 
 * userList.add(marry); 
 * Type targetType = new TypeToken<List<User>>(){}.getType(); 
 * String sUserList1 = JSONUtils.toJson(userList, targetType); 
 * sUserList1 ----> [{"uname":"jack","gender":"Male","sex":"Male"},{"uname":"marry","gender":"Female","sex":"Female"}] 
 * String sUserList2 = JSONUtils.toJson(userList, targetType, false); 
 * sUserList2 ----> [{"uname":"jack","pwd":"123456","gender":"Male","sex":"Male"},{"uname":"marry","pwd":"888888","gender":"Female","sex":"Female"}] 
 * String sUserList3 = JSONUtils.toJson(userList, targetType, 1.0d, true); 
 * sUserList3 ----> [{"uname":"jack","sex":"Male"},{"uname":"marry","sex":"Female"}]
 * </pre>
 * 
 * @version 1.1.0
 */
@SuppressWarnings("unchecked")
public class JSONUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);
    /** �յ� {@code JSON} ���� - <code>"{}"</code>�� */
    public static final String EMPTY_JSON = "{}";
    /** �յ� {@code JSON} ����(����)���� - {@code "[]"}�� */
    public static final String EMPTY_JSON_ARRAY = "[]";
    /** Ĭ�ϵ� {@code JSON} ����/ʱ���ֶεĸ�ʽ��ģʽ�� */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** {@code Google Gson} �� <code>@Since</code> ע�ⳣ�õİ汾�ų��� - {@code 1.0}�� */
    public static final double SINCE_VERSION_10 = 1.0d;
    /** {@code Google Gson} �� <code>@Since</code> ע�ⳣ�õİ汾�ų��� - {@code 1.1}�� */
    public static final double SINCE_VERSION_11 = 1.1d;
    /** {@code Google Gson} �� <code>@Since</code> ע�ⳣ�õİ汾�ų��� - {@code 1.2}�� */
    public static final double SINCE_VERSION_12 = 1.2d;
    /** {@code Google Gson} �� <code>@Until</code> ע�ⳣ�õİ汾�ų��� - {@code 1.0}�� */
    public static final double UNTIL_VERSION_10 = SINCE_VERSION_10;
    /** {@code Google Gson} �� <code>@Until</code> ע�ⳣ�õİ汾�ų��� - {@code 1.1}�� */
    public static final double UNTIL_VERSION_11 = SINCE_VERSION_11;
    /** {@code Google Gson} �� <code>@Until</code> ע�ⳣ�õİ汾�ų��� - {@code 1.2}�� */
    public static final double UNTIL_VERSION_12 = SINCE_VERSION_12;
    /**
     * <p>
     * <code>JSONUtils</code> instances should NOT be constructed in standard programming. Instead,
     * the class should be used as <code>JSONUtils.fromJson("foo");</code>.
     * </p>
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     */
    public JSONUtils() {
        super();
    }
    /**
     * ��������Ŀ��������ָ������������ת���� {@code JSON} ��ʽ���ַ�����
     * <p />
     * <strong>�÷���ת����������ʱ�������׳��κ��쳣������������ʱ����ͨ���󷵻� <code>"{}"</code>�� ���ϻ�������󷵻� <code>"[]"</code>
     * </strong>
     * 
     * @param target Ŀ�����
     * @param targetType Ŀ���������͡�
     * @param isSerializeNulls �Ƿ����л� {@code null} ֵ�ֶΡ�
     * @param version �ֶεİ汾��ע�⡣
     * @param datePattern �����ֶεĸ�ʽ��ģʽ��
     * @param excludesFieldsWithoutExpose �Ƿ��ų�δ��ע {@literal @Expose} ע����ֶΡ�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version,
            String datePattern, boolean excludesFieldsWithoutExpose) {
        if (target == null) return EMPTY_JSON;
        GsonBuilder builder = new GsonBuilder();
        if (isSerializeNulls) builder.serializeNulls();
        if (version != null) builder.setVersion(version.doubleValue());
        if (StringUtils.isBlank(datePattern)) datePattern = DEFAULT_DATE_PATTERN;
        builder.setDateFormat(datePattern);
        if (excludesFieldsWithoutExpose) builder.excludeFieldsWithoutExposeAnnotation();
        return toJson(target, targetType, builder);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ֻ����ת����ͨ�� {@code JavaBean} ����</strong>
     * <ul>
     * <li>�÷���ֻ��ת������ {@literal @Expose} ע����ֶΣ�</li>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷�����ת������δ��ע���ѱ�ע {@literal @Since} ���ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target) {
        return toJson(target, null, false, null, null, false);
    }
    /**
     * ��ͨ�� gson
     * @param target
     * @return
     */
    public static String toJson(JsonObject target) {
    	Gson gson = new Gson();
        return gson.toJson(target);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ֻ����ת����ͨ�� {@code JavaBean} ����</strong>
     * <ul>
     * <li>�÷���ֻ��ת������ {@literal @Expose} ע����ֶΣ�</li>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷�����ת������δ��ע���ѱ�ע {@literal @Since} ���ֶΣ�</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param datePattern �����ֶεĸ�ʽ��ģʽ��
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, String datePattern) {
        return toJson(target, null, false, null, datePattern, true);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ֻ����ת����ͨ�� {@code JavaBean} ����</strong>
     * <ul>
     * <li>�÷���ֻ��ת������ {@literal @Expose} ע����ֶΣ�</li>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param version �ֶεİ汾��ע��({@literal @Since})��
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Double version) {
        return toJson(target, null, false, version, null, true);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ֻ����ת����ͨ�� {@code JavaBean} ����</strong>
     * <ul>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷�����ת������δ��ע���ѱ�ע {@literal @Since} ���ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param excludesFieldsWithoutExpose �Ƿ��ų�δ��ע {@literal @Expose} ע����ֶΡ�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
        return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ֻ����ת����ͨ�� {@code JavaBean} ����</strong>
     * <ul>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param version �ֶεİ汾��ע��({@literal @Since})��
     * @param excludesFieldsWithoutExpose �Ƿ��ų�δ��ע {@literal @Expose} ע����ֶΡ�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
        return toJson(target, null, false, version, null, excludesFieldsWithoutExpose);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ͨ������ת��ʹ�÷��͵Ķ���</strong>
     * <ul>
     * <li>�÷���ֻ��ת������ {@literal @Expose} ע����ֶΣ�</li>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷�����ת������δ��ע���ѱ�ע {@literal @Since} ���ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param targetType Ŀ���������͡�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, false, null, null, true);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ͨ������ת��ʹ�÷��͵Ķ���</strong>
     * <ul>
     * <li>�÷���ֻ��ת������ {@literal @Expose} ע����ֶΣ�</li>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param targetType Ŀ���������͡�
     * @param version �ֶεİ汾��ע��({@literal @Since})��
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Type targetType, Double version) {
        return toJson(target, targetType, false, version, null, true);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ͨ������ת��ʹ�÷��͵Ķ���</strong>
     * <ul>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷�����ת������δ��ע���ѱ�ע {@literal @Since} ���ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param targetType Ŀ���������͡�
     * @param excludesFieldsWithoutExpose �Ƿ��ų�δ��ע {@literal @Expose} ע����ֶΡ�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
    }
    /**
     * ��������Ŀ�����ת���� {@code JSON} ��ʽ���ַ�����<strong>�˷���ͨ������ת��ʹ�÷��͵Ķ���</strong>
     * <ul>
     * <li>�÷�������ת�� {@code null} ֵ�ֶΣ�</li>
     * <li>�÷���ת��ʱʹ��Ĭ�ϵ� ����/ʱ�� ��ʽ��ģʽ - {@code yyyy-MM-dd HH:mm:ss SSS}��</li>
     * </ul>
     * 
     * @param target Ҫת���� {@code JSON} ��Ŀ�����
     * @param targetType Ŀ���������͡�
     * @param version �ֶεİ汾��ע��({@literal @Since})��
     * @param excludesFieldsWithoutExpose �Ƿ��ų�δ��ע {@literal @Expose} ע����ֶΡ�
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.0
     */
    public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
    }
    /**
     * �������� {@code JSON} �ַ���ת����ָ�������Ͷ���
     * 
     * @param <T> Ҫת����Ŀ�����͡�
     * @param json ������ {@code JSON} �ַ�����
     * @param token {@code com.google.gson.reflect.TypeToken} ������ָʾ�����
     * @param datePattern ���ڸ�ʽģʽ��
     * @return ������ {@code JSON} �ַ�����ʾ��ָ�������Ͷ���
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return (T)gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            LOGGER.error(json + " �޷�ת��Ϊ " + token.getRawType().getName() + " ����!", ex);
            return null;
        }
    }
    /**
     * �������� {@code JSON} �ַ���ת����ָ�������Ͷ���
     * 
     * @param <T> Ҫת����Ŀ�����͡�
     * @param json ������ {@code JSON} �ַ�����
     * @param token {@code com.google.gson.reflect.TypeToken} ������ָʾ�����
     * @return ������ {@code JSON} �ַ�����ʾ��ָ�������Ͷ���
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, null);
    }
    /**
     * �������� {@code JSON} �ַ���ת����ָ�������Ͷ���<strong>�˷���ͨ������ת����ͨ�� {@code JavaBean} ����</strong>
     * 
     * @param <T> Ҫת����Ŀ�����͡�
     * @param json ������ {@code JSON} �ַ�����
     * @param clazz Ҫת����Ŀ���ࡣ
     * @param datePattern ���ڸ�ʽģʽ��
     * @return ������ {@code JSON} �ַ�����ʾ��ָ�������Ͷ���
     * @since 1.0
     */
    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return (T)gson.fromJson(json, clazz);
        } catch (Exception ex) {
            LOGGER.error(json + " �޷�ת��Ϊ " + clazz.getName() + " ����!", ex);
            return null;
        }
    }
    /**
     * �������� {@code JSON} �ַ���ת����ָ�������Ͷ���<strong>�˷���ͨ������ת����ͨ�� {@code JavaBean} ����</strong>
     * 
     * @param <T> Ҫת����Ŀ�����͡�
     * @param json ������ {@code JSON} �ַ�����
     * @param clazz Ҫת����Ŀ���ࡣ
     * @return ������ {@code JSON} �ַ�����ʾ��ָ�������Ͷ���
     * @since 1.0
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return (T)fromJson(json, clazz, null);
    }
    /**
     * ��������Ŀ��������{@code GsonBuilder} ��ָ������������ת���� {@code JSON} ��ʽ���ַ�����
     * <p />
     * �÷���ת����������ʱ�������׳��κ��쳣������������ʱ��{@code JavaBean} ���󷵻� <code>"{}"</code>�� ���ϻ�������󷵻�
     * <code>"[]"</code>�� �䱾�������ͣ�������Ӧ�Ļ���ֵ��
     * 
     * @param target Ŀ�����
     * @param targetType Ŀ���������͡�
     * @param builder �ɶ��Ƶ�{@code Gson} ��������
     * @return Ŀ������ {@code JSON} ��ʽ���ַ�����
     * @since 1.1
     */
    public static String toJson(Object target, Type targetType, GsonBuilder builder) {
        if (target == null) return EMPTY_JSON;
        Gson gson = null;
        if (builder == null) {
            gson = new Gson();
        } else {
            gson = builder.create();
        }
        String result = EMPTY_JSON;
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception ex) {
            LOGGER.warn("Ŀ����� " + target.getClass().getName() + " ת�� JSON �ַ���ʱ�������쳣��", ex);
            if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            }
        }
        return result;
    }
    
    public static JsonElement parse(String json){
    	JsonParser jsonParser = new JsonParser();
    	return jsonParser.parse(json);
    }
    /**
     * ��JSONArray��ȡ����index��JSONObject.
     * 
     * @param jsonObjects
     * @param index
     * @return ����JSONObject��null
     */
    public static JsonObject getIndexJSONObject(JsonArray jsonObjects, int index) {
        if (jsonObjects == null || jsonObjects.size() <= index) {
            return null;
        }
        return (JsonObject) jsonObjects.get(index);
    }

    /**
     * ��JSONObject��������ȡkey��ֵ��
     * 
     * @param <T>
     * @param jsonObj
     * @param propName
     * @param propType
     * @return ����T����null��
     */
    public static <T> T getValue(JsonObject jsonObj, String propName, Class<T> propType) {
        Object obj = jsonObj.get(propName);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    /**
     * ��JSONObject��������ȡkey��ֵ��
     * 
     * @param <T>
     * @param jsonObj
     * @param propName
     * @param propType
     * @param defValue
     * @return ����T����defValue��
     */
    public static <T> T getValue(JsonObject jsonObj, String propName, Class<T> propType, T defValue) {
        Object obj = jsonObj.get(propName);
        if (obj == null) {
            return defValue;
        }
        return (T) obj;
    }
    
    public <T> T fromJson(JsonElement json, Class<T> clazz, String datePattern){
    	 if (json==null) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return (T)gson.fromJson(json, clazz);
        } catch (Exception ex) {
            LOGGER.error(json + " �޷�ת��Ϊ " + clazz.getName() + " ����!", ex);
            return null;
        }
    }
	 public <T> T fromJson(JsonElement json, Class<T> clazz){
		return (T)fromJson(json, clazz, null);
	}
    
}
