package me.alexand.intech.client.view;

import me.alexand.intech.client.controller.Controller;
import me.alexand.intech.client.model.ButtonsNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author asidorov84@gmail.com
 */
public class MainForm extends JFrame {
    private static final String WINDOW_CAPTION = "Simple client";
    private static final String SERVER_ADDRESS_QUESTION = "Введите адрес сервера:\n (по умолчанию \"localhost:7777\")";
    private static final String INPUT_SRV_ADDR_CAPTION = "Подключиться к...";
    private static final String USER_ID_QUESTION = "Введите свой ID (только цифры):";
    private static final String INPUT_ID_CAPTION = "Авторизация";

    private static final int SCREEN_WIDTH = 300;
    private static final int SCREEN_HEIGHT = 200;
    private static final int BUTTONS_PANEL_HEIGHT = 200;

    private final Controller controller = Controller.getInstance();

    private final JTextArea screen = new JTextArea("");
    private final JLabel statusMsg = new JLabel("Status bar:");
    private final List<JButton> buttons = new ArrayList<>();

    public MainForm() throws HeadlessException {
        super(WINDOW_CAPTION);

        initFormComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        setMinimumSize(getPreferredSize());
    }

    public void showForm() {
        String input;

        do {
            input = JOptionPane.showInputDialog(this,
                    SERVER_ADDRESS_QUESTION,
                    INPUT_SRV_ADDR_CAPTION,
                    JOptionPane.INFORMATION_MESSAGE);
        } while (!controller.connect(input));

        do {
            input = JOptionPane.showInputDialog(this,
                    USER_ID_QUESTION,
                    INPUT_ID_CAPTION,
                    JOptionPane.INFORMATION_MESSAGE);
        } while (input == null || "".equals(input));

        controller.sendMessage(input);

        setVisible(true);
    }

    public void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void initFormComponents() {
        JPanel msgPanel = new JPanel();

        screen.setEditable(false);
        screen.setBorder(BorderFactory.createEmptyBorder());
        screen.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);
        screen.setLineWrap(true);
        screen.setWrapStyleWord(true);

        msgPanel.add(screen);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 3));
        buttonsPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, BUTTONS_PANEL_HEIGHT));

        AbstractAction actionListener = new ButtonsClickListener();

        for (ButtonsNames b : ButtonsNames.values()) {
            String buttonText = b.toString();

            JButton jButton = new JButton(buttonText);

            switch (buttonText) {
                case "*":
                    //TODO
                    jButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).
                            put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), buttonText);
                    break;
                case "#":
                    //TODO
                    jButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).
                            put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMBER_SIGN, 0), buttonText);
                    break;
                default:
                    jButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(buttonText), buttonText);
                    jButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD" + buttonText), buttonText);
            }

            jButton.getActionMap().put(buttonText, actionListener);

            jButton.addActionListener(actionListener);
            buttons.add(jButton);
            buttonsPanel.add(jButton);
        }



        getContentPane().add(BorderLayout.NORTH, msgPanel);
        getContentPane().add(BorderLayout.CENTER, buttonsPanel);
        getContentPane().add(BorderLayout.SOUTH, statusMsg);

        pack();
    }

    private void showStatus(String msg) {
        statusMsg.setText(msg);
    }

    public void showMessage(String message) {
        screen.setText(message);
    }

    private class ButtonsClickListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = ButtonsNames.getByName(((JButton)e.getSource()).getText()).name();
            showStatus("Send to server: " + msg);
            controller.sendMessage(msg);
        }
    }
}
