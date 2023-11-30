package io.tecky.filter;

//import io.tecky.JsonWebToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;


@Component
public class APIGuardFilter extends OncePerRequestFilter {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JsonWebToken jsonWebToken;
    public APIGuardFilter(JsonWebToken jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{


        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PATCH, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");

            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer")) {
                // Preflight request
                filterChain.doFilter(request, response);
                return;
            }
            String token = header.split(" ")[1];
            if(token == null){
                throw new Exception("Not exist Token in header");
            }
            Map payload = this.jsonWebToken.decodeJWT(token);
            request.setAttribute("user", payload.get("userId"));
            filterChain.doFilter(request,response);
        }catch(Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            // Preflight request
            System.out.println("1.5 preflight");
            return false;
        }

        return !request.getRequestURI().matches("^(/memos|/countries).*");
    }
}
