# VidaSalud — Appointments Service

Microservicio de dominio que administra las atenciones/citas del sistema VidaSalud.

## Stack
- Java 17, Spring Boot, Spring Data JPA, H2 (en memoria)

## Cómo levantarlo localmente

\`\`\`bash
mvn clean package -DskipTests
java -jar target/ms-vidasalud-appointments-0.0.1-SNAPSHOT.jar
\`\`\`

Corre en el puerto `8081`.

## Endpoints

- `GET /api/appointments?status=` — lista atenciones, filtrable por estado
- `GET /api/appointments/{id}` — detalle
- `POST /api/appointments` — crea una atención (`{ "patientId": "...", "serviceId": "..." }`)
- `PUT /api/appointments/{id}/status` — cambia el estado (`{ "status": "CONFIRMADA" }`)

Estados posibles: `SOLICITADA`, `CONFIRMADA`, `EN_ESPERA`, `EN_ATENCION`, `CERRADA`, `CANCELADA`.
