package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Account;
import com.example.MyFamilyMoney.models.Operations;
import com.example.MyFamilyMoney.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationsRepository extends CrudRepository<Operations, Long> {
    Iterable<Operations> findAllByAccount(Account account);
}
