package com.jpan.retail.repo;

import com.jpan.retail.entity.AOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<AOrder, Long> {

}
