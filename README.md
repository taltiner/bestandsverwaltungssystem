# 📦 Bestandsverwaltungssystem

Ein Microservice-basiertes System zur Verwaltung von Bestellungen und Lagerbeständen, entwickelt mit **Spring Boot**, **Apache Camel**, **Kafka**, **PostgreSQL** und vollständig containerisiert mit **Docker Compose**.

---

## 🚀 Features

- Eingänge der Bestellungen und Nachbestellungen über REST-API
- Asynchrone Kommunikation über **Kafka**
- Routing, Transformation und Integration mit **Apache Camel**
- Persistenz mit **PostgreSQL**
- Vollständig dockerisiert für einfache lokale Entwicklung und Deployment

---

## ⚙️ Services

| Service                   | Beschreibung                                                                                          |
|---------------------------|-------------------------------------------------------------------------------------------------------|
| `bestellservice`          | Verarbeitet die Bestellung                                                                            |
| `bestandservice`          | Prüft und aktualisiert den Bestand                                                                    |
| `benachrichtigungservice` | Erstellt und sendet Benachrichtigungen an Kunden basierend auf dem Bestellstatus                     |
| `nachbestellservice`      | Erstellt automatische Nachbestellungen, wenn der Bestand eines Produkts unter die Mindestmenge fällt |
| `kafka`                   | Kafka Broker für Messaging                                                                            |
| `zookeeper`               | Notwendig für Kafka                                                                                   |
| `postgres`                | Datenbank zur Speicherung von Bestellungen                                                           |

---

## 🛠 Technologien

- **Spring Boot**
- **Apache Camel** – für Integration, Routing und Transformation zwischen Microservices
- **Apache Kafka**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## 🐳 Docker Setup

### Voraussetzungen

- Docker
- Docker Compose

### Starten

```bash
docker-compose up --build
