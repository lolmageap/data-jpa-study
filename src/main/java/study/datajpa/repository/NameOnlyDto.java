package study.datajpa.repository;

import lombok.Getter;

@Getter
public class NameOnlyDto {

    private final String name;

    public NameOnlyDto(String name) {
        this.name = name;
    }

}
