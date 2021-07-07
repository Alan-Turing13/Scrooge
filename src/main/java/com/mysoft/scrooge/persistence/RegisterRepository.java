package com.mysoft.scrooge.persistence;

import com.mysoft.scrooge.model.Register;
import org.springframework.data.repository.CrudRepository;

public interface RegisterRepository extends CrudRepository<Register, Long> {
}
