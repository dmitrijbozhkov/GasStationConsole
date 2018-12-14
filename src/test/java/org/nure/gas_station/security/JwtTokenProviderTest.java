package org.nure.gas_station.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.repositories.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.nure.gas_station.model.GasStationUser;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenProviderTest {

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Token properties
    @Value("${token.settings.signKey}")
    private String signingKey;
    @Value("${token.settings.tokenValid}")
    private long tokenValid;
    @Value("${token.settings.authoritiesKey}")
    private String authoritiesKey;
    @Value("${token.settings.tokenPrefix}")
    private String tokenPrefix;
    private final String authoritiesSeparator = ",";
    // User properties
    private final String username = "matviei";
    private final String userAuthority = UserRoles.ROLE_ADMIN.getAuthority();

    @Test
    public void testValidateTokenSouldReturnUsernamePasswordAuthenticationTokenWithUserDetailsAndUthorities() {
        String authorities = StringUtils.join(Arrays.asList(userAuthority), authoritiesSeparator.charAt(0));
        String token = Jwts
                .builder()
                .setSubject(username)
                .claim(authoritiesKey, authorities)
                .signWith(SignatureAlgorithm.HS256, signingKey.getBytes())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValid * 1000))
                .compact();
        given(userRepository.findById(username)).willReturn(Optional.of(new GasStationUser(username, "pass1234", "mat", "pass", UserRoles.ROLE_ADMIN)));
        UsernamePasswordAuthenticationToken auth = jwtTokenProvider.validateToken(token);
        System.out.println(token);
        assertEquals(username, auth.getName());
        assertTrue(auth
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(userAuthority)));
    }

}
