/*******************************************************************************
 * Copyright 2011 Netflix
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.netflix.astyanax.serializers;

import java.nio.ByteBuffer;
import java.util.Set;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.SetType;

import com.netflix.astyanax.serializers.AbstractSerializer;

/**
 * Serializer implementation for generic sets.
 * 
 * @author vermes
 * 
 * @param <T>
 *            element type
 */
public class SetSerializer<T> extends AbstractSerializer<Set<T>> {

    private final SetType<T> mySet;

    /**
     * @param elements
     */
    public SetSerializer(AbstractType<T> elements) {
        mySet = SetType.getInstance(elements);
    }

    @Override
    public Set<T> fromByteBuffer(ByteBuffer arg0) {
        return arg0 == null ? null : mySet.compose(arg0);
    }

    @Override
    public ByteBuffer toByteBuffer(Set<T> arg0) {
        return arg0 == null ? null : mySet.decompose(arg0);
    }
}