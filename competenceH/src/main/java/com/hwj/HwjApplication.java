package com.hwj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hwj.dao")
public class HwjApplication {

	public static void main(String[] args) {
		SpringApplication.run(HwjApplication.class, args);
	}
}
