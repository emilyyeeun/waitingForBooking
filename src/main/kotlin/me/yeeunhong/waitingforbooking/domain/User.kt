package me.yeeunhong.waitingforbooking.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import kotlin.properties.Delegates

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long,

    @Column(name = "username", unique = true, nullable = false)
    private var username: String,

    @Column(name = "password", nullable = false)
    private var password: String,

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: List<String> = ArrayList()) : UserDetails {

    companion object {
        fun builder() = UserBuilder()
    }

    class UserBuilder {
        private var id : Long = 0;
        private lateinit var username: String
        private lateinit var password: String
        private var roles: List<String> = ArrayList()

        fun id(id: Long) = apply { this.id = id }
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun roles(roles: List<String>) = apply { this.roles = roles }

        fun build() = User(id, username, password, roles)
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { role -> SimpleGrantedAuthority(role) }
    }

    override fun getUsername() = username
    override fun getPassword() = password
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}