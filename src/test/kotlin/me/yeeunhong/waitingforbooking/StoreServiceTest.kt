package me.yeeunhong.waitingforbooking


import me.yeeunhong.waitingforbooking.domain.Store
import me.yeeunhong.waitingforbooking.labels.StoreType
import me.yeeunhong.waitingforbooking.repository.StoreRepository
import me.yeeunhong.waitingforbooking.service.StoreService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class StoreServiceTest {

    private var storeRepository = mock<StoreRepository>()
    private var storeService = StoreService(storeRepository)

    @Test
    fun `test create store`() {
        //given
        val store = Store();
        store.id = 1L
        store.storeType = StoreType.RESTAURANT
        store.storeName = "예니의 식당"

        //when
        whenever(storeRepository.save(any<Store>())).thenReturn(store)

        //do
        val createdStore = storeService.createStore(StoreType.RESTAURANT, "예니의 식당")

        //then
        assertEquals(store, createdStore)
    }

    @Test
    fun `test update store name`(){
        //given
        val value = "예니의 스토어"
        val store = Store()
        store.id = 1

        //when
        whenever(storeRepository.findById(store.id)).thenReturn(Optional.of(store))
        whenever(storeRepository.save(store)).thenReturn(store)

        //do
        val updatedStore = storeService.updateStoreName(store.id, value)

        //then
        assertThat(updatedStore.storeName).isEqualTo(value)
    }

}

