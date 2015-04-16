package com.greatbit.xgn.console.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoginUserRepository extends PagingAndSortingRepository<LoginUser, Long> {
    LoginUser findByUsername(String username);
}
