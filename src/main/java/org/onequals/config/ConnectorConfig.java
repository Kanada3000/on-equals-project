//package org.onequals.config;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class ConnectorConfig {
//
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat =
//                new TomcatServletWebServerFactory() {
//
//                    @Override
//                    protected void postProcessContext(Context context) {
//                        SecurityConstraint securityConstraint = new SecurityConstraint();
//                        securityConstraint.setUserConstraint("CONFIDENTIAL");
//                        SecurityCollection collection = new SecurityCollection();
//                        collection.addPattern("/*");
//                        securityConstraint.addCollection(collection);
//                        context.addConstraint(securityConstraint);
//                    }
//                };
//        tomcat.addAdditionalTomcatConnectors(createHttpConnector());
//        return tomcat;
//    }
//
//    private Connector createHttpConnector() {
//        Connector connector =
//                new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setSecure(false);
//        connector.setPort(8899);
//        connector.setRedirectPort(8899);
//        return connector;
//    }
//}