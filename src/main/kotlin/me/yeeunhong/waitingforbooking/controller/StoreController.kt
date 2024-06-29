package me.yeeunhong.waitingforbooking.controller

import me.yeeunhong.waitingforbooking.domain.Store
import me.yeeunhong.waitingforbooking.dto.StoreRequest
import me.yeeunhong.waitingforbooking.labels.StoreType
import me.yeeunhong.waitingforbooking.service.StoreService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/stores")
    fun getDevices(): Iterable<Store> {
        return storeService.getAllStores()
    }

    @PostMapping("/stores")
    fun createStore(@RequestBody storeRequest: StoreRequest): Store? {
        val storeType = when (storeRequest.storeTypeInput) {
            "Restaurant" -> StoreType.RESTAURANT
            "Hairsalon" -> StoreType.HAIRSALON
            "Accomodation" -> StoreType.ACCOMODATION
            else -> null
        }

        return storeService.createStore(storeType!!, storeRequest.storeName)
    }

    @GetMapping("/stores/{storeId}")
    fun getStore(@PathVariable("storeId") storeId:Long) : Store {
        return storeService.getStore(storeId);
    }

    @PatchMapping("/stores/{storeId}/storeName")
    fun updateStoreName(
        @PathVariable("storeId") storeId: Long,
        @RequestBody storeName: String) : Store {
        return storeService.updateStoreName(storeId, storeName)
    }

}