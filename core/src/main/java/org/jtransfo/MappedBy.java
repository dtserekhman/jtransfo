/*
 * This file is part of jTransfo, a library for converting to and from transfer objects.
 * Copyright (c) PROGS bvba, Belgium
 *
 * The program is available in open source according to the Apache License, Version 2.0.
 * For full licensing details, see LICENSE.txt in the project root.
 */

package org.jtransfo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which indicates how the field in the transfer object should be mapped to a field in the domain object.
 * <p/>
 * By default fields are mapped in both directions between fields of the same name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MappedBy {

    /**
     * Name of the field in the domain class.
     * <p/>
     * By default the field is assumed to have the same name in the domain class.
     */
    String field() default DEFAULT_FIELD;

    /**
     * Fully qualified class name for the type converter.
     * <p/>
     * Using this allows you to avoid a compile dependency on the class name but makes the connection more brittle
     * when refactoring. The value is ignored if {@link #typeConverterClass()} is set.
     */
    String typeConverter() default DEFAULT_TYPE_CONVERTER;

    /**
     * Class to use for type conversion.
     * <p/>
     * This requires a compile dependency on the domain class. The value has precedence over {@link #typeConverter()}.
     */
    Class typeConverterClass() default DefaultTypeConverter.class;

    /**
     * When set, the field will never be written in the domain class.
     */
    boolean readOnly() default false;

    /**
     * Default value for {@link #field} indicating that then domain object field is expected to have the same name as
     * the annotated field.
     */
    String DEFAULT_FIELD = "?";

    /**
     * Default value for type converter class name, indicating no name specified.
     */
    String DEFAULT_TYPE_CONVERTER = "?";

    /**
     * Default value for {@link #typeConverterClass} indicating the type converter which should be used if
     * {@link #typeConverter} is not set.
     */
    class DefaultTypeConverter implements TypeConverter<Object, Object> {

        @Override
        public boolean canConvert(Class<?> realToType, Class<?> realDomainType) {
            return false;
        }

        @Override
        public Object convert(Object object) throws JTransfoException {
            return null;
        }

        @Override
        public Object reverse(Object object) throws JTransfoException {
            return null;
        }
    }
}
