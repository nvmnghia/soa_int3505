# Spring SOAP Web Service demo

[Original instruction](https://forum.simidoc.vn/d/11-tao-mot-soap-web-service-don-gian-voi-spring)

## 1. Introduction

Spring-WS is a dependency of Spring framework, providing SOAP capabilities to Spring.

Spring-WS uses a *contract-first* model. In Spring-WS, developers create the XML contracts (i.e. defining what is sent to and received from server) first, then the equivalent Java classes will be generated. The developers then implement the actual logic using the generated classes.

On the contrary, contract-last model starts with the code, and generates contracts from the code. The main drawback of this method is that it suffers from object/XML mismatch. Many object features cannot be trivially translated into XML, for example cyclic graph.

## 2. Implementation steps

This section explains the step in the [class tutorial][1].

### 2.1. Initialize project

[Spring Initializr](https://start.spring.io/) scaffolds the project structure.

The project uses Maven. To install libs and build, run:

```bash
mvn install
```

To update (snapshot, not release), run:

```bash
mvn clean install -U
```

### 2.2. Defining messages: XML

Create sample messages in XML.

### 2.3. Defining message schemas: XSD

Create XSDs which validates the created XML messages.

To automatically generate WSDL from the XSD, `name` of request/response message element must end with one of two special suffixes:

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

### 2.4. Defining service contract: WSDL

The WSDL can be created automatically by `DefaultWsdl11Definition` from XSD, so manually writing one is not necessary. `requestSuffix` and `responseSuffix` can be configured here.

### 2.5. Generate Java class from XSD

There're many routes at this point.

- [Class tutorial][1] uses `jaxb2:xjc`.
- [Reference tutorial][2] uses Spring-WS archetype (i.e. template). This method generates the whole project structure, which is already done at [step one](#21-initialize-project).

The message classes `GetSumRequest` and `GetSumResponse` is created after this step.

### 2.6. Write endpoint

Logic is implemented in `sum()` that takes a `GetSumRequest` and returns a `GetSumResponse`. The class `CalculatorEndpoint` and its `sum()` function must be written with Spring-WS annotation for the framework to recognize.

See [`CalculatorEndpoint`](src/main/java/vn/edu/vnu/uet/nvmnghia/demo/CalculatorEndpoint.java) for further information.

### 2.7. Wire things together: Spring configuration



## 3. Spring Framework

This [SO answer](https://stackoverflow.com/a/1064562/5959593) sums it all. Spring is a **dependency injection** framework. Bean-style classes are developed independently, then they are wired together/*injected* by annotation or XML.

[1]: https://forum.simidoc.vn/d/11-tao-mot-soap-web-service-don-gian-voi-spring
[2]: https://docs.spring.io/spring-ws/docs/current/reference/
