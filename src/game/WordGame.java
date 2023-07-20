package game;

import DictionaryAPI.DictionaryAPIHandler;
import gui.WordGameGUI;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class WordGame {
    private DictionaryAPIHandler apiHandler;
    private char currentAlphabet;
    private Set<String> usedWords;
    private Timer timer;
    private int secondsLeft;
    private final WordGameGUI wordGameGUI;
    private int level;
    private int score;

    public WordGame() {
        wordGameGUI = new WordGameGUI();
        setupEventListeners();
        apiHandler = new DictionaryAPIHandler();
    }

    private void setupEventListeners() {
        wordGameGUI.getStartButton().addActionListener(e -> {
            wordGameGUI.getStartButton().setEnabled(false);
            wordGameGUI.getSubmitButton().setEnabled(true);
            level = JOptionPane.showOptionDialog(
                    null, "Select a level:", "Level", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new String[]{"Easy", "Medium", "Hard"}, "Easy");
            score = 0;
            wordGameGUI.getScoreLabel().setText("Score: " + score);
            startGame();
        });

        wordGameGUI.getSubmitButton().addActionListener(e -> submitWord());
    }

    private void startGame() {
        secondsLeft = getLevelTime();
        wordGameGUI.getLevelLabel().setText("Level: " + getLevelName());
        wordGameGUI.getTimerLabel().setText(String.valueOf(secondsLeft));

        generateRandomAlphabet();
        wordGameGUI.getInputField().setText("");
        wordGameGUI.getInputField().requestFocus();

        usedWords = new HashSet<>();

        timer = new Timer(1000, e -> {
            secondsLeft--;
            wordGameGUI.getTimerLabel().setText(String.valueOf(secondsLeft));

            if (secondsLeft == 0) {
                timer.stop();
                wordGameGUI.getSubmitButton().setEnabled(false);
                JOptionPane.showMessageDialog(null, "Time's up! Your score is: " + score);
                wordGameGUI.getStartButton().setEnabled(true);
                cleanup();
            }
        });
        timer.start();
    }

    private void submitWord() {
        String word = wordGameGUI.getInputField().getText().trim().toLowerCase();

        if (word.length() <= 3) {
            JOptionPane.showMessageDialog(null, "Please enter a word with more than 3 characters.");
            return;
        }

        if (!isWordValid(word)) {
            JOptionPane.showMessageDialog(null, "The word must start with the given alphabet and contain only letters.");
            wordGameGUI.getInputField().setText("");
            return;
        }

        if (usedWords.contains(word)) {
            JOptionPane.showMessageDialog(null, "You already entered this word.");
            wordGameGUI.getInputField().setText("");
            return;
        }

        if (!apiHandler.wordExists(word)) {
            JOptionPane.showMessageDialog(null, "The word is not valid.");
            wordGameGUI.getInputField().setText("");
            return;
        }

        usedWords.add(word); // Add the word to the Set
        score++;
        wordGameGUI.getScoreLabel().setText("Score: " + score);
        wordGameGUI.getInputField().setText("");
        generateRandomAlphabetFromWord(word);
        wordGameGUI.getInputField().requestFocus();

        secondsLeft = getLevelTime(); // Restart the timer based on the level
        wordGameGUI.getTimerLabel().setText(String.valueOf(secondsLeft));
        timer.restart();
    }

    private boolean isWordValid(String word) {
        if (word.charAt(0) != currentAlphabet) {
            return false;
        }
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private void generateRandomAlphabet() {
        Random random = new Random();
        currentAlphabet = (char) (random.nextInt(26) + 'a');
        wordGameGUI.getAlphabetLabel().setText(String.valueOf(currentAlphabet));
    }

    private void generateRandomAlphabetFromWord(String word) {
        Random random = new Random();
        int index = random.nextInt(word.length());
        currentAlphabet = Character.toLowerCase(word.charAt(index));
        wordGameGUI.getAlphabetLabel().setText(String.valueOf(currentAlphabet));
    }

    private int getLevelTime() {
        return switch (level) {
            case 1 -> 8;
            case 2 -> 5;
            default -> 10;
        };
    }

    private String getLevelName() {
        return switch (level) {
            case 1 -> "Medium";
            case 2 -> "Hard";
            default -> "Easy";
        };
    }

    private void cleanup() {
        usedWords.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::new);
    }
}
