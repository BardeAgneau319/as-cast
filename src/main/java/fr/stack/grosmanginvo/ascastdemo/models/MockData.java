package fr.stack.grosmanginvo.ascastdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MockData {
    private String source;
    private List<String> path;
    private String text;
}
