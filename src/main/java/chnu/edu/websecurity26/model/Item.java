package chnu.edu.websecurity26.model;

import lombok.*;

/*
  @author   oleksii
  @project  web-security26
  @class    Item
  @version  1.0.0 
  @since    15.09.2026
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Item {
    private String id;
    private String name;
    private String description;

    public Item(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
