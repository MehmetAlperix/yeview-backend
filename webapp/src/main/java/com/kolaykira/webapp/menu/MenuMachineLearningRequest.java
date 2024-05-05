package com.kolaykira.webapp.menu;


        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuMachineLearningRequest {
    private String menuID;
    private String menuTitle;
    private String context;
    private long rating;
    // Tenant Info
    private String ingredients;
    private String restaurantID;

}

