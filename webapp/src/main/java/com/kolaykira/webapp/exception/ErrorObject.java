package com.kolaykira.webapp.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject
{
    private int status;
    private String message;
    private Timestamp timestamp;
}
