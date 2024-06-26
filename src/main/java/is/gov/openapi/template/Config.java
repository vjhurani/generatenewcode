package is.gov.openapi.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config implements Serializable {

    public String generateApiTests;
    public String generateModelTests;
    public String sourceFolder;
    public String useJakartaEe;
    public String library;
    public String useSpringBoot3;
    public String skipOverwrite;
    public String configHelp;
    public String reactive;
    public String implicitHeaders;
    public String useResponseEntity;
    public String basePackage;
    public String configPackage;
    public String modelPackage;
    public String apiPackage;
    public String groupId;
    public String artifactId;
    public String artifactVersion;
    public String interfaceOnly;
    public String useTags;
    public String returnSuccessCode;
    public String delegatePattern;
    public String openApiNullable;
    public String serializableModel;


}
