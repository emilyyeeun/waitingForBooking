package me.yeeunhong.waitingforbooking.domain

import lombok.NoArgsConstructor
import me.yeeunhong.waitingforbooking.labels.StoreType
import javax.persistence.*

@Entity
@Table(name ="stores")
class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long = 0

    @Column(name = "storeType", unique = true, nullable = false)
    var storeType: StoreType ?= null

    @Column(name = "storeName", nullable = false)
    var storeName: String ?= null
}