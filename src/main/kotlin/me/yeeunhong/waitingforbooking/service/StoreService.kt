package me.yeeunhong.waitingforbooking.service

import me.yeeunhong.waitingforbooking.domain.Store
import me.yeeunhong.waitingforbooking.labels.StoreType
import me.yeeunhong.waitingforbooking.repository.StoreRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class StoreService (
    private val storeRepository: StoreRepository
) {

    @Transactional
    fun createStore(storeType: StoreType?, storeName: String?) : Store {
        requireNotNull(storeType) { "storeType must not be null" }
        requireNotNull(storeName) { "storeName must not be null" }
        val store = Store()
        store.storeType = storeType
        store.storeName = storeName
        return storeRepository.save(store)
    }

    fun getStore(storeId: Long) : Store {
        return storeRepository.findById(storeId).orElseThrow()
    }

    fun getAllStores() : Iterable<Store> {
        return storeRepository.findAll()
    }

    @Transactional
    fun updateStoreName(storeId: Long, value: String) : Store {
        val store = storeRepository.findById(storeId).orElseThrow()
        store.storeName = value;
        return storeRepository.save(store)
    }
}