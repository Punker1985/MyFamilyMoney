package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.ItemGroup;
import com.example.MyFamilyMoney.models.TypeOperation;
import com.example.MyFamilyMoney.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ItemGroupRepository extends CrudRepository<ItemGroup, Long> {
//   Iterable<ItemGroup> findAllByTypeOperation(TypeOperation typeOperation);
//   Iterable<ItemGroup> findByUserAndTypeOperation(User user, TypeOperation typeOperation);
//   Iterable<ItemGroup> findByUserAndTypeOperation(User user, TypeOperation typeOperation);
//   Iterable<ItemGroup> findAllByUserAndTypeOperation(List<User> users, TypeOperation typeOperation);
   Iterable<ItemGroup> findByUserOrUserAndTypeOperation(User user, User user1, TypeOperation typeOperation);
   Iterable<ItemGroup> findByUserOrUser(User user, User user1);
   Iterable<ItemGroup> findByUser(User user);

   }
