package me.yeeunhong.waitingforbooking.repository

import me.yeeunhong.waitingforbooking.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findByStoreName(name: String): List<Store>
}