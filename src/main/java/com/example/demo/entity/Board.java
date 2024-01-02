package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity // 엔티티는 db에 있는 테이블을 의미
@Data
public class Board {
    @Id // primary 키 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String title;

    private  String content;

    private String filename;

    private String filepath;

}
