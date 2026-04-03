# 🚀 AI-Powered Incident Intelligence Platform

<p align="center">
  <b>Real-time log processing system with intelligent incident detection</b><br/>
  Built using Kafka, Redis, Microservices & AI-ready architecture
</p>

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-4.x-green)
![Kafka](https://img.shields.io/badge/Kafka-EventStreaming-black)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![PostgreSQL](https://img.shields.io/badge/Postgres-DB-blue)
![Docker](https://img.shields.io/badge/Docker-Containerization-blue)

---

## 🧠 Problem Statement

In modern distributed systems:

* Thousands of logs are generated every second
* Duplicate errors flood monitoring systems
* Root cause analysis is manual and slow

👉 This leads to **alert fatigue and delayed incident resolution**

---

## 💡 Solution

This project builds a **real-time incident intelligence pipeline** that:

* Processes logs using Kafka
* Eliminates duplicate alerts using Redis
* Classifies severity dynamically
* Enriches logs with AI-generated insights

---

## 🏗️ Architecture

```id="v9k8yc"
           +----------------------+
           |   Log Producers      |
           +----------+-----------+
                      |
                      v
               +-------------+
               |   Kafka     |
               +------+------+
                      |
                      v
            +-------------------+
            | Consumer Service  |
            | (Processing Layer)|
            +--------+----------+
                     |
     +---------------+----------------+
     |                                |
     v                                v
+-----------+                  +----------------+
|  Redis    |                  |   AI Layer     |
| Dedup +   |                  | Root Cause     |
| Frequency |                  | Suggestions    |
+-----------+                  +----------------+
                     |
                     v
            +-------------------+
            | Incident Service  |
            | (Spring Boot API) |
            +--------+----------+
                     |
                     v
               +-----------+
               | Database  |
               +-----------+
```

---

## 🔥 Key Features

### ⚡ Real-Time Event Processing

* Kafka-based event streaming
* Asynchronous, scalable log ingestion

---

### 🧹 Deduplication (Redis)

* Prevents duplicate incident creation
* Uses Redis keys with TTL (time-window based)

---

### 📊 Dynamic Severity Classification

* Based on error frequency:

    * 🟢 LOW → Rare occurrences
    * 🟡 MEDIUM → Moderate frequency
    * 🔴 HIGH → Frequent critical errors

---

### 🤖 AI-Ready Intelligence Layer

* Automatically generates:

    * Root cause
    * Suggested fix
* Currently mock-based (LLM integration ready)

---

### 🧩 Microservices Architecture

| Service              | Responsibility                |
| -------------------- | ----------------------------- |
| **Incident Service** | REST APIs + DB storage        |
| **Consumer Service** | Kafka processing + Redis + AI |

---

## 📡 API Endpoints

### Get all incidents

```id="1y4h3x"
GET /incidents
```

### Create incident

```id="a6c6xg"
POST /incidents
```

---

## 🧪 Example Workflow

### 1. Log Sent to Kafka

```json id="8nsntn"
{"service":"payment-service","level":"ERROR","message":"DB timeout"}
```

---

### 2. Consumer Processing

* Deduplication check (Redis)
* Frequency tracking
* Severity classification
* AI analysis

---

### 3. Stored Incident

```json id="b5bn8q"
{
  "service": "payment-service",
  "severity": "HIGH",
  "rootCause": "Database connection pool exhausted",
  "suggestedFix": "Increase pool size"
}
```

---

## 🚀 Getting Started

### 1️⃣ Start Infrastructure

```bash id="w9yo0g"
docker-compose up -d
```

---

### 2️⃣ Run Services

* Start `incident-service`
* Start `incident-consumer`

---

### 3️⃣ Send Kafka Message

```bash id="e7rrbo"
docker exec -it kafka bash

kafka-console-producer \
--topic logs-topic \
--bootstrap-server localhost:9092
```

Paste:

```json id="wk9dkn"
{"service":"payment-service","level":"ERROR","message":"DB timeout"}
```

---

## 📁 Project Structure

```id="7r4tdh"
incident-intelligence/
 ├── incident-service/
 ├── incident-consumer/
 ├── docker-compose.yml
```

---

## 🧠 Key Engineering Concepts

* Event-driven architecture
* Idempotent processing
* Redis-based rate limiting & caching
* Microservices communication
* Real-time stream processing

---

## 🔮 Future Enhancements

* 🤖 OpenAI / LLM integration
* 🔁 Retry mechanism & Dead Letter Queue
* 📊 Monitoring dashboard
* 📢 Alert system (Email / Slack)
* ⚡ Go-based high-performance consumer

---

## 💬 Interview Talking Points

* Built a real-time distributed system using Kafka
* Implemented Redis-based deduplication & frequency tracking
* Designed dynamic severity classification logic
* Integrated AI-ready pipeline for automated analysis
* Followed microservices architecture principles

---

## 👨‍💻 Author

**Prashant Yadav**

---

## ⭐ Support

If you found this useful, consider giving it a ⭐ on GitHub!
