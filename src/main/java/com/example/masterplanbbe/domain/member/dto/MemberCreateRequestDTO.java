package com.example.masterplanbbe.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@ToString
@AllArgsConstructor
public class MemberCreateRequestDTO {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식을 지켜야 합니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Length(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 공백 없이 영문, 한글, 숫자를 조합해서 입력해주세요")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 10, max = 16, message = "비밀번호는 10자 이상, 16자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{}|;:'\",<>.?/])[a-zA-Z0-9!@#$%^&*()_\\-+=\\[\\]{}|;:'\",<>.?/]*$",
            message = "비밀번호는 영문, 숫자, 특수문자가 모두 포함되어야 합니다.")
    private String password;

    private Boolean isAgreed;
}
