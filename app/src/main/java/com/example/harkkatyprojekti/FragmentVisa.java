package com.example.harkkatyprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

  /* kysymykset:
    1:Minna vuonna kunta on perustettu? , oikea vastaus: 1550
    2:Minkä niminen on Helsingin keskustakirjasto?
    3: Mikä on Helsingin lempi nimi? stadi
    4: Milloin Helsingistä tuli pääkaupunki? vastaus: 1812
    5: Milloin Helsingin tuomiokirrko on rakennettu? vastaus: 1852
    6: Mikä on Helsingin vanhin rakennus? Sederholmin taloa
    7: Mikä on Helsingin pisinkatu? vastaus: itäväylä
    8: Mikä on Helsingin kallein asuin alue? vastaus: kaivopuisto
    9: Montako ravintola Helsingissä? vastaus: 1300
    10: Miten iso Helsinki on? oikea vastaus: 213.8 neliö km


     */


public class FragmentVisa extends Fragment {
    private TextView textQuestion;
    private Button btnOption1;
    private Button btnOption2;
    private Button btnOption3;
    private Button btnOption4;

    private String[] questions = {
            "Minna vuonna kunta on perustettu?",
            "Minkä niminen on Helsingin keskustakirjasto?",
            "Mikä on Helsingin lempi nimi?",
            "Milloin Helsingistä tuli pääkaupunki?",
            "Milloin Helsingin tuomiokirkko on rakennettu?",
            "Mikä on Helsingin vanhin rakennus?",
            "Mikä on Helsingin pisinkatu?",
            "Mikä on Helsingin kallein asuinalue?",
            "Montako ravintolaa Helsingissä?",
            "Miten iso Helsinki on?"
    };

    private String[] correctAnswers = {
            "1550",
            "Oodi",
            "Stadi",
            "1812",
            "1852",
            "Sederholmin talo",
            "Itäväylä",
            "Kaivopuisto",
            "1300",
            "213.8 neliö km"
    };

    private String[][] incorrectAnswers = {
            {"1400", "1600", "1700"},
            {"Alvar Aalto", "Kiasma", "Ateneum"},
            {"Hesa", "Helinski", "Helsset"},
            {"1900", "1709", "1950"},
            {"1800", "1900", "1750"},
            {"Uspenskin katedraali", "Hakasalmen huvila", "Kolmen sepän patsas"},
            {"Mannerheimintie", "Pasilanväylä", "Kehä I"},
            {"Töölö", "Eira", "Vallila"},
            {"800", "500", "2000"},
            {"100", "320.5", "250"}
    };

    private Random random = new Random();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visa, container, false);

        textQuestion = view.findViewById(R.id.textQuestion);
        btnOption1 = view.findViewById(R.id.btnOption1);
        btnOption2 = view.findViewById(R.id.btnOption2);
        btnOption3 = view.findViewById(R.id.btnOption3);
        btnOption4 = view.findViewById(R.id.btnOption4);

        displayQuestion();

        View.OnClickListener answerListener = v -> checkAnswer(((Button) v).getText().toString());
        btnOption1.setOnClickListener(answerListener);
        btnOption2.setOnClickListener(answerListener);
        btnOption3.setOnClickListener(answerListener);
        btnOption4.setOnClickListener(answerListener);

        return view;
    }

    private void displayQuestion() {
        textQuestion.setText(questions[currentQuestionIndex]);
        String[] answers = generateAnswers();
        btnOption1.setText(answers[0]);
        btnOption2.setText(answers[1]);
        btnOption3.setText(answers[2]);
        btnOption4.setText(answers[3]);
    }

    private String[] generateAnswers() {
        String[] answers = new String[4];
        int correctPosition = random.nextInt(4);
        // Added this so there is no duplicate answer options:
        List<String> shuffledIncorrectAnswers = new ArrayList<>(Arrays.asList(incorrectAnswers[currentQuestionIndex]));
        Collections.shuffle(shuffledIncorrectAnswers);

        answers[correctPosition] = correctAnswers[currentQuestionIndex];

        for (int i = 0, j = 0; i < 4; i++) {
            if (i != correctPosition) {
                answers[i] = shuffledIncorrectAnswers.get(j++);
            }
        }

        return answers;
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            displayQuestion();
        } else {
            showFinalScore();
        }
    }

    private void showFinalScore() {
        String finalScoreMessage = "Quiz completed!\nYour score: " + score + " out of " + questions.length;
        textQuestion.setText(finalScoreMessage);

    }
}

