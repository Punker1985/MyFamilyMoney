package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
