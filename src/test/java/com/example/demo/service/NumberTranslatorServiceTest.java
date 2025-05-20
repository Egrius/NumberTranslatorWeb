package com.example.demo.service;

import com.example.demo.model.DigitsModel;
import com.example.demo.model.HundredsModel;
import com.example.demo.model.NumbersModel;
import com.example.demo.model.TensModel;
import com.example.demo.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.io.*;

@ExtendWith(MockitoExtension.class)
class NumberTranslatorServiceTest {

    @Test
    void shouldTranslateBeforeThousands() {
    }
}