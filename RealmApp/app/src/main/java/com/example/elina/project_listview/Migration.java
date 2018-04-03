package com.example.elina.project_listview;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Elina on 13.10.2017.
 */

class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema taskSchema = realmSchema.get("Task");
            // Add the complete state boolean field
            taskSchema.addField("complete", Boolean.class, FieldAttribute.REQUIRED);
        }
        oldVersion++;
    }
}

