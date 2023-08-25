package com.rafal.IStore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    @NotEmpty(message = "First name is required")
    @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]+$", message = "Invalid first name")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]+$", message = "Invalid last name")
    private String lastName;

    @NotEmpty(message = "Address is required")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s]+\\s+[0-9]+(?:/[0-9]+)?|" +
                      "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s]+\\s*[0-9]*$", message = "Invalid address")
    private String address;

    @NotEmpty(message = "Postal code is required")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Invalid postal code")
    private String postCode;

    @NotEmpty(message = "City is required")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ. ]+$", message = "Invalid city")
    private String city;
}
