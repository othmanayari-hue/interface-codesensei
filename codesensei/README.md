# CodeSensei (Angular + Spring Boot + Flask + MySQL)

## Architecture (haut niveau)

- **Angular** (`frontend-angular/`) : UI (auth, admin, chat, account, paiement) → consomme l’API Spring Boot.
- **Spring Boot** (`backend-spring/`) : JWT auth, RBAC (ADMIN/USER), logique métier, MySQL, historique conversations, paiements → appelle **Flask AI**.
- **Flask AI** (`backend-flask/`) : endpoint REST qui exécute (ou stub) CodeFuse CGM.
- **MySQL** : persistance (`users`, `conversations`, `payments`).

## Schéma MySQL (SQL)

Les tables sont créées au démarrage MySQL via:

- `mysql/init/001_schema.sql`

## Endpoints API (Spring Boot)

- **Auth**
  - `POST /api/auth/register` : create user + retourne JWT
  - `POST /api/auth/login` : login + retourne JWT
- **Me**
  - `GET /api/me` : infos utilisateur connecté (role, abonnement, crédits)
- **Admin**
  - `GET /api/admin/users?page=&size=` : liste paginée
  - `PUT /api/admin/users/{id}` : modifier (nom, prénom, date)
  - `DELETE /api/admin/users/{id}` : supprimer
- **Conversations**
  - `GET /api/conversations?page=&size=` : historique paginé (dernier → premier)
  - `POST /api/conversations/chat` : envoie message → appelle Flask → stocke en MySQL → décrémente crédits
- **Paiements**
  - `GET /api/payments?page=&size=` : paiements paginés
  - `POST /api/payments` : enregistre paiement + met à jour abonnement + crédits

## Endpoints API (Flask)

- `GET /health`
- `POST /v1/cgm/chat` : `{ "message": "..." }` → `{ "response": "..." }` (stub prêt à brancher au modèle)

## Lancement (Docker)

Depuis `codesensei/`:

```bash
docker compose up --build
```

- MySQL : `localhost:3306`
- Spring API : `http://localhost:8080`
- Flask AI : `http://localhost:5001`
- Angular (dev) : dans un autre terminal:

```bash
cd frontend-angular
npm run start
```

## Lancement (local, sans Docker)

### MySQL

L’erreur `Access denied for user 'codesensei'@'localhost'` signifie que:
- **l’utilisateur MySQL `codesensei` n’existe pas**, ou
- **le mot de passe est différent**, ou
- **les privilèges sont manquants**.

Exécutez le script (en tant que root MySQL) :

- `mysql/init/000_user_and_db.sql`

Puis créez les tables:

- `mysql/init/001_schema.sql`

### Variables d’environnement (optionnel)

Vous pouvez override la connexion DB sans modifier le code:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## Schéma MySQL

Voir `mysql/init/001_schema.sql`.

