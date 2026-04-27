package com.lyceeande.inscription.repository;

import com.lyceeande.inscription.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    List<Parent> findByEleveId(Long eleveId);
}