package com.auth.jwtserver.service.interfaces;

import com.auth.jwtserver.dto.TransactionDto;
import com.auth.jwtserver.dto.TransactionResponseDto;
import com.auth.jwtserver.exception.BadInputException;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;

import java.util.List;

public interface ITransactionService {

    TransactionResponseDto recordPayment(TransactionDto transactionDto) 
        throws UserNotFoundException, ProjectNotFoundException, UpdateFailedException, BadInputException;

    List<TransactionResponseDto> getUserTransaction(String userId);

    List<TransactionResponseDto> getProjectTransaction(String projectId);
}
