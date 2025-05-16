package com.example.demo.service;

import com.example.demo.model.BaseType;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class VocabularyService {
    private final DeclinationsRepository declinationsRepository;
    private final HundredsRepository hundredsRepository;
    private final TensRepository tensRepository;
    private final NumbersRepository numbersRepository;
    private final DigitsRepository digitsRepository;

    public VocabularyService(DeclinationsRepository declinationsRepository,
                             HundredsRepository hundredsRepository,
                             TensRepository tensRepository,
                             NumbersRepository numbersRepository,
                             DigitsRepository digitsRepository) {
        this.declinationsRepository = declinationsRepository;
        this.hundredsRepository = hundredsRepository;
        this.tensRepository = tensRepository;
        this.numbersRepository = numbersRepository;
        this.digitsRepository = digitsRepository;
    }

    protected String translateNumber(int number, int count, NumberPartType type) {
        // Переменная, для хранения числа сотен. Пример: 123, hundredsPart = 1.
        int hundredsPart = number / 100;
        // Переменная для хранения числа десятков и единиц. Пример: 123, tensAndUnits = 23.
        int tensAndUnits = number % 100;
        // Переменная для хранения числа десятков. Пример: 123, tensPart = 2.
        int tensPart = tensAndUnits / 10;
        // Переменная для хранения числа единиц. Пример: 123, tensPart = 2.
        int unitsPart  = tensAndUnits % 10;

        // Временная переменная для хранения результата перевода
        String result = "";

        // Проверка на наличие сотен, при наличии сотен к результату добавляется соответствующий переод
        if(hundredsPart != 0) {
            result += hundreds.get(hundredsPart) + " ";
        }

        /*
         * Проверка исключительной ситуации (числа от 10 до 19 нельзя собрать по частям).
         * Иначе число собирается по частям. Пример: 21 -> двадцать + один.
         */
        if (tensAndUnits >= 10 && tensAndUnits <= 19){
            result += numbers.get(tensAndUnits) + " " + declination.getIntDeclination(tensAndUnits, count);
            return result.trim();

        } else {
            // Дополнительная проверка, что число, представляющее количество десятков не указывает на 10.
            if (tensPart >= 2) {
                result += tens.get(tensPart) + " ";
            }
            if (unitsPart != 0) {
                // Проверка числа, представляющего разряд числа. В зависимости от этого определяется результат.
                switch (count) {
                    case 1,2,3:
                        // Проверка принадлежность передаваемой части к дробной части числа.
                        if(type == NumberPartType.FRACTIONAL) {
                            if(unitsPart == 1) result += "одна";
                            else if(unitsPart == 2) result += "две";
                            else  result += digits.get(unitsPart);
                            return result.trim();
                        }

                        result += digits.get(unitsPart);
                        return result.trim();

                    case 4, 5, 6:
                        /*
                         * Случай, где передаваемая часть числа представляет разряд тысяч.
                         * Выполняется соответсвующий перевод.
                         */
                        if (unitsPart == 1) result += "одна" + " ";
                        else if (unitsPart == 2) result += "две" + " ";
                        else result += digits.get(unitsPart) + " ";
                        break;

                    default:
                        result += digits.get(unitsPart) + " ";
                        break;
                }
            }
        }
        //Запись в строку результата
        result += declination.getIntDeclination(unitsPart != 0 ? unitsPart : tensAndUnits, count);
        return result.trim();
    }

    /**
     * Возвращает склонение дробной части числа.
     *
     * @param lastDigit последняя цифра дробной части.
     * @param countToPass количество разрядов для склонения.
     * @return строковое представление дробной части числа с корректным склонением.
     */
    protected String getFractionalDeclination(int lastDigit, int countToPass) {
        return declination.getFractionalDeclination(lastDigit, countToPass);
    }
}
