# maschine-eintrage-service

Ein Quarkus-basierter Microservice zur Validierung von Maschineneinträgen über **Apache Kafka** mit **Redpanda** als Message Broker.

---

## Beschreibung

Der `maschine-eintrage-service` ist verantwortlich für die Validierung eingehender Maschineneinträge. Er stellt sicher, dass alle Maschinennummern dem vorgeschriebenen Format der Firma **Stryker** entsprechen – d.h. jede Maschinennummer muss mit `9007-` beginnen.

Valide Einträge werden anschliessend über Kafka/Redpanda an den `maschine-eintrage-rest-api` Service weitergeleitet.

```
[Eingehende Nachricht] ──► [Validierung: 9007-?] ──► [Kafka Producer] ──► [maschine-eintrage-rest-api]
                                      │
                              [Ungültig: verworfen]
```

---

## Kafka Topic Konfiguration

| Eigenschaft                      | Wert                 |
| -------------------------------- | -------------------- |
| **Broker**                       | `redpanda-1:9092`    |
| **Eingehender Topic** (Consumer) | `maschine-eingang`   |
| **Ausgehender Topic** (Producer) | `maschine-validiert` |

### Konfiguration in `application.properties`

```properties
# Kafka Broker
kafka.bootstrap.servers=redpanda-1:9092

# Consumer
mp.messaging.incoming.maschine-eingang.connector=smallrye-kafka
mp.messaging.incoming.maschine-eingang.topic=maschine-eingang

# Producer
mp.messaging.outgoing.maschine-validiert.connector=smallrye-kafka
mp.messaging.outgoing.maschine-validiert.topic=maschine-validiert

```

---

## Validierungsregel

Eine Maschinennummer gilt als **valide**, wenn sie mit dem Präfix `9007-` beginnt.

```
✅  9007-ABC123   → wird weitergeleitet
❌  1234-XYZ      → wird verworfen
```

---

## Lokaler Start

```bash
docker run --rm \
  -p 8082:8082 \
  --network mashine-nw \
  --name maschine-valid-service \
  ghcr.io/merontf/maschine-eintrage-service:latest
```

> Das Netzwerk `mashine-nw` muss bereits existieren und Redpanda muss laufen. Siehe Haupt-README für die vollständige Setup-Anleitung.

---

## Verwendete Technologien

| Technologie                                                                     | Zweck                            |
| ------------------------------------------------------------------------------- | -------------------------------- |
| [Quarkus](https://quarkus.io/)                                                  | Java-Framework                   |
| [SmallRye Reactive Messaging](https://smallrye.io/smallrye-reactive-messaging/) | Kafka-Integration                |
| [Redpanda](https://redpanda.com/)                                               | Kafka-kompatibler Message Broker |

> This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/maschine-eintrage-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-getting-started)): Connect to Kafka with Reactive Messaging
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
