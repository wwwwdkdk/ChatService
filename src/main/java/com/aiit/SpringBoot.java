package com.aiit;


import com.aiit.netty.NettyTcpServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;


@SpringBootApplication
@MapperScan("com.aiit.dao")
public class SpringBoot implements CommandLineRunner {
    @Resource
    private NettyTcpServer tcpServer;

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        tcpServer.startServer();
    }
}
