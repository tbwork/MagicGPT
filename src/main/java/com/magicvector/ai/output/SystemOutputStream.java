package com.magicvector.ai.output;

import java.io.IOException;
import java.io.OutputStream;

public class SystemOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
        System.out.write(b);
        System.out.flush();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        System.out.write(b, off, len);
        System.out.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        System.out.write(b);
        System.out.flush();
    }
}
