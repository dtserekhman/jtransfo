/*
 * This file is part of jTransfo, a library for converting to and from transfer objects.
 * Copyright (c) PROGS bvba, Belgium
 *
 * The program is available in open source according to the Apache License, Version 2.0.
 * For full licensing details, see LICENSE.txt in the project root.
 */

package org.jtransfo.internal;

import org.fest.assertions.MapAssert;
import org.jtransfo.Converter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test for {@link TaggedConverter}.
 */
public class TaggedConverterTest {

    private static final String TAG = "key";

    private TaggedConverter taggedConverter;

    @Before
    public void setUp() throws Exception {
        taggedConverter = new TaggedConverter();
    }

    @Test
    public void testAddConverters() throws Exception {
        Converter c1 = mock(Converter.class);
        Converter c2 = mock(Converter.class);
        taggedConverter.addConverters(new String[] {TAG, "zzz"}, c1);
        taggedConverter.addConverters(new String[] {TAG}, c2);

        Map<String, Converter> res = (Map<String, Converter>)
                ReflectionTestUtils.getField(taggedConverter, "converters");

        assertThat(res).hasSize(2).includes(entry("zzz", c1), entry("key", c2));
    }

    @Test
    public void testConvert() throws Exception {
        Converter starConverter = mock(Converter.class);
        Converter converter = mock(Converter.class);
        taggedConverter.addConverters(new String[] {TAG}, converter);
        taggedConverter.addConverters(new String[] {"*"}, starConverter);
        Object source = mock(Object.class);
        Object target = mock(Object.class);

        taggedConverter.convert(source, target);

        verifyNoMoreInteractions(converter);
        verify(starConverter).convert(source, target);
    }

    @Test
    public void testConvert_withTags() throws Exception {
        Converter converter = mock(Converter.class);
        taggedConverter.addConverters(new String[] {TAG}, converter);
        Object source = mock(Object.class);
        Object target = mock(Object.class);

        taggedConverter.convert(source, target, TAG);

        verify(converter).convert(source, target, TAG);
    }
}
