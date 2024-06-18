package me.yeeunhong.waitingforbooking.repository

import me.yeeunhong.waitingforbooking.domain.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long> {
    fun findByStoreName(name: String): List<Store>
}