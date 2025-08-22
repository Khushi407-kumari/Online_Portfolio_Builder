# Online Portfolio Builder with Templates

This is a full-stack Angular + Spring Boot + MySQL project that lets users create a personal portfolio site from templates, preview it live, and download a ready-to-host ZIP.

## Quick Start

### Backend
1. Install JDK 17 and Maven.
2. Create a MySQL database `portfolio_db` and update credentials in `backend/src/main/resources/application.properties`.
3. In `backend/`:
   ```bash
   mvn spring-boot:run
   ```

### Frontend
1. Install Node.js 18+.
2. In `frontend/`:
   ```bash
   npm install
   npm start
   ```
3. Open http://localhost:4200

## Deploy the downloaded ZIP
- You can upload the ZIP contents (index.html + assets) to Netlify or GitHub Pages.
