package com.blog.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 有关拷贝工具类的封装
 * @author Ray2310
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){
    }

    /**
     * 实现属性拷贝
     * @param source
     * @param clazz
     * @return
     */
    public static <V> V copyBean(Object source,Class<V> clazz){
        //利用反射创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性的拷贝
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**
     * 如果是list集合的属性拷贝 ，就直接调用该方法
     * @param list 源列表
     * @param clazz 目标对象
     * @param <V> 需要转换的类型的泛型
     * @return 返回转换后的集合
     */
    public static  <O,V> List<V> copyBeanList(List<O> list , Class<V> clazz ){
        List<V> collect = list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
        return collect;
    }
}
