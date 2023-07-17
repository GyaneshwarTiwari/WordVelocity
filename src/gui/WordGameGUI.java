package gui;

import javax.swing.*;

public class WordGameGUI {
    private JFrame frame;
    private JLabel timerLabel;
    private JTextField inputField;
    private JButton startButton;
    private JLabel alphabetLabel;
    private JButton submitButton;
    private JLabel levelLabel;
    private JLabel scoreLabel;

    public WordGameGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Word Game");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        alphabetLabel = createLabel(150, 50, 100, 30, JLabel.CENTER);
        inputField = createTextField(100, 100, 200, 30);
        startButton = createButton("Start", 150, 150, 100, 30);
        submitButton = createButton("Submit", 150, 200, 100, 30);
        levelLabel = createLabel(10, 10, 100, 30, JLabel.LEFT);
        scoreLabel = createLabel(290, 10, 100, 30, JLabel.RIGHT);
        timerLabel = createLabel(160, 10, 100, 30, JLabel.CENTER);

        frame.setVisible(true);
    }

    private JLabel createLabel(int x, int y, int width, int height, int alignment) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(alignment);
        frame.add(label);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        frame.add(textField);
        return textField;
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        frame.add(button);
        return button;
    }

    public JLabel getAlphabetLabel() {
        return alphabetLabel;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JLabel getLevelLabel() {
        return levelLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }
}
