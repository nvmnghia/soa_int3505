# Spring SOAP Web Service demo

[Original instruction](https://forum.simidoc.vn/d/11-tao-mot-soap-web-service-don-gian-voi-spring)

## 1. Introduction

Spring-WS is a dependency of Spring framework, providing SOAP capabilities to Spring.

Spring-WS uses a *contract-first* model. In Spring-WS, developers create the XML contracts (i.e. defining what is sent to and received from server) first, then the equivalent Java classes will be generated. The developers then implement the actual logic using the generated classes.

On the contrary, contract-last model starts with the code, and generates contracts from the code. The main drawback of this method is that it suffers from object/XML mismatch. Many objects feature cannot be trivially translated into XML, for example cyclic graph.

## 2. Implementation steps

### 2.1. Defining messages: XML

### 2.2. Defining message schemas: XSD

### 2.3. Defining service contract: WSDL

The WSDL can be created by WSDL, so manually writing one is not necessary.
