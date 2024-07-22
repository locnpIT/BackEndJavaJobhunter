package vn.phuocloc.jobhunter.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Base64;

@Service
public class SecurityUtil {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${phuocloc.jwt.base64-secret}")
    private String jwtKey;

    @Value("${phuocloc.jwt.token-validity-in-seconds}")
    private String jwtKeyExpiration;

    public void createToken(org.springframework.security.core.Authentication authentication) {

    }

}
