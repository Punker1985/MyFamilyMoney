package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface TransfersRepository extends CrudRepository<Transfer, Long> {
}
