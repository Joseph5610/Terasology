/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.logic.location;

import org.junit.Test;
import org.terasology.TerasologyTestingEnvironment;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.math.geom.Vector3f;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DistanceComparatorTest extends TerasologyTestingEnvironment {
    private long nextFakeEntityId = 1;

    private EntityRef createFakeEntityWith(LocationComponent locationComponent) {
        EntityRef entRef = mock(EntityRef.class);
        when(entRef.getComponent(LocationComponent.class)).thenReturn(locationComponent);
        when(entRef.exists()).thenReturn(true);
        when(entRef.getId()).thenReturn(nextFakeEntityId++);
        return entRef;
    }

    private DistanceComparator distanceComparator = new DistanceComparator();

    @Test
    public void testCompareEquals() {
        LocationComponent first = new LocationComponent();
        EntityRef firstEntity = createFakeEntityWith(first);

        LocationComponent second = new LocationComponent();
        EntityRef secondEntity = createFakeEntityWith(second);

        int c = distanceComparator.compare(firstEntity, secondEntity);

        assertEquals(0, c);
    }

    @Test
    public void testCompareFirstLessThanSecond() {
        LocationComponent first = new LocationComponent();
        first.setWorldPosition(new Vector3f(5, 0, 0));
        EntityRef firstEntity = createFakeEntityWith(first);

        LocationComponent second = new LocationComponent();
        second.setWorldPosition(new Vector3f(10, 0, 0));
        EntityRef secondEntity = createFakeEntityWith(second);

        int c = distanceComparator.compare(firstEntity, secondEntity);

        assertEquals(-1, c);
    }

    @Test
    public void testCompareFirstMoreThanSecond() {
        LocationComponent first = new LocationComponent();
        first.setWorldPosition(new Vector3f(10, 0, 0));
        EntityRef firstEntity = createFakeEntityWith(first);

        LocationComponent second = new LocationComponent();
        second.setWorldPosition(new Vector3f(5, 0, 0));
        EntityRef secondEntity = createFakeEntityWith(second);

        int c = distanceComparator.compare(firstEntity, secondEntity);

        assertEquals(1, c);
    }
}
