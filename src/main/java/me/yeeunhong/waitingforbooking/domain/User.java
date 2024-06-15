package me.yeeunhong.waitingforbooking.domain;

//
//@Table(name = "users")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@AllArgsConstructor
//@Builder
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false)
//    private Long id;
//
//    @Column(name = "username", unique = true)
//    private String username;
//
//    @Column(name = "password")
//    private String password;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//    @Override
//    public String getUsername() {
//        return username;
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
