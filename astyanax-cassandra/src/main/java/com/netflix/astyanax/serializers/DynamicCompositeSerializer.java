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

import com.netflix.astyanax.model.DynamicComposite;

/**
 * @author Todd Nine
 * 
 */
public class DynamicCompositeSerializer extends
        AbstractSerializer<DynamicComposite> {
    private static final DynamicCompositeSerializer instance = new DynamicCompositeSerializer();

    public static DynamicCompositeSerializer get() {
        return instance;
    }

    @Override
    public ByteBuffer toByteBuffer(DynamicComposite obj) {
        return obj.serialize();
    }

    @Override
    public DynamicComposite fromByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer == null)
            return null;
        ByteBuffer dup = byteBuffer.duplicate();
        DynamicComposite composite = new DynamicComposite();
        composite.deserialize(dup);
        return composite;
    }

    @Override
    public ComparatorType getComparatorType() {
        return ComparatorType.DYNAMICCOMPOSITETYPE;
    }

    @Override
    public ByteBuffer fromString(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer getNext(ByteBuffer byteBuffer) {
        throw new IllegalStateException(
                "DynamicComposite columns can't be paginated this way.");
    }
}
