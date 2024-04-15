package is.gov.openapi.template;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String outputFolder = "c:\\outputFolder\\";
        if(args != null && args.length > 0){
            outputFolder = args[0];
        }
        var url = ClassLoader.getSystemResource("").getPath();
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(url + "open-api/config_updated.json"),
         Config.class);
        File directoryPath = new File(url + "open-api/yaml");
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        String basePackage = "irs.gov.webapps";
        for (File file : filesList) {
            config.setBasePackage(basePackage);
            config.setConfigPackage(basePackage + "." + file.getName() + ".config");
            config.setModelPackage(basePackage + "." + file.getName() + ".model");
            config.setApiPackage(basePackage + "." + file.getName() + ".api");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(config);
            Gson gson = new Gson();
           // gson.toJson(config, new FileWriter(url + "open-api/config.json"));
            FileWriter fw = new FileWriter(url + "open-api/config.json");
            gson.toJson(config, fw);
            fw.close();
            //String baseFilePath =  url.substring(1).replace("/","\\") + "open-api\\";
//            String jarFilePath = baseFilePath + "openapi-generator-cli.jar ";
//            String yamlFile = baseFilePath + file.getName();
//            String configFilePath = baseFilePath + "config.json";
//            String batchFilePath = baseFilePath + "generateOpenApi.bat";
//            String outPutFolderPath = outputFolder+ file.getName().replace(".yaml", "");
            String baseFilePath =  url .substring(1).replace("/","\\") + "open-api\\";
            String jarFilePath =  "openapi-generator-cli.jar ";
            String yamlFile =  file.getName();
            String configFilePath =  "config.json";
            String batchFilePath =  baseFilePath + "generateOpenApi.bat";
            String outPutFolderPath = outputFolder+ file.getName().replace(".yaml", "");

            String comm = "java -jar " + jarFilePath + "   generate -g spring-boot -i " + yamlFile  + " -o " + outPutFolderPath + " -c " + configFilePath;
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(batchFilePath));
            myWriter.write(comm);
            myWriter.newLine();
            myWriter.write("exit");
            myWriter.close();


            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "generateOpenApi.bat");
            processBuilder.directory(new File(url + "open-api"));
            //String comm = "  -g spring -i " + file.getName()   + " -o " + outputFolder+ file.getName().replace(".yaml", "") + " -c config.json ";
            //processBuilder.command("./generateOpenApi.bat");
            //processBuilder.command("java" ," -jar " , jarFilePath , " generate ", " -g spring -i " ,yamlFile , " -o " , outPutFolderPath , " -c " , configFilePath);
            try {
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();


                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null)
                    System.out.println("tasklist: " + line);
                process.waitFor();
                int exit = process.waitFor();
            }
            catch (Exception ex){
                System.out.println(ex);
            }
            File fileToDelete = new File(configFilePath);
            fileToDelete.delete();
            fileToDelete =  new File(batchFilePath);
           fileToDelete.delete();
        }
    }

    public static void executeJar( List<String> args) throws Exception {
        // Create run arguments for the


        try {
            final Runtime re = Runtime.getRuntime();
            //final Process command = re.exec(cmdString, args.toArray(new String[0]));
            final Process command = re.exec(args.toArray(new String[0]));
            BufferedReader error = new BufferedReader(new InputStreamReader(command.getErrorStream()));
            BufferedReader op = new BufferedReader(new InputStreamReader(command.getInputStream()));
            // Wait for the application to Finish
            command.waitFor();
            int exitVal = command.exitValue();


        } catch (final IOException | InterruptedException e) {
            throw new Exception(e);
        }
    }

    }
