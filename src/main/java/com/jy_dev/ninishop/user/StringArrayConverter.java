package com.jy_dev.ninishop.user;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//JPA에서 List<String> 타입을 데이터베이스의 단일 String 컬럼으로 변환하기 위한 커스텀 컨버터
@Converter // JPA 엔티티의 속성과 데이터베이스 컬럼 간의 변환을 수행하는 컨버터
public class StringArrayConverter implements AttributeConverter<List<String>, String> {

    // 구분자로 , 사용
    private static final String SPLIT_CHAR = ",";

    // 엔티티의 List<String> 타입 속성을 데이터베이스의 String 타입 컬럼으로 변환
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute.stream().map(String::valueOf).collect(Collectors.joining(SPLIT_CHAR));
    }

    // 데이터베이스의 String 타입 컬럼을 엔티티의 List<String> 타입 속성으로 변환
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if(dbData == null) { // JPA save는 select부터 하기 때문에, null을 체크해줘야 한다.
            return Collections.emptyList(); // DB데이터가 null 이면 빈리스트 반환
        } else {
            return Arrays.stream(dbData.split(SPLIT_CHAR))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        }
    }
}
