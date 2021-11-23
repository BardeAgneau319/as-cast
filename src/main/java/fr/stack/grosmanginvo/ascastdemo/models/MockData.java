package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockData {
    private String address;
    private String someField;
}
