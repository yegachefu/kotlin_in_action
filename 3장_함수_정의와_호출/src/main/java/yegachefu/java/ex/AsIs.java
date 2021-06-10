package yegachefu.java.ex;

import kotlin.text.StringsKt;

import java.util.Objects;

public class AsIs {
    private Integer id;
    private String name;

    public AsIs(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void save() {
        validate(id, name);
        System.out.println("saved!");
    }

    private void validate(Integer id, String name) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id 잘못됨!");
        }

        if (StringsKt.isBlank(name)) {
            throw new IllegalArgumentException("name 잘못됨!");
        }
    }
}
