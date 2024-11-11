package ru.ylab.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.repository.StatusRepository;

@ExtendWith(MockitoExtension.class)
class StatusServiceTest {
    @InjectMocks
    private StatusService statusService;


    @Mock
    private StatusRepository statusRepository;

    @Test
    void createStatus() {

    }
}