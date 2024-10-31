package mobiledev.unb.ca.roompersistencelab.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mobiledev.unb.ca.roompersistencelab.entity.Item

/**
 * This DAO object validates the SQL at compile-time and associates it with a method
 */
@Dao
interface ItemDao {
    // TODO
    //  Add app specific queries for:
    //   1. Insert new record
    //  Additional details can be found at https://developer.android.com/reference/kotlin/androidx/room/Insert
    //   2. Query for items by name sorted by number
    //  Additional details can be found at https://developer.android.com/reference/kotlin/androidx/room/Query
    @Insert
    fun insert(item: Item)

    @Query("SELECT * FROM item_table WHERE name = :name ORDER BY num")
    fun getItemsByName(name: String): List<Item>
}
