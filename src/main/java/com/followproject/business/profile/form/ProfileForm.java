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
            private List<String> followEmails;

            @Builder
            public Find(Long followCount, List<String> followEmails) {
                this.followCount = followCount;
                this.followEmails = followEmails;
            }
        }
    }
}
