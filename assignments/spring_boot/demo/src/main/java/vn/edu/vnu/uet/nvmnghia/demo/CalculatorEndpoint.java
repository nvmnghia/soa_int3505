package vn.edu.vnu.uet.nvmnghia.demo;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import uet.soa.soap.GetSumRequest;
import uet.soa.soap.GetSumResponse;


@Endpoint    // Register to Spring-WS that it can handle the request
public class CalculatorEndpoint {

    static final String NAMESPACE_URI = "http://soa.com/soap/calculator";

    @PayloadRoot(                      // Register to Spring-WS that it can handle request
        namespace = NAMESPACE_URI,     // of the given namespace
        localPart = "getSumRequest"    // and localPart (see calculator.xsd for local part)
    )
    @ResponsePayload
    public GetSumResponse sum(@RequestPayload GetSumRequest request) {
        GetSumResponse response = new GetSumResponse();

        response.setResult(request.getFirstSummand() + request.getSecondSummand());
        response.setError(null);

        return response;
    }

}
