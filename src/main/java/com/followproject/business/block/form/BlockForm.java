package com.followproject.business.block.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class BlockForm {
    public static class Request{
        @Getter
        @NoArgsConstructor
        public static class Add{
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식대로 입력해주세요.")
            private String email;

            @Builder
            public Add(String email) {
                this.email = email;
            }
        }
    }
}
