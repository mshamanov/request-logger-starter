package com.mash.requestexample.model;

import com.mash.requestexample.controller.ExampleController;
import lombok.Data;

/**
 * Class to represent a user, used as a DTO for the {@link ExampleController}
 *
 * @author Mikhail Shamanov
 */
@Data
public class User {
    private String firstName;
    private String lastName;
}
