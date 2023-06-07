package com.inventoryService.repositories;

import com.inventoryService.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String sukCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
