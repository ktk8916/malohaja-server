package malohaja.speak.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import malohaja.speak.member.domain.entity.Member;
import malohaja.speak.member.domain.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    
    //access token 유효기간 : 일단 3일
    private final long EXPIRATION_DATE = 1000L * 60 * 60 * 24 * 3;

    public TokenInfo extractUser(String token){
        Claims claims = extractAllClaims(token);
        return TokenInfo.builder()
                .id(claims.get("id", Long.class))
                .providerName(claims.get("providerName", String.class))
                .providerId(claims.get("providerId", String.class))
                .role(Role.valueOf(claims.get("role", String.class)))
                .profileImageUri((claims.get("profileImageUri", String.class)))
                .nickname((claims.get("nickname", String.class)))
                .build();
    }
    private String generateToken(
            Map<String, Object> extraClaims,
            Member member
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String generateToken(Member member) {
        HashMap<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("id", member.getId());
        extraClaims.put("providerName", member.getProviderName());
        extraClaims.put("providerId", member.getProviderId());
        extraClaims.put("profileImageUri", member.getProfileImageUri());
        extraClaims.put("nickname", member.getNickname());
        extraClaims.put("role", member.getRole().name());

        return generateToken(extraClaims, member);
    }

    public boolean isValidToken(String token){
        try{
            TokenInfo tokenInfo = extractUser(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parse(token)
                .getBody();
    }
}
