# xy-inc

## Ferramentas necessárias para compilação e testes

> [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

> [Maven](https://maven.apache.org/download.cgi)

A variável de ambiente $JAVA_HOME deve ser configurada

A pasta bin do maven deve estar no $PATH

## Instruções de uso

$ git clone https://github.com/vbortole/xy-inc.git

$ cd xy-inc

### Rodar os testes:

$ mvn clean test

### Iniciar a aplicação

$ mvn clean spring-boot:run

Acessar http://localhost:8080/swagger-ui.html

## Troubleshooting

É possível que ocorram conflitos de dependências, caso aconteça, exclua a pasta repository no diretorio .m2
