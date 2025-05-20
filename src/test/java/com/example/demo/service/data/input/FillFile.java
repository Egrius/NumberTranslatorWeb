package com.example.demo.service.data.input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class FillFile {
    public static void main( String[] args ) {
        String fullOutputFileName = "randomBeforeMillion.txt";

        try {
            Random random = new Random();

            DecimalFormat df = new DecimalFormat("###.######");
            df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
            df.setMaximumFractionDigits(6);

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fullOutputFileName)));
            for(int i = 0; i < 100; i++) {
                double number = random.nextDouble(1000000000.999999999);
                String formattedNumber = df.format(number);
                out.println(formattedNumber);
            }
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
