package com.followproject.business.follow.form;

import com.followproject.business.follow.entity.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class FollowForm {
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

    public static class Response{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class Find{
            private Long followCount;
            private List<Follow> follows;

            @Builder
            public Find(Long followCount, List<Follow> follows) {
                this.followCount = followCount;
                this.follows = follows;
            }
        }
    }
}
