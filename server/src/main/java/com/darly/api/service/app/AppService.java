package com.darly.api.service.app;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface AppService {
    Path load(String filename);
    Resource loadFileAsResource(String fileName);
}
