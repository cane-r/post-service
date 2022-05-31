package com.csiris.postservice.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for dto-entity conversion
 * 
 * Since dto and entitys are kind of similar,no need to use a third party
 * lib(i.e mapstruct,lombok..)
 * 
 * @param <S> Source class from which properties are to be copied
 * @param <T> Target class to which properties are to be copied
 */
@Component
public class NonNullPropertyObjectCopier<S, T> {

	// exclude null properties and id property from the Source object
	private String[] getNullProperties(S source) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
		PropertyDescriptor[] propertyDescriptors = wrapper.getPropertyDescriptors();

		Set<String> fieldsToExclude = new HashSet<String>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Object srcValue = wrapper.getPropertyValue(propertyDescriptor.getName());
			// wrapper.getWrappedClass().getDeclaredField(propertyDescriptor.getName()).isAnnotationPresent(JsonIgnore.class);
			if (srcValue == null)
				fieldsToExclude.add(propertyDescriptor.getName());
		}
		//fieldsToExclude.add("id");
		String[] result = new String[fieldsToExclude.size()];
		return fieldsToExclude.toArray(result);
	}

	public void copyProperties(S src, T target) {
		BeanUtils.copyProperties(src, target, getNullProperties(src));
	}

//	public T copyPropertiesAndReturn(S src,T target) {
//		BeanUtils.copyProperties(src, target, getNullProperties(src));
//		// Probably its best to go with BeanUtils.copyProperties instead of cross-checking properties
//		Field[] declaredFields = target.getClass().getDeclaredFields();
//		for(Field f : declaredFields) {
//			System.out.println(f.getName());
//		}
//		return target;
//	}
}