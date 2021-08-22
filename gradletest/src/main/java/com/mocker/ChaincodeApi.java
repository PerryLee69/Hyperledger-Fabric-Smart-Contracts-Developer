package main.java.com.mocker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ChaincodeApi {
    private String name;
    private String args;
    private String expectResult;
}
