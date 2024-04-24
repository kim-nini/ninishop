package com.jy_dev.ninishop.order;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jdk.jshell.Snippet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter // JPA 엔티티의 속성과 데이터베이스 컬럼 간의 변환을 수행하는 컨버터
public class TinyIntConverter implements AttributeConverter<StatusEnum, Integer> {

    // 구분자로 , 사용
    private static final String SPLIT_CHAR = ",";

    // 엔티티의 StatusEnum 타입을 데이터베이스의 tinyInt 타입 컬럼으로 변환
    @Override
    public Integer convertToDatabaseColumn(StatusEnum status) {
        return status == null ? StatusEnum.ORDER_COMPLETED.getStatus() : status.getStatus();
    }

    // 데이터베이스의 String 타입 컬럼을 엔티티의 List<String> 타입으로 변환
    @Override
    public StatusEnum convertToEntityAttribute(Integer dbData) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getStatus() == dbData) {
                return status;
            }
        }
        throw new IllegalArgumentException("No such status: " + dbData);
    }
}
