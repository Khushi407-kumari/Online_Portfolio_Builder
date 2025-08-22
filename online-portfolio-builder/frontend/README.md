# Portfolio Builder Frontend (Angular)

## Run
1. Install Node.js 18+.
2. From `frontend/` run:
   ```bash
   npm install
   npm start
   ```
3. Make sure the backend is running on `http://localhost:8080`. A proxy is configured so frontend calls `/api/...` will be forwarded.

## Features
- Form to enter name, email, summary, skills, projects, social links
- Theme selection (classic / modern / dark)
- Live preview (HTML returned from backend)
- Download ZIP (static site)