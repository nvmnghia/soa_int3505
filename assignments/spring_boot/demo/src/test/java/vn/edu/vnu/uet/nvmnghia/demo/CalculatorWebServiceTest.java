package vn.edu.vnu.uet.nvmnghia.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import uet.soa.soap.GetSumRequest;
import uet.soa.soap.GetSumResponse;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalculatorWebServiceTest {

    @LocalServerPort
    private int port;

    @Test
    public void testSum() throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetSumRequest.class));
        marshaller.afterPropertiesSet();
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);

        GetSumRequest request = new GetSumRequest();
        request.setFirstSummand(100);
        request.setSecondSummand(200);

        GetSumResponse response = (GetSumResponse) ws.marshalSendAndReceive(
                                    "http://localhost:" + port + "/ws",
                                    request);
        System.out.println("Result is: " + response.getResult());
        assertThat(response.getResult() == 300 && response.getError() == null);
    }

}
