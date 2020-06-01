package vn.edu.vnu.uet.nvmnghia.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.core.io.ClassPathResource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration    // Register to Spring that it is a source for beans.
public class WebServiceConfig {

    @Bean    // Register to Spring that it is a bean.
             // Bean id is the method's name.
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        // Servlet is a Java object that can process requests and produce responses
        // Servlet container actually receives requests and sends responses on behalf of servlet
        // Spring application usually uses a DispatcherServlet
        // Spring-WS application uses this one instead
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();

        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Generate WSDL from XSD
     * 
     * @return DefaultWsdl11Definition the generated WSDL
     */
    @Bean(name = "calculator")
    public DefaultWsdl11Definition calculatorWsdl() {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();

        wsdl.setPortTypeName("CalculatorPort");
        wsdl.setTargetNamespace(CalculatorEndpoint.NAMESPACE_URI);
        wsdl.setLocationUri("/ws/*");
        wsdl.setSchema(getCalculatorSchema());

        return wsdl;
    }

    @Bean
    public XsdSchema getCalculatorSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/calculator.xsd"));
    }

}
