package com.severgroup;

import com.severgroup.controller.MainController;
import com.severgroup.util.FileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationStartPoint {

    public static void main(String[] args) {
        if (args.length != 0) {
            Path config = Paths.get(args[0]);
            if (FileUtil.isExist(config) && (FileUtil.isNotEmpty(config))) {
                System.out.println("##Start handling CSV file...");
                new MainController()
                        .init(config)
                        .process();
            }
        } else System.out.println("##error, not specified config file, exit!");
    }
}