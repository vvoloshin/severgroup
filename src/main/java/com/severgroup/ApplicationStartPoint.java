package com.severgroup;

import com.severgroup.controller.MainController;
import com.severgroup.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class ApplicationStartPoint {

    @Autowired
    private static MainController controller;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStartPoint.class, args);
        if (args.length != 0) {
            Path config = Paths.get(args[0]);
            if (FileUtil.isExist(config) && (FileUtil.isNotEmpty(config))) {
                System.out.println("##Start handling CSV file...");
                controller.init(config).process();
            }

        } else {
            System.out.println("##error, not specified config file, exit!");
            System.exit(0);
        }
    }
}