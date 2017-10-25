package me.alexand.intech.client;

import me.alexand.intech.client.controller.Controller;

import java.awt.*;

/**
 * @author asidorov84@gmail.com
 */
public class SimpleSwingClient {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> Controller.getInstance().start());
    }
}
