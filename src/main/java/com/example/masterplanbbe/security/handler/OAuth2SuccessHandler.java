package com.example.masterplanbbe.security.handler;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.security.dto.MemberInfoDTO;
import com.example.masterplanbbe.security.jwt.JwtService;
import com.example.masterplanbbe.security.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String userId = userDetails.getUsername();
        MemberRoleEnum role;
        Date date =new Date();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String roleString = auth.getAuthority();

        if (roleString.equals(MemberRoleEnum.ADMIN.getRole())) role = MemberRoleEnum.ADMIN;
        else if (roleString.equals(MemberRoleEnum.USER.getRole())) role = MemberRoleEnum.USER;
        else throw new IllegalArgumentException("지정된 권한 외의 값 오류");

        String accessToken = jwtService.createToken(userId, role, date);
        response.addHeader(AUTHORIZATION_HEADER, URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"));

        Member member = ((UserDetailsImpl) authentication.getPrincipal()).getMember();
        MemberInfoDTO dto = new MemberInfoDTO(member);
        ApiResponse<MemberInfoDTO> apiResponse = ApiResponse.ok("로그인에 성공하였습니다.", dto);

        sendResponseMsg(response, HttpStatus.OK.value(), apiResponse);
    }

    private void sendResponseMsg(HttpServletResponse response, int statusCode, Object responseBody) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(responseBody));
            writer.flush();
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }
}
