package com.auth.jwtserver.service;

import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.Transaction;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.document.enums.TransactionType;
import com.auth.jwtserver.dto.PaymentInfoDto;
import com.auth.jwtserver.dto.TransactionDto;
import com.auth.jwtserver.dto.TransactionResponseDto;
import com.auth.jwtserver.exception.BadInputException;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;
import com.auth.jwtserver.repository.ProjectRepository;
import com.auth.jwtserver.repository.TransactionRepository;
import com.auth.jwtserver.repository.UserRepository;
import com.auth.jwtserver.service.interfaces.ITransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    
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
        transaction.setTransactionType(TransactionType.valueOf(transactionDto.getTransactionType().toUpperCase()));

        PaymentInfoDto paymentInfo = new PaymentInfoDto();
        paymentInfo.setPaymentId(transactionDto.getPaymentInfo().getPaymentId());
        paymentInfo.setSignature(transactionDto.getPaymentInfo().getSignature());
        paymentInfo.setPaymentService(transactionDto.getPaymentInfo().getPaymentService());
        paymentInfo.setPaymentServiceMessage(transactionDto.getPaymentInfo().getPaymentServiceMessage());
        paymentInfo.setPaymentMode(transactionDto.getPaymentInfo().getPaymentMode());
        paymentInfo.setCurrencyType(transactionDto.getPaymentInfo().getCurrencyType());

        transaction.setPaymentInfo(paymentInfo);
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
        responseDto.setUserName(transaction.getUser().getUsername());
        responseDto.setProjectId(transaction.getProject().getId());
        responseDto.setTransactionType(transaction.getTransactionType().toString());

        PaymentInfoDto paymentInfo = new PaymentInfoDto();
        paymentInfo.setPaymentId(transaction.getPaymentInfo().getPaymentId());
        paymentInfo.setSignature(transaction.getPaymentInfo().getSignature());
        paymentInfo.setPaymentService(transaction.getPaymentInfo().getPaymentService());
        paymentInfo.setPaymentServiceMessage(transaction.getPaymentInfo().getPaymentServiceMessage());
        paymentInfo.setPaymentMode(transaction.getPaymentInfo().getPaymentMode());
        paymentInfo.setCurrencyType(transaction.getPaymentInfo().getCurrencyType());

        responseDto.setPaymentInfo(paymentInfo);

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
        if(transactionDto.getTransactionType() == null || transactionDto.getTransactionType().toString().isEmpty()){
            throw new BadInputException("Transaction Id is required");
        }
        if(transactionDto.getPaymentInfo().getPaymentId() == null || transactionDto.getPaymentInfo().getPaymentId().isEmpty()){
            throw new BadInputException("Payment Id is required");
        }
        if(transactionDto.getPaymentInfo().getSignature() == null || transactionDto.getPaymentInfo().getSignature().isEmpty()){
            throw new BadInputException("Signature is required");
        }
        if(transactionDto.getPaymentInfo().getPaymentService() == null || transactionDto.getPaymentInfo().getPaymentService().isEmpty()){
            throw new BadInputException("Payment Service is required");
        }
        if(transactionDto.getPaymentInfo().getPaymentServiceMessage() == null || transactionDto.getPaymentInfo().getPaymentServiceMessage().isEmpty()){
            throw new BadInputException("Payment Service Message is required");
        }
        if(transactionDto.getPaymentInfo().getPaymentMode() == null || transactionDto.getPaymentInfo().getPaymentMode().isEmpty()){
            throw new BadInputException("Payment Mode is required");
        }
        if(transactionDto.getPaymentInfo().getCurrencyType() == null || transactionDto.getPaymentInfo().getCurrencyType().isEmpty()){
            throw new BadInputException("Currency Type is required");
        }
    }
}
