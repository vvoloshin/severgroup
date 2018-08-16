package com.severgroup.service;

import java.util.concurrent.Callable;

public interface ReaderService<T> extends Callable<T> {
}
