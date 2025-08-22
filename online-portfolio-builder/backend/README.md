# Portfolio Builder Backend (Spring Boot)

## Run
1. Update `src/main/resources/application.properties` with your MySQL username/password.
2. Ensure MySQL has a database named `portfolio_db` (Spring will create tables).
3. From this `backend` folder run:
   ```bash
   mvn spring-boot:run
   ```

## Key Endpoints
- `POST /api/portfolio/create` — Save data to DB.
- `GET /api/portfolio/{id}` — Fetch saved data.
- `POST /api/portfolio/preview` — Returns rendered HTML string for preview.
- `POST /api/portfolio/generate-zip` — Returns a ZIP of a static site (index.html + assets).
