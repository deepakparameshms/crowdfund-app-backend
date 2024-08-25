package com.auth.jwtserver.service;

import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.Transaction;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.TransactionDto;
import com.auth.jwtserver.dto.TransactionResponseDto;
import com.auth.jwtserver.exception.BadInputException;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;
import com.auth.jwtserver.repository.ProjectRepository;
import com.auth.jwtserver.repository.TransactionRepository;
import com.auth.jwtserver.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
     @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TransactionResponseDto recordPayment(TransactionDto transactionDto) {

        validateTransactionDto(transactionDto);
        Transaction transaction = new Transaction();

        // Validate user and project
        User user = userRepository.findById(transactionDto.getUserId())
            .orElseThrow(() -> new UserNotFoundException());
        Project project = projectRepository.findById(transactionDto.getProjectId())
            .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        // Set Transaction fields
        transaction.setUser(user);
        transaction.setProject(project);
        transaction.setPaid(transactionDto.isPaid());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionId(transactionDto.getTransactionId());
        transaction.setPaymentId(transactionDto.getPaymentId());
        transaction.setSignature(transactionDto.getSignature());
        transaction.setPaymentService(transactionDto.getPaymentService());
        transaction.setPaymentServiceMessage(transactionDto.getPaymentServiceMessage());
        transaction.setPaymentMode(transactionDto.getPaymentMode());
        transaction.setCurrencyType(transactionDto.getCurrencyType());
        transaction.setDate(new Date());

        if(transaction.isPaid()){
            double currentAmount = project.getCurrentAmount() + transaction.getAmount();
            project.setCurrentAmount(currentAmount);
            if(currentAmount >= project.getAskAmount()){
                project.setAchieved(true);
            }
            project.setDonations(project.getDonations() + 1);
        }

        try{
            Transaction savedTransaction = transactionRepository.save(transaction);
            
            if(transactionDto.isPaid()){
                projectRepository.save(project);
            }

            return convertToResponseDto(savedTransaction);


        } catch (Exception e){
            throw new UpdateFailedException("Failed to record the payment", e);
        }
    }

    
    public List<TransactionResponseDto> getUserTransaction(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDto> getProjectTransaction(String projectId){
        List<Transaction> transactions = transactionRepository.findByProjectId(projectId);
        return transactions.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    private TransactionResponseDto convertToResponseDto(Transaction transaction) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(transaction.getId());
        responseDto.setPaid(transaction.isPaid());
        responseDto.setAmount(transaction.getAmount());
        responseDto.setUserId(transaction.getUser().getId());
        responseDto.setProjectId(transaction.getProject().getId());
        responseDto.setPaymentMode(transaction.getPaymentMode());
        responseDto.setTransactionId(transaction.getTransactionId());
        transaction.setPaymentId(transaction.getPaymentId());
        transaction.setSignature(transaction.getSignature());
        transaction.setPaymentService(transaction.getPaymentService());
        transaction.setPaymentServiceMessage(transaction.getPaymentService());
        transaction.setPaymentMode(transaction.getPaymentMode());
        transaction.setCurrencyType(transaction.getCurrencyType());
        responseDto.setDate(transaction.getDate());
        return responseDto;
    }

    private void validateTransactionDto(TransactionDto transactionDto){
        if(transactionDto.getUserId() == null || transactionDto.getUserId().isEmpty()){
            throw new BadInputException("User Id is required");
        }
        if(transactionDto.getProjectId() == null || transactionDto.getProjectId().isEmpty()){
            throw new BadInputException("Project Id is required");
        }
        if(transactionDto.getAmount() <= 10){
            throw new BadInputException("Amount should be greater than 10");
        }
        if(transactionDto.getTransactionId() == null || transactionDto.getTransactionId().isEmpty()){
            throw new BadInputException("Transaction Id is required");
        }
        if(transactionDto.getPaymentId() == null || transactionDto.getPaymentId().isEmpty()){
            throw new BadInputException("Payment Id is required");
        }
        if(transactionDto.getSignature() == null || transactionDto.getSignature().isEmpty()){
            throw new BadInputException("Signature is required");
        }
        if(transactionDto.getPaymentService() == null || transactionDto.getPaymentService().isEmpty()){
            throw new BadInputException("Payment Service is required");
        }
        if(transactionDto.getPaymentServiceMessage() == null || transactionDto.getPaymentServiceMessage().isEmpty()){
            throw new BadInputException("Payment Service Message is required");
        }
        if(transactionDto.getPaymentMode() == null || transactionDto.getPaymentMode().isEmpty()){
            throw new BadInputException("Payment Mode is required");
        }
        if(transactionDto.getCurrencyType() == null || transactionDto.getCurrencyType().isEmpty()){
            throw new BadInputException("Currency Type is required");
        }
    }
}
