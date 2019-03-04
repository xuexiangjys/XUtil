package com.xuexiang.xutil.common;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * (简易的Java反射类工具）
 * 在使用工具类之前主要注意两点：
 * 1.如果调用的方法属于该对象的父类,那getDeclaredMethod就会抛异常NoSuchMethodException;
 * 2.如果我们有个方法里面包含基础数据类型（int、byte、boolean等等）,在反射getClass()获取参数类型的时候会发现int变成了Integer、boolean变成了Boolean包装类，在调用getDeclaredMethod获取方法对象的时候就会抛异常NoSuchMethodException;
 *
 * @author XUE
 * @since 2019/3/4 18:14
 */
public class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //================属性设置==================//

    /**
     * 反射设置某个对象的成员属性
     *
     * @param owner     对象
     * @param fieldName 属性名
     * @param value     属性值
     * @return
     * @throws Exception
     */
    public static void setField(Object owner, String fieldName, Object value)
            throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field field = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(owner, value);
    }

    /**
     * 反射设置某个类的静态属性
     *
     * @param className 类名
     * @param fieldName 属性名
     * @param value     属性值
     * @return
     * @throws Exception
     */
    public static void setStaticField(String className, String fieldName, Object value)
            throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Field field = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }

    /**
     * 反射设置某个类的静态属性
     *
     * @param clazz     类
     * @param fieldName 属性名
     * @param value     属性值
     * @return
     * @throws Exception
     */
    public static void setStaticField(Class<?> clazz, String fieldName, Object value)
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }

    //================属性获取==================//

    /**
     * 反射获取某个对象的成员属性
     *
     * @param owner     对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getField(Object owner, String fieldName)
            throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field field = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(owner);
    }

    /**
     * 反射获取某个类的静态属性
     *
     * @param className 类名
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getStaticField(String className, String fieldName)
            throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Field field = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(ownerClass);
    }

    /**
     * 反射获取某个类的静态属性
     *
     * @param clazz     类
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getStaticField(Class<?> clazz, String fieldName)
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(clazz);
    }

    //================方法执行==================//

    /**
     * 反射执行某对象的成员方法
     *
     * @param owner      对象
     * @param methodName 方法名
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object owner, String methodName,
                                      Object... args) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Class<?>[] argsClass = getClasses(args);
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * 反射执行某对象的成员方法（对于参数是基础类型的方法，反射时用getClass获取的是包装类型，会出错）
     *
     * @param owner      对象
     * @param methodName 方法名
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object owner, String methodName,
                                      Class<?>[] argsClass, Object... args) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * 反射执行某个类的静态方法
     *
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(String className,
                                            String methodName, Object... args) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Class<?>[] argsClass = getClasses(args);
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 反射执行某个类的静态方法
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(Class<?> clazz,
                                            String methodName, Object... args) throws Exception {
        Class<?>[] argsClass = getClasses(args);
        Method method = clazz.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 反射执行某个类的静态方法（对于参数是基础类型的方法，反射时用getClass获取的是包装类型，会出错）
     *
     * @param className  类名
     * @param methodName 方法名
     * @param argsClass  参数类型的集合
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(String className,
                                            String methodName, Class<?>[] argsClass, Object... args) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 反射执行某个类的静态方法（对于参数是基础类型的方法，反射时用getClass获取的是包装类型，会出错）
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param argsClass  参数类型的集合
     * @param args       参数集合
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(Class<?> clazz,
                                            String methodName, Class<?>[] argsClass, Object... args) throws Exception {
        Method method = clazz.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 获取参数的类集合
     *
     * @param args
     * @return
     */
    private static Class<?>[] getClasses(Object... args) {
        Class<?>[] argsClass;
        if (args != null && args.length > 0) {
            argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
        } else {
            argsClass = new Class[0];
        }
        return argsClass;
    }

    //================新建实例==================//
    /**
     * 反射构建实例
     *
     * @param className 类名
     * @param args      构造函数的参数集合
     * @return
     * @throws Exception
     */
    public static Object newInstance(String className, Object... args)
            throws Exception {
        Class<?> newOneClass = Class.forName(className);
        Class<?>[] argsClass = getClasses(args);
        Constructor<?> cons = newOneClass.getDeclaredConstructor(argsClass);
        cons.setAccessible(true);
        return cons.newInstance(args);
    }

    /**
     * 反射构建实例
     *
     * @param clazz 类
     * @param args  构造函数的参数集合
     * @return
     * @throws Exception
     */
    public static <T> T newInstance(Class<T> clazz, Object... args)
            throws Exception {
        Class<?>[] argsClass = getClasses(args);
        Constructor<?> cons = clazz.getDeclaredConstructor(argsClass);
        cons.setAccessible(true);
        return (T) cons.newInstance(args);
    }

    /**
     * 反射构建实例
     *
     * @param className 类名
     * @param argsClass 参数类型的集合
     * @param args      构造函数的参数集合
     * @return
     * @throws Exception
     */
    public static Object newInstance(String className, Class<?>[] argsClass, Object... args)
            throws Exception {
        Class<?> newOneClass = Class.forName(className);
        Constructor<?> cons = newOneClass.getDeclaredConstructor(argsClass);
        cons.setAccessible(true);
        return cons.newInstance(args);
    }

    /**
     * 反射构建实例
     *
     * @param clazz     类
     * @param argsClass 参数类型的集合
     * @param args      构造函数的参数集合
     * @return
     * @throws Exception
     */
    public static <T> T newInstance(Class<T> clazz, Class<?>[] argsClass, Object... args)
            throws Exception {
        Constructor<?> cons = clazz.getDeclaredConstructor(argsClass);
        cons.setAccessible(true);
        return (T) cons.newInstance(args);
    }

    /**
     * 判断是否为某个类的实例
     *
     * @param obj 对象
     * @param cls 类
     * @return
     */
    public static boolean isInstance(Object obj, Class<?> cls) {
        return cls.isInstance(obj);
    }

    /**
     * 得到数组中的某个元素
     *
     * @param array 数组对象
     * @param index 索引
     * @return
     */
    public static Object getByArray(Object array, int index) {
        return Array.get(array, index);
    }

}
