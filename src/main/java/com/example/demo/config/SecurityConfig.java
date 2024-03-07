package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final UserRepository userRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
					//「cssやjs、imagesなどの静的リソース」をアクセス可能にする
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll()
					.requestMatchers("/registerUser","/login")
					.permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(login -> login
						//ログイン時のURLを指定
						.loginPage("/top")
						//認証後にリダイレクトする場所を指定
						.defaultSuccessUrl("/")
						.failureUrl("/login?error")
						.permitAll()
				)
				//ログアウトの設定
				.logout(logout -> logout
						//ログアウト時のURLを指定
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.permitAll()
				)
				//Remember-Meの認証を許可する
				.rememberMe();
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			//ユーザ名の検索（存在しない場合、例外をスローする）
			var user = userRepository.findByUsername(username).orElseThrow(
					() -> new UsernameNotFoundException(username + " not found"));
			
			return new User(user.getUsername(), user.getPassword(),
					AuthorityUtils.createAuthorityList("ADMIN"));
		};
	}
 
}
