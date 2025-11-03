package com.andrea.proptech.bff.web;

import com.andrea.proptech.bff.web.dto.response.MeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MeController {

    @GetMapping("/api/v1/me")
    public Mono<ResponseEntity<MeResponse>> getMe(@AuthenticationPrincipal Mono<OAuth2User> userMono) {

        return userMono
                .map(oauth2User -> {
                    String username = oauth2User.getAttribute("name");
                    if (username == null) {
                        username = oauth2User.getName();
                    }

                    Set<String> authorities = oauth2User.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());

                    MeResponse responseDto = new MeResponse(username, authorities);

                    return ResponseEntity.ok(responseDto);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
