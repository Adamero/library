package pl.edu.wat.testowy.reflection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldInformation2 {

    private String fieldName;
    private String fieldType;
    private static List<FieldInformation2> fields = new ArrayList<FieldInformation2>();

    public FieldInformation2() {
    }

    public FieldInformation2(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public static List<FieldInformation2> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return fieldName;
    }

    public static void readJson(String path) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode fieldsNode = jsonNode.get("fields");
        for (JsonNode fieldNode : fieldsNode) {
            String fieldName = fieldNode.get("fieldName").asText();
            String fieldType = fieldNode.get("fieldType").asText();
            fields.add(new FieldInformation2(fieldName, fieldType));
        }
    }
}
