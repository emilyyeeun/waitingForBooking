package me.yeeunhong.waitingforbooking.service

import lombok.RequiredArgsConstructor
import me.yeeunhong.waitingforbooking.domain.Store
import me.yeeunhong.waitingforbooking.labels.StoreType
import me.yeeunhong.waitingforbooking.repository.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Required
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
open class StoreService @Autowired constructor (
    private var storeRepository: StoreRepository
) {

    @Transactional
    fun createStore(storeType: StoreType, storeName: String) : Store {
        val store = Store().apply {
            this.storeType = storeType
            this.storeName = storeName
        }
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