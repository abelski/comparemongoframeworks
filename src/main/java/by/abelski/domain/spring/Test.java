package by.abelski.domain.spring;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Artur Belski
 */
@Document(collection = "test")
public class Test {
    @Id
    private String id;

    private String name;
    private String name1;
    private String name2;
    private String name3;

    public Test() {
        this.name = "TEST";
        this.name1 = "TEST";
        this.name2 = "TEST";
        this.name3 = "TEST";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }
}
