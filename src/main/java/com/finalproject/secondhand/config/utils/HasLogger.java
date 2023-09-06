package com.finalproject.secondhand.config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ini buat ngasih logging ke aplikasi.
 * implement aplikasi ini untuk buat logging pada aplikasi
 * contoh implementasi pada kelas:
 * public interface Product implements HasLogger
 * <p>
 * Kemudian pada kelas implementasinya tinggal panggil
 * method <a href= {@link HasLogger}> <b>getLogger()</b> </a>.
 * <p>
 * Contoh: pemakaian logging
 * getLogger().info("ini adalah pesan logging")
 */
public interface HasLogger {

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

}