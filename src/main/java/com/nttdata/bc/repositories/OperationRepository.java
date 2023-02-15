package com.nttdata.bc.repositories;

import com.nttdata.bc.models.Operation;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationRepository implements PanacheRepositoryBase<Operation, Integer> {

}
