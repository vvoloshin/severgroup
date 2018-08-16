package com.severgroup.controller;

import com.severgroup.service.CsvReaderServiceImpl;
import com.severgroup.service.CsvWriterServiceImpl;
import com.severgroup.service.ReaderService;
import com.severgroup.service.WriterService;
import com.severgroup.to.AvgRecord;
import com.severgroup.util.Config;
import com.severgroup.util.FileUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static com.severgroup.util.Config.READPATH;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class MainController {
    private final static Logger LOGGER = Logger.getLogger(MainController.class);

    public MainController() {
    }

    public MainController init(Path config) {
        Config.init(config);
        return this;
    }

    public void process() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        if (FileUtil.isFolder(READPATH)) {
            try (Stream<Path> paths = Files.walk(READPATH)) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(newPath -> convert(executorService, newPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileSystem fs = READPATH.getFileSystem();
        try (WatchService watchService = fs.newWatchService()) {
            READPATH.register(watchService, ENTRY_CREATE);
            WatchKey key = null;
            while (true) {
                key = watchService.take();
                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    if (OVERFLOW == kind) {
                        continue;
                    } else if ((ENTRY_CREATE == kind)) {
                        Path newPath = ((WatchEvent<Path>) watchEvent)
                                .context();
                        LOGGER.debug("New incoming CSV file : " + newPath.getFileName());
                        convert(executorService, newPath);
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void convert(ExecutorService executorService, Path newPath) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.csv");
        if (matcher.matches(newPath.getFileName())) {
            LOGGER.debug("Directory has not converted file: " + newPath.getFileName());
            ReaderService<List<AvgRecord>> readtask = new CsvReaderServiceImpl(READPATH.resolve(newPath.getFileName()));
            Future<List<AvgRecord>> submit = executorService.submit(readtask);
            try {
                while (true) {
                    if (submit.isDone()) {
                        List<AvgRecord> avgRecords = submit.get();
                        if (avgRecords != null && avgRecords.size() != 0) {
                            WriterService task = new CsvWriterServiceImpl(newPath.getFileName().toString(), avgRecords);
                            executorService.submit(task);
                            LOGGER.debug("Complete converting data from file: " + newPath.getFileName());
                        }
                        break;
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
