package com.german.downloader.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ProxyBook {

    public void writeContent(OutputStream output) throws IOException {
        final URL url = new URL("https://doc-0s-5g-docs.googleusercontent.com/docs/securesc/ha0ro937gcuc7l7deffksulhg5h7mbp1/iasho0kaa30r6khkcvu9vpgnc8v3u0us/1503597600000/16706558120316080820/*/0B2BBJnm4YGo8TzlrZHBpbWxldVE?e=download");
        final InputStream istream = url.openStream();

        final byte[] buffer = new byte[1024 * 8];
        while (true) {
            final int len = istream.read(buffer);
            if (len <= 0) {
                break;
            }
            output.write(buffer, 0, len);
        }
    }
}
