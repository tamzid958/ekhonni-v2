package com.ekhonni.backend;

import java.lang.reflect.ParameterizedType;

/**
 * Author: Asif Iqbal
 * Date: 12/17/24
 */

class GetTypeParent<T> {

    protected String getGenericName()
    {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
    }
}

class GetTypeChild extends GetTypeParent<Integer> {
    public static void main(String[] args) {
        GetTypeChild getTypeChild = new GetTypeChild();
        System.out.println("Generic type: "+ getTypeChild.getGenericName());
    }
}

public class TestMain {
    public static void main(String[] args) {
    }
}
