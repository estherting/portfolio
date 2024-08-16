package com.eting.portfolio.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("app_user")
public class AppUser {

    @Id
    private Long id;
    private String name;
    private String email;
}
