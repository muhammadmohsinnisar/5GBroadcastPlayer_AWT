package com.example.a5gbroadcastplayer_awt;

import static java.lang.Math.min;

import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataSink;

import java.io.IOException;
import java.nio.ByteBuffer;


/** A {@link UploadDataProvider} implementation that provides data from a {@code byte[]}. */
/* package */ final class ByteArrayUploadDataProvider extends UploadDataProvider {

    private final byte[] data;

    private int position;

    public ByteArrayUploadDataProvider(byte[] data) {
        this.data = data;
    }

    @Override
    public long getLength() {
        return data.length;
    }

    @Override
    public void read(UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
        int readLength = min(byteBuffer.remaining(), data.length - position);
        byteBuffer.put(data, position, readLength);
        position += readLength;
        uploadDataSink.onReadSucceeded(false);
    }

    @Override
    public void rewind(UploadDataSink uploadDataSink) throws IOException {
        position = 0;
        uploadDataSink.onRewindSucceeded();
    }
}

