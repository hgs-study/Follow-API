package com.followproject.business.account.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AccountForm {
    public static class Request{
        @Getter
        @NoArgsConstructor
        public static class Join{
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식대로 입력해주세요.")
            private String email;

            @NotBlank(message = "비밀번호를 입력해주세요.")
            @Pattern(message = "비밀번호는 최소 8자 최대20자로 영문, 숫자, 특수문자를 포함해야합니다.",
                     regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$")
            private String password;

            @Builder
            public Join(String email, String password) {
                this.email = email;
                this.password = password;
            }
        }

        @Getter
        @Setter
        public static class Login {
            private String email;
            private String password;
        }
    }

    public static class Response{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class Find{
            private Long id;
            private String email;
            private String password;

            @Builder
            public Find(Long id, String email, String password) {
                this.id = id;
                this.email = email;
                this.password = password;
            }
        }
    }
}
