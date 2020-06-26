package com.icommerce.nab.repository;

import com.icommerce.nab.entity.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository or DAO class for {@link Account}
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find account info by username
     *
     * @param username username
     * @return account info
     */
    @Query("SELECT a FROM Account a WHERE a.userName = :username")
    Account findAccountByUsername(@Param("username") String username);
}
