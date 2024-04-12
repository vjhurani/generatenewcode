package is.gov.openapi.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        var url = ClassLoader.getSystemResource("").getPath();
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(url + "open-api/config_updated.json"), Config.class);
        File directoryPath = new File(url + "open-api/yaml");
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        String basePath = "irs.gov.webapps";
        for (File file : filesList) {
            config.setBasePackage(basePath);
            config.setConfigPackage(basePath + "." + file.getName() + ".config");
            config.setModelPackage(basePath + "." + file.getName() + ".model");
            config.setApiPackage(basePath + "." + file.getName() + ".api");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(config);
            Gson gson = new Gson();
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cd open-api");
            processBuilder.command("java -jar openapi-generator-cli.jar  generate -g spring -i " + file.getName() + ".yaml "  + "-o " + file.getName() + " -c config.json ");
            Process process = processBuilder.start();
            process.waitFor();
            File fileToDelete = new File(url + "open-api/config.json");
            fileToDelete.delete();
        }
    }
    }
