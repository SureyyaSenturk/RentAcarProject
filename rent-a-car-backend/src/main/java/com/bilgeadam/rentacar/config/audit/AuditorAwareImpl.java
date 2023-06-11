package com.bilgeadam.rentacar.config.audit;

import com.bilgeadam.rentacar.config.MyUserDetails;
import java.util.Optional;

import com.bilgeadam.rentacar.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * @return Konfigüre edilmiş Auditor döner
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()||authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        return Optional.ofNullable(((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }
}
