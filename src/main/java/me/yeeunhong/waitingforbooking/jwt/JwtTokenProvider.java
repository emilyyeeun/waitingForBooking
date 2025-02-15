package me.yeeunhong.waitingforbooking.jwt;

//
//@Slf4j
//@Component
//public class JwtTokenProvider implements JwtTokenProviderInterface {
//    private final Key key;
//
//    // application.yml에서 secret 값 가져와서 key에 저장
//    public JwtTokenProvider(@Value("${jwt.secret_key}") String secretKey) {
//        byte[] keyBytes = Base64.decodeBase64(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    @Override
//    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
//    public JwtToken generateToken(Authentication authentication) {
//        // 권한 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + 86400000);
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith( SignatureAlgorithm.HS256, key)
//                .compact();
//
//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + 86400000))
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//
//        return JwtToken.builder()
//                .grantType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    @Override
//    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
//    public Authentication getAuthentication(String accessToken) {
//        // Jwt 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get("auth") == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        // UserDetails 객체를 만들어서 Authentication return
//        // UserDetails: interface, User: UserDetails를 구현한 class
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }
//
//    @Override
//    // 토큰 정보를 검증하는 메서드
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }
//        return false;
//    }
//
//
//    // accessToken
//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(accessToken)
//                    .getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }
//
//}
