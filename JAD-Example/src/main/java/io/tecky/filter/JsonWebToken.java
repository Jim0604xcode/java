package io.tecky.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JsonWebToken {
    @Autowired
    Environment env;

    public String encodeJWT(int userId){

        return JWT.create()
                .withIssuer("tecky")
                .withClaim("user", userId)
                .withExpiresAt(new Date(new Date().getTime() + 60 * 60 * 1000))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(env.getProperty("jwt.secret")));
    }

    public Map decodeJWT(String token) throws Exception {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(env.getProperty("jwt.secret"))).build().verify(token);
        if(isJWTExpired(jwt)){
            throw new Exception("token expired");
        }
        var userId = jwt.getClaim("user").asInt();
        if(userId == null){
            throw new Exception("Invalid Token");
        }
        return Map.of("userId", userId);
    }

    boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }
}
