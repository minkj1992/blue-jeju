package com.minkj1992.bluejeju.account;

import com.minkj1992.bluejeju.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
