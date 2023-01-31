package com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessMessage {
    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
