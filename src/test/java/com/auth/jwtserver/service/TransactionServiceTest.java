package com.auth.jwtserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.Transaction;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.PaymentInfoDto;
import com.auth.jwtserver.dto.TransactionDto;
import com.auth.jwtserver.dto.TransactionResponseDto;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;
import com.auth.jwtserver.repository.ProjectRepository;
import com.auth.jwtserver.repository.TransactionRepository;
import com.auth.jwtserver.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    private TransactionDto transactionDto;
    private User user;
    private Project project;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize the mock objects and test data

        user = new User("testUser", "test@gmail.com", "testPassword");

        project = new Project();
        project.setId("project123");
        project.setAskAmount(1000.0);
        project.setCurrentAmount(500.0);

        transactionDto = new TransactionDto();
        transactionDto.setUserId("user123");
        transactionDto.setProjectId("project123");
        transactionDto.setAmount(200.0);
        transactionDto.setPaid(true);
        transactionDto.setTransactionType("DONATION");
        
        PaymentInfoDto paymentInfoDto = new PaymentInfoDto();
        paymentInfoDto.setPaymentId("payment123");
        paymentInfoDto.setSignature("signature");
        paymentInfoDto.setPaymentService("service");
        paymentInfoDto.setPaymentServiceMessage("message");
        paymentInfoDto.setPaymentMode("mode");
        paymentInfoDto.setCurrencyType("USD");

        transactionDto.setPaymentInfo(paymentInfoDto);

        transaction = new Transaction();
        transaction.setId("trans123");
        transaction.setUser(user);
        transaction.setProject(project);
        transaction.setAmount(200.0);
        transaction.setPaid(true);
    }

    @Test
    public void testRecordPaymentSuccess() {
        // Setup mock behavior
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(projectRepository.findById("project123")).thenReturn(Optional.of(project));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        
        TransactionResponseDto response = transactionService.recordPayment(transactionDto);

        // Validate the response
        assertNotNull(response);
        assertEquals("trans123", response.getId());
        assertTrue(response.isPaid());
        assertEquals(200.0, response.getAmount());

        // Verify interactions
        verify(userRepository, times(1)).findById("user123");
        verify(projectRepository, times(1)).findById("project123");
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testRecordPaymentUserNotFound() {
        // Setup mock behavior
        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        // Expect an exception
        assertThrows(UserNotFoundException.class, () -> {
            transactionService.recordPayment(transactionDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findById("user123");
        verify(projectRepository, never()).findById(anyString());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void testRecordPaymentProjectNotFound() {
        // Setup mock behavior
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(projectRepository.findById("project123")).thenReturn(Optional.empty());

        // Expect an exception
        assertThrows(ProjectNotFoundException.class, () -> {
            transactionService.recordPayment(transactionDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findById("user123");
        verify(projectRepository, times(1)).findById("project123");
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void testRecordPaymentWithExceptionDuringSave() {
        // Setup mock behavior
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(projectRepository.findById("project123")).thenReturn(Optional.of(project));
        when(transactionRepository.save(any(Transaction.class))).thenThrow(new RuntimeException("Database error"));

        // Expect an exception
        assertThrows(UpdateFailedException.class, () -> {
            transactionService.recordPayment(transactionDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findById("user123");
        verify(projectRepository, times(1)).findById("project123");
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}

