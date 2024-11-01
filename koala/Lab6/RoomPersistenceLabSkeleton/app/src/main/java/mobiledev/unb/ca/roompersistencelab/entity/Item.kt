package mobiledev.unb.ca.roompersistencelab.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table") // Represents a SQLite table
class Item (val name: String, val num: Int){
    // TODO
    //  Create the values for the entity
    //  NOTES:
    //      You will need to add variables for the column names of id, name, and num
    //      id is an autogenerated attribute
    //  Additional details can be found at https://developer.android.com/training/data-storage/room/defining-data
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
