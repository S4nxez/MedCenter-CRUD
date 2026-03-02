# MedCenter CRUD
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![Podman](https://img.shields.io/badge/Podman-892CA0?style=for-the-badge&logo=podman&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![Caddy](https://img.shields.io/badge/Caddy-00ADD8?style=for-the-badge&logo=caddy&logoColor=white)
![Tailscale](https://img.shields.io/badge/Tailscale-242424?style=for-the-badge&logo=tailscale&logoColor=white)
![Raspberry Pi](https://img.shields.io/badge/Raspberry_Pi-A22846?style=for-the-badge&logo=raspberry-pi&logoColor=white)
<br>
![Website](https://img.shields.io/website?url=https%3A%2F%2Fraspberrypi.tail0a4b52.ts.net&style=for-the-badge&label=Demo&up_message=online&down_message=offline)

A full-stack medical center management system built with **Spring Boot**, **Vanilla JS**, and **MySQL** — containerized, secured with **JWT**, and deployed automatically to production via a fully automated **CI/CD pipeline** running on a **Raspberry Pi**.

🌐 **Live demo:** [https://raspberrypi.tail0a4b52.ts.net/](https://raspberrypi.tail0a4b52.ts.net/)


---

## Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot, Spring JDBC |
| **Auth** | JWT (JSON Web Tokens) |
| **Database** | MySQL 8 |
| **Frontend** | Vanilla JavaScript, HTML5, Bootstrap |
| **Containerization** | Podman, Podman Compose |
| **Reverse Proxy** | Caddy (automatic HTTPS) |
| **CI/CD** | GitHub Actions (build + deploy workflows) |
| **Networking** | Tailscale + Funnel |
| **Server** | Raspberry Pi |

---

## Features

- **JWT Authentication** — stateless token-based auth; credentials validated server-side before any resource access
- **Patient Management** — full CRUD: create, read, update, and delete patient records
- **Medical Records** — nested CRUD table per patient with add, edit, and delete operations
- **Role-based Access** — protected routes enforce authenticated sessions

---

## Folder Structure

```
MedCenter-CRUD/
├── ClienteJS/          # Frontend — HTML, JS, Bootstrap
│   ├── index.html
│   ├── mainPage.html
│   ├── js/
│   └── Containerfile
└── SpringBoot/         # Backend — Spring Boot, JDBC, JWT
    ├── src/
    ├── pom.xml
    └── Containerfile
```

---

## Local Setup

**Requirements:** Java 17+, MySQL 8, a modern browser.

```bash
# 1. Clone
git clone https://github.com/S4nxez/MedCenter-CRUD.git
cd MedCenter-CRUD

# 2. Configure DB
# Edit SpringBoot/src/main/resources/application.properties
# Set your MySQL host, port, user, and password

# 3. Run backend
cd SpringBoot
./mvnw spring-boot:run

# 4. Open frontend
open ClienteJS/index.html
```

---

## Screenshots

**Login**
   <img width="1920" height="359" alt="Login screen" src="https://github.com/user-attachments/assets/b6021ee9-926d-4d63-a6c2-bbb7dceb20e5" />


**Patient Table**
   <img width="1920" height="526" alt="Patient Table" src="https://github.com/user-attachments/assets/f1331015-8694-4316-8874-14fdf902e2b1" />


---

## Automated Deployment — CI/CD on Raspberry Pi

> Deploying to production used to mean hours of errors and confusing logs. Now a single `git push` triggers a fully automated pipeline that builds, ships, and publishes the app in ~1 minute.

### How it works

**1 — CI/CD with GitHub Actions + Podman**

Every push to `main` triggers two independent workflows:
- **Build workflow** — builds container images for the DB, backend (Spring Boot + JDBC + JWT), and frontend using Podman
- **Deploy workflow** — SSHs into the Raspberry Pi and updates the running environment with the new images

**2 — Orchestration with Podman Compose**

Services run together on an isolated `medcenter-net` network:
- **MySQL** — persistent volumes + SQL init scripts
- **Backend** — depends on a healthy MySQL instance; receives DB credentials via environment variables
- **Frontend** — static JS/HTML served as a container
- **Caddy** — reverse proxy on port `8080:80` with mounted `Caddyfile`, data, and config volumes

**3 — Public access with Tailscale Funnel**

A persistent Tailscale Funnel service keeps the app reachable 24/7 from the public internet — no VPN, no port forwarding, no router config.

- ✅ Public HTTPS URL accessible from anywhere
- ✅ No open ports on the home router
- ✅ Raspberry Pi isolated from the internet
- ✅ Encrypted tunnel end-to-end

**4 — Reverse proxy with Caddy**

Caddy handles:
- Automatic HTTPS with zero manual certificate management
- Clean reverse-proxy routing to each container

### Final infrastructure stack

```
Raspberry Pi
└── Podman Compose (medcenter-net)
    ├── MySQL          ← persistent volumes + init scripts
    ├── Spring Boot    ← JWT auth, JDBC, env-based config
    ├── Frontend JS    ← static container
    └── Caddy          ← HTTPS + reverse proxy
        └── Tailscale Funnel  ← public URL, always on
            └── GitHub Actions (build + deploy workflows)
```

A real-world example of production-grade infrastructure: automated CI/CD, containerized services, secure networking, and zero-downtime public deployment.
