package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Account;
import com.example.MyFamilyMoney.models.Operations;
import com.example.MyFamilyMoney.models.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface TransfersRepository extends CrudRepository<Transfer, Long> {
    Iterable<Transfer> findAllByAccountSpending(Account account);
    Iterable<Transfer> findAllByAccountReceipt(Account account);
}
