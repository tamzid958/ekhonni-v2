package com.ekhonni.backend.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public class BeanUtilHelper {

    // Utility method to get names of properties that are blank (for String fields)
    public static String[] getBlankPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> blankPropertyNames = new HashSet<>();

        for (java.beans.PropertyDescriptor pd : pds) {
            // Check for blank values (for String fields)
            if (src.getPropertyValue(pd.getName()) instanceof String &&
                    ((String) src.getPropertyValue(pd.getName())).isBlank()) {
                blankPropertyNames.add(pd.getName());
            }
        }

        return blankPropertyNames.toArray(new String[0]);
    }
}
