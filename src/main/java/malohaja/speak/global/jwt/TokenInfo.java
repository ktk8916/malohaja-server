package malohaja.speak.global.jwt;

import lombok.Builder;
import lombok.Getter;
import malohaja.speak.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class TokenInfo implements UserDetails {

    private Long id;
    private String providerName;
    private String providerId;
    private Role role;

    @Builder
    public TokenInfo(Long id, String providerName, String providerId, Role role) {
        this.id = id;
        this.providerName = providerName;
        this.providerId = providerId;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
