# Spring SOAP Web Service demo

[Original instruction](https://forum.simidoc.vn/d/11-tao-mot-soap-web-service-don-gian-voi-spring)

## 1. Introduction

Spring-WS is a dependency of Spring framework, providing SOAP capabilities to Spring.

Spring-WS uses a *contract-first* model. In Spring-WS, developers create the XML contracts (i.e. defining what is sent to and received from server) first, then the equivalent Java classes will be generated. The developers then implement the actual logic using the generated classes.

On the contrary, contract-last model starts with the code, and generates contracts from the code. The main drawback of this method is that it suffers from object/XML mismatch. Many object features cannot be trivially translated into XML, for example cyclic graph.

## 2. Implementation steps

This section explains the step in the [class tutorial][1].

### 2.1. Defining messages: XML

Create sample messages in XML.

### 2.2. Defining message schemas: XSD

Create XSDs which validates the created XML messages.

For `DefaultWsdl11Definition` to recognize messages, and create WSDL from the XSD, `name` of each message element must end with one of two special suffixes:

- **`Request`**
  - In the example: `getSumRequest`
  - Can be changed by `requestSuffix` in [WSDL](#23-defining-service-contract-wsdl)
- **`Response`**
  - In the example: `getSumResponse`
  - Can be changed by `responseSuffix` in [WSDL](#23-defining-service-contract-wsdl)

```xml
<xs:schema>
    <xs:element name="getSumRequest">
        ...
    </xs:element>

    <xs:element name="getSumResponse">
        ...
    </xs:element>
</xs:schema>
```

### 2.3. Defining service contract: WSDL

The WSDL can be created automatically by `DefaultWsdl11Definition` from XSD, so manually writing one is not necessary. `requestSuffix` and `responseSuffix` can be configured here.

### 2.4. Generate Java class from XSD

There're many routes at this point.

- [Class tutorial][1] uses `jaxb2:xjc`.
- [Reference tutorial][2] use Spring-WS archetype (i.e. template). This method also generates the whole project structure, unlike the above one which already has a project structure from Spring Initializr and just need to `xjc`.

The message classes `GetSumRequest` and `GetSumResponse` is created after this step.

### 2.5. Write endpoint

Logic is implemented in `sum()` that takes a `GetSumRequest` and returns a `GetSumResponse`. The class `CalculatorEndpoint` and its `sum()` function must be written with Spring-WS annotation for the framework to recognize.

See [the class](src/main/java/vn/edu/vnu/uet/nvmnghia/demo/CalculatorEndpoint.java) for further information.

## 3. EJB & Spring

[1]: https://forum.simidoc.vn/d/11-tao-mot-soap-web-service-don-gian-voi-spring
[2]: https://docs.spring.io/spring-ws/docs/current/reference/
