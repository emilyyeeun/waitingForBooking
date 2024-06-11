package me.yeeunhong.waitingforbooking.service

import lombok.RequiredArgsConstructor
import me.yeeunhong.waitingforbooking.domain.User
import me.yeeunhong.waitingforbooking.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.List


@Service
@RequiredArgsConstructor
class CustomUserDetailsService @Autowired constructor(private val userRepository: UserRepository): UserDetailsService {
    private var passwordEncoder: BCryptPasswordEncoder? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }

        return createUserDetails(user)
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private fun createUserDetails(user: User): UserDetails {
        val authorities = user.roles.map { SimpleGrantedAuthority(it) }

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.username)
            .password(user.password)
            .authorities(authorities)
            .build()
    }
}

