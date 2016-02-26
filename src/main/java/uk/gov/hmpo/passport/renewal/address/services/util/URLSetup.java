package uk.gov.hmpo.passport.renewal.address.services.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

public class URLSetup {

    private static volatile boolean setup;

    public static void setup() {
        if (setup) {
            return;
        }
        setup = true;
        ConfigurableStreamHandlerFactory urlHandlers = new ConfigurableStreamHandlerFactory(
                "classpath", new Handler());
        urlHandlers.addHandler("http", new sun.net.www.protocol.http.Handler());
        urlHandlers.addHandler("https",
                new sun.net.www.protocol.https.Handler());
        urlHandlers.addHandler("file", new sun.net.www.protocol.file.Handler());
        urlHandlers.addHandler("ftp", new sun.net.www.protocol.ftp.Handler());
        urlHandlers.addHandler("mailto",
                new sun.net.www.protocol.mailto.Handler());
        urlHandlers.addHandler("jar", new sun.net.www.protocol.jar.Handler());
        URL.setURLStreamHandlerFactory(urlHandlers);
    }

    static class ConfigurableStreamHandlerFactory implements
            URLStreamHandlerFactory {
        private final Map<String, URLStreamHandler> protocolHandlers;

        public ConfigurableStreamHandlerFactory(String protocol,
                                                URLStreamHandler urlHandler) {
            protocolHandlers = new HashMap<String, URLStreamHandler>();
            addHandler(protocol, urlHandler);
        }

        public void addHandler(String protocol, URLStreamHandler urlHandler) {
            protocolHandlers.put(protocol, urlHandler);
        }

        public URLStreamHandler createURLStreamHandler(String protocol) {
            return protocolHandlers.get(protocol);
        }
    }

    static class Handler extends URLStreamHandler {
        /**
         * The classloader to find resources from.
         */
        private final ClassLoader classLoader;

        public Handler() {
            this.classLoader = getClass().getClassLoader();
        }

        public Handler(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            final URL resourceUrl = classLoader.getResource(u.getPath());
            return resourceUrl.openConnection();
        }
    }
}
