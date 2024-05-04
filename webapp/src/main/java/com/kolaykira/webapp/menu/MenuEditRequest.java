package com.kolaykira.webapp.menu;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuEditRequest {
    private String context;
    // Tenant Info
    private String menuID;

}
