Cliente Acreencias 
==================

Este es un experimento del uso de un cliente que consuma un servicio Web en 
[JAX-RS](https://jax-rs-spec.java.net/) y es construido con la ayuda de 
[Jersey](https://jersey.java.net/).

El cliente permite hacer uso del servicio de [Acreencias](https://github.com/gssbzn/acreencias) 
para crear:
* Clientes;
* Cuentas;
* Movimientos.

## Uso

Menu interactivo para las diferentes opciones de acreencias

```console
java -jar acreenciasCliente.jar (-i)
```
 
Demonio para crear concurrentemente movimientos a un cliente dado

```console
java -jar acreenciasCliente.jar -c <id>
```

Donde id es el identificador del cliente a usar

## License

MIT License. Copyright 2014 Gustavo Bazan. http://gustavobazan.com