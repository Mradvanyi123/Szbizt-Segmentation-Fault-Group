package hu.bme.sfg.catalogbackend.security;

import hu.bme.sfg.catalogbackend.util.Role;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String jwtSecret;
    private final String jwtIssuer;
    private final String jwtType;
    private final String jwtAudience;

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String jwtAudience, String jwtIssuer, String jwtSecret, String jwtType, UserRepository userRepository) {
        super(authenticationManager);
        this.jwtAudience = jwtAudience;
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.jwtType = jwtType;
        this.userRepository = userRepository;
    }

    private UsernamePasswordAuthenticationToken parseToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            String claims = token.replace("Bearer ", "");
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(jwtSecret.getBytes())
                        .build()
                        .parseClaimsJws(claims);

                String username = claimsJws.getBody().getSubject();

                if ("".equals(username) || username == null) {
                    return null;
                }

                Optional<User> user = userRepository.findByUserName(username);
                if (!user.isPresent()) {
                    return null;
                }

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                /*for (Role role : user.getRoles()) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                    authorities.add(authority);
                }*/
                authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));

                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            } catch (JwtException exception) {
                log.warn("Some exception : {} failed : {}", token, exception.getMessage());
            }
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = parseToken(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}

