package mobiledev.unb.ca.roompersistencelab.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val itemRepository: ItemRepository = ItemRepository(application)
    
    // TODO
    //  Add mapping calls between the UI and Database

    fun insert(name: String, num: Int) {
        val item = Item(name, num)
        itemRepository.insert(item)
    }

    fun search(name: String): List<Item>? {
        return itemRepository.search(name)
    }

}