# TP19 - Orchestration de Microservices avec Spring Cloud Eureka, Gateway et OpenFeign

## 📋 Étape 0 — Contexte et Architecture

### Objectif de l'étape

Comprendre ce qu'apporte Spring Cloud dans une architecture microservices.

Situer le rôle d'Eureka (découverte de services) et de la Gateway (point d'entrée et routage).

Visualiser le flux complet d'une requête entre client, Gateway, Eureka, Load Balancer et microservices.

---

## 🏗️ Concepts clés (microservices)

### Services autonomes
Chaque service gère un domaine fonctionnel, déployé indépendamment.

### Communication légère
Principalement HTTP/REST ; synchrone (OpenFeign) ou asynchrone (messaging).

### Données isolées
Chaque service possède son propre stockage (ici H2 en mémoire pour le lab).

### Scalabilité horizontale
Plusieurs instances d'un même service derrière un équilibrage de charge.

---

## ☁️ Spring Cloud en bref

- **Découverte de services** : enregistrement/lookup dynamique (Eureka).
- **Routage et agrégation** : API Gateway centralise, filtre et route le trafic.
- **Configuration centralisée** : Spring Cloud Config (Git, versionné, reloadable).
- **Résilience** : Circuit Breaker, timeouts, retries (Hystrix ou Resilience4j).
- **Observabilité** : traçage/corrélation (Sleuth/Zipkin) et health checks (Actuator).

---

## 🔍 Eureka (Service Discovery)

### Registre dynamique
Les microservices publient leur présence (nom logique + host/port).

### Découverte côté client
Les clients (Feign, Gateway) interrogent Eureka pour lister les instances.

### Mécanismes cœur
- **Heartbeats (battements)** + TTL
- **Cache côté client**
- **Self-preservation** (disponibilité prioritaire)

---

## 🚪 API Gateway (Spring Cloud Gateway)

### Point d'entrée unique
Expose des routes publiques stables et masque la topologie interne.

### Routage
- **Statique** : vers des URLs fixes
- **Dynamique** : via des noms logiques `lb://SERVICE-NAME`

### Cross-cutting concerns
- Sécurité, CORS
- Rate limiting
- Réécriture d'URL
- Journaux, métriques

---

## ⚖️ Équilibrage de charge

### Client-side load balancing
Spring Cloud LoadBalancer choisit une instance (round-robin, etc.) parmi celles fournies par Eureka.

### Bénéfices
- Pas de point unique de défaillance
- Adaptation dynamique au scaling

---

## 🗺️ Topologie du lab

| Service | Description | Port |
|---------|------------|------|
| **Eureka Server** | Registre des services | 8761 |
| **SERVICE-CLIENT** | Microservice CRUD clients (H2 en mémoire) | 8088 |
| **SERVICE-VOITURE** | Microservice voitures, appelle SERVICE-CLIENT via OpenFeign | 8089 |
| **Gateway** | Point d'entrée ; routage statique puis dynamique (`lb://...`) | 8888 |

---

## 🔄 Flux d'une requête (pas-à-pas)

1. **Démarrage** : Chaque microservice contacte Eureka et s'enregistre
   - Nom = `spring.application.name` + host/port

2. **Requête client** : Le client (navigateur ou autre app) appelle l'API Gateway

3. **Résolution de route** : La Gateway résout la route
   - Statique (URI fixe) ou
   - Dynamique (nom logique via Eureka)

4. **Load Balancing** : LoadBalancer choisit une instance cible et la requête est transmise

5. **Traitement** : Le service traite la requête ; si besoin, il appelle un autre service (Feign + Eureka + LB)

6. **Réponse** : Réponse renvoyée à la Gateway, puis au client

---

## 🛡️ Résilience et tolérance aux pannes

### Timeouts + retries
Évitent les blocages et améliorent la robustesse.

### Circuit Breaker
Ouvre le circuit si un service est défaillant ; fallback pour limiter l'impact (Hystrix/Resilience4j).

### Health checks (Actuator)
Liveness/readiness pour le monitoring.

---

## 🏷️ Nommage et adressage

### Nom logique
`spring.application.name` (ex. `SERVICE-CLIENT`, `SERVICE-VOITURE`)

### Routage dynamique
Schéma `lb://SERVICE-NAME` (découverte + choix d'instance)

### Ports (lab)
- Eureka : **8761**
- Gateway : **8888**
- Client : **8088**
- Voiture : **8089**

---

## 🔒 Sécurité et CORS (aperçu)

### Gateway = surface d'exposition unique
Centraliser authN/authZ.

### CORS
À configurer si front séparé (origins/headers/methods).

> **Note** : En TP : non obligatoire, mais important en production.

---

## 📊 Observabilité (aperçu)

### Actuator
- `/actuator/health`
- Métriques
- Env

### Traces distribuées
Sleuth/Zipkin (optionnel dans ce lab)

---

## 🚀 Démarrage rapide

### Ordre de démarrage recommandé

1. **Eureka Server** (port 8761)
   ```bash
   cd EurekaServer
   mvn spring-boot:run
   ```

2. **SERVICE-CLIENT** (port 8088)
   ```bash
   cd Client
   mvn spring-boot:run
   ```

3. **SERVICE-VOITURE** (port 8089)
   ```bash
   cd Voiture
   mvn spring-boot:run
   ```

4. **Gateway** (port 8888)
   ```bash
   cd GateWay
   mvn spring-boot:run
   ```

### Vérification

- **Eureka Dashboard** : http://localhost:8761
- **Gateway** : http://localhost:8888
- **SERVICE-CLIENT** : http://localhost:8088
- **SERVICE-VOITURE** : http://localhost:8089

---

## 📝 Structure du projet

```
.
├── EurekaServer/          # Serveur de découverte de services
├── GateWay/               # API Gateway
├── Client/                # Microservice SERVICE-CLIENT
└── Voiture/               # Microservice SERVICE-VOITURE
```

---

## 🔗 Technologies utilisées

- **Spring Boot**
- **Spring Cloud Eureka** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **Spring Cloud OpenFeign** (Client HTTP déclaratif)
- **Spring Cloud LoadBalancer** (Client-side load balancing)
- **H2 Database** (Base de données en mémoire)

---

## 📚 Ressources

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Netflix Eureka](https://github.com/Netflix/eureka)

