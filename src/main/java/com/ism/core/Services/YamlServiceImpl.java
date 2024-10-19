package com.ism.core.Services;

import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException; 
import java.util.Map;

public class YamlServiceImpl implements YamlService {

    private String path="META-INF/config.yaml";

    @Override
    public Map<String, Object> loadYaml() {
      return this.loadYaml(path);
    }
    
    @Override
    public Map<String, Object> loadYaml(String path) {
        Yaml yaml = new Yaml();
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("Fichier non trouvé : " + path);
                }
                return yaml.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException("Échec du chargement du fichier YAML : " + path, e);
        }
    }

}
