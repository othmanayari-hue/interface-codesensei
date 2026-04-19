<img width="2521" height="1216" alt="chat" src="https://github.com/user-attachments/assets/969f1ac0-7916-44de-92be-89c9e9d4fc6a" />
<img width="1836" height="679" alt="payment" src="https://github.com/user-attachments/assets/823aac74-bb2e-484f-ab51-d77580d6956a" />
# 🚀 CodeSensei: Assistant IA Intelligent (Fullstack)

![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Flask](https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

> **CodeSensei** est une plateforme complète d'assistance à la programmation propulsée par l'IA, combinant la robustesse de Spring Boot, la flexibilité de Flask pour l'IA et une interface moderne sous Angular.

---

## 🏗️ Architecture du Système (Haut Niveau)

Le projet repose sur une architecture micro-services articulée comme suit :

1.  **Frontend (Angular)** : Interface utilisateur complète (Authentification, Dashboard Admin, Chat, Gestion de compte et Paiements).
2.  **Backend Principal (Spring Boot)** : Cœur du système. Gère la sécurité (JWT, RBAC), la logique métier, la persistance MySQL et communique avec le service IA.
3.  **Moteur IA (Flask)** : Service dédié à l'exécution du modèle **CodeFuse CGM** via un endpoint REST.
4.  **Base de données (MySQL)** : Persistance des utilisateurs, de l'historique des conversations et des transactions de paiement.

---

## 📸 Aperçu de l'Interface

| Interface de Chat | Gestion des Paiements |
| :---: | :---: |
| ![Chat CodeSensei](./chat.jpg) | ![Paiements CodeSensei](./payment.jpg) |
| *Interaction fluide avec l'IA* | *Système d'abonnement et crédits* |

---

## 🔑 Fonctionnalités Clés

### 🛡️ Authentification & Rôles
* Authentification sécurisée par **JWT**.
* Contrôle d'accès basé sur les rôles (**RBAC**) : `ADMIN` et `USER`.
* Gestion du profil utilisateur (nom, prénom, date).

### 🤖 Intelligence Artificielle & Chat
* Interface de chat en temps réel.
* Historique des conversations paginé.
* **Système de crédits** : Chaque message décrémente les crédits de l'utilisateur.

### 💳 Paiements & Abonnements
* Suivi des transactions.
* Mise à jour automatique de l'abonnement et des crédits après paiement.

---

## 🔌 Endpoints API

### Spring Boot (Port 8080)
* **Auth**: `POST /api/auth/register`, `POST /api/auth/login`
* **Utilisateur**: `GET /api/me` (infos session)
* **Admin**: `GET /api/admin/users`, `PUT /api/admin/users/{id}`, `DELETE`
* **Chat**: `GET /api/conversations`, `POST /api/conversations/chat`
* **Finances**: `GET /api/payments`, `POST /api/payments`

### Flask AI (Port 5001)
* `GET /health` : Vérification du service.
* `POST /v1/cgm/chat` : Endpoint de traitement du modèle.

---

## 🚀 Installation et Lancement

### 🐳 Via Docker (Recommandé)
Pour lancer l'ensemble de l'infrastructure (DB + Backends) en une commande :

```bash
docker compose up --build
