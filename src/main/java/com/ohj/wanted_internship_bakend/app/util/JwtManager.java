package com.ohj.wanted_internship_bakend.app.util;



import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.Date;

import static com.ohj.wanted_internship_bakend.app.common.BaseResponseStatus.EMPTY_JWT;
import static com.ohj.wanted_internship_bakend.app.common.BaseResponseStatus.INVALID_JWT;


@Service
public class JwtManager {

    private static final String JWT_SECRET_KEY = "UwKYibQQgkW7g-*k.ap9kje-wxBHb9wdXoBT4vnt4P3sJWt-Nu";

    /*
    JWT 생성
    @param id
    @return String
     */
    public static String createJwt(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", member.getId())
                .claim("username", member.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 3))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /*
    JWT에서 id 추출
    @return long
    @throws BaseException
     */
    public static String getId() {
        //1. JWT 추출
        String jwt = getJwt();

        // 2. JWT parsing
        Jws<Claims> claims = parseToClaims(jwt);

        // 3. id 추출
        Claims claimsBody = claims.getBody();
        try {
            return claimsBody.get("id", String.class);
        } catch (JwtException e) {
            return String.valueOf(claimsBody.get("id", String.class));
        }
    }


    private static Jws<Claims> parseToClaims(String accessToken) {
        try {
            return Jwts.parser() // Jwt가 유효하지 않다면 parseClaimsJws() 에서 JwtException이 발생한다.
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception e) {
            throw new BaseException(INVALID_JWT);
        }
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public static String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }
}
