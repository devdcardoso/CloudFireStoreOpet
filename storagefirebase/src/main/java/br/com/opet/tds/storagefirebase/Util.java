package br.com.opet.tds.storagefirebase;

import java.sql.Timestamp;

public class Util {

    public static long getTimestamp() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
