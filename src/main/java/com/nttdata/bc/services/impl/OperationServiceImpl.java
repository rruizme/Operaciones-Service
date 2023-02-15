package com.nttdata.bc.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.nttdata.bc.clients.IAccountRestClient;
import com.nttdata.bc.enums.OperationTypeEnum;
import com.nttdata.bc.exceptions.BadRequestException;
import com.nttdata.bc.exceptions.NotFoundException;
import com.nttdata.bc.models.Operation;
import com.nttdata.bc.models.Account;
import com.nttdata.bc.models.Client;
import com.nttdata.bc.repositories.OperationRepository;
import com.nttdata.bc.services.IOperationService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;

@ApplicationScoped
public class OperationServiceImpl implements IOperationService {
    @Inject
    Logger logger;

    @Inject
    @RestClient
    IAccountRestClient accountRestClient;

    @Inject
    private OperationRepository repository;

    @Override
    public Operation insert(Operation obj) {
        obj.setCreatedAt(Instant.now());

        if (obj.getOperationType().equals(OperationTypeEnum.DE.toString())) {
            logger.info("DEPOSITO");
            if (obj.getAccountId() == null)
                throw new BadRequestException("El campo accountId es requerido.");

            Account account = this.accountRestClient.fintById(obj.getAccountId());
            account.setAmount(account.getAmount().add(obj.getAmount()));
            logger.info("DEPOSITO ::: account ::: " + account);
            this.accountRestClient.update(account.getAccountId(), account);
        } else if (obj.getOperationType().equals(OperationTypeEnum.RE.toString())) {
            logger.info("RETIRO");
            if (obj.getDebitCardId() == null)
                throw new BadRequestException("El campo debitCardId es requerido.");

            List<Account> accounts = this.accountRestClient.findDebitCardId(obj.getDebitCardId());
            logger.info("RETIRO ::: accounts ::: " + accounts);
            if (accounts.size() == 0)
                throw new NotFoundException("Las cuentas con id: " + obj.getDebitCardId() + ", no existe.");

            Optional<Account> accountOptional = accounts.stream().filter(a -> a.getIsMain() == true).findFirst();
            if (accountOptional.isPresent() == false)
                throw new BadRequestException("Para realizar un retiro, debe tener una cuenta principal.");

            Account account = accountOptional.get();
            if (account.getAmount().compareTo(obj.getAmount()) < 0) // account.get().getAmount() -> si es menor
                throw new BadRequestException("Saldo insuficiente para realizar el retiro.");

            account.setAmount(account.getAmount().subtract(obj.getAmount()));
            this.accountRestClient.update(account.getAccountId(), account);
        }

        this.repository.persist(obj);
        return obj;
    }

    @Override
    public Operation update(Operation obj) {
        return null;
    }

    @Override
    public List<Operation> findAll() {
        return this.repository.listAll();
    }

    @Override
    public Operation findById(Integer id) {
        Operation operation = this.repository.findById(id);
        if (operation == null) {
            throw new NotFoundException("La cuenta con id: " + id.toString() + ", no existe.");
        }

        return operation;
    }

    @Override
    public void delete(Integer id) {

    }
}
