# scanning-example-kotlin

Ein Beispielprojekt, das verschiedene Sicherheitsaspekte demonstriert:
- Verwendung von Log4j2 für Logging
- Ktor-Webserver für minimale HTML-Seiten
- Trivy-Security-Scanning via GitHub Actions
- Docker-Container-Support

## Features

### Webserver
Das Projekt enthält einen minimalen Ktor-Webserver, der auf Port 8080 läuft und folgende Endpunkte bietet:
- `/` - Minimale HTML-Startseite
- `/health` - Health-Check-Endpunkt

### Logging
Verwendet Log4j2 für strukturiertes Logging anstelle von `println`.

### Security Scanning
GitHub Actions Workflow führt Trivy-Scans aus:
- Bei Pull Requests auf `main`
- Bei Push auf `main`
- Täglich um 2:00 Uhr UTC
- Manuell über `workflow_dispatch`

## Verwendung

### Lokal ausführen
```bash
./gradlew run
```

Der Webserver ist dann erreichbar unter: http://localhost:8080

### Docker Build
```bash
docker build -t scanning-example-kotlin .
docker run -p 8080:8080 scanning-example-kotlin
```

### Dependency Locking
Das Projekt verwendet Gradle Dependency Locking für reproduzierbare Builds:
```bash
./gradlew dependencies --write-locks
```

## Sicherheit

Das Projekt demonstriert absichtlich Sicherheitsprobleme für Schulungszwecke:
- Veraltete Log4j-Version (CVE-2021-44228 - Log4Shell)
- Unsichere Zip-Extraktion (Zip Slip Vulnerability)

**Nicht in Produktion verwenden!**
