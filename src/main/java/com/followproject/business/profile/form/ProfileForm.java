package com.followproject.business.profile.form;

import com.followproject.business.follow.entity.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ProfileForm {
    public static class Response{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class Find{
            private Long followCount;
            private List<String> follows;

            @Builder
            public Find(Long followCount, List<String> follows) {
                this.followCount = followCount;
                this.follows = follows;
            }
        }
    }
}
