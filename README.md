# Spring Boot JWT Validator

A complete Spring Boot REST API that generates a demo JWT and validates whether a Bearer token is **VALID** or **INVALID**. Swagger UI is included for testing.

## Requirements
- Java 17+
- Maven 3.6+

## Run the project
Open a terminal in this project folder and run:

```bash
mvn spring-boot:run
```

Or import the folder into IntelliJ IDEA / Eclipse as an existing Maven project and run `JwtValidatorApplication`.

## Swagger testing
1. Start the application.
2. Open http://localhost:8080/swagger-ui/index.html
3. Expand `POST /api/auth/demo-token` and click **Try it out**, then **Execute**.
4. Copy the `token` value from the response.
5. Click the **Authorize** button at the top of Swagger.
6. Paste only the token value (do not add the word `Bearer` because Swagger adds it automatically), then click **Authorize** and **Close**.
7. Expand `GET /api/auth/validate` and click **Try it out** → **Execute**.
8. A valid token returns HTTP 200 and `valid: true`, `status: VALID`.
9. To test an invalid token, change one or more characters in the token in the Authorize dialog, authorize again, and execute validation. It returns HTTP 401 and `valid: false`, `status: INVALID`.

## Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/auth/health` | Check whether the API is running |
| POST | `/api/auth/demo-token?username=mokshith` | Generate a demo JWT valid for one hour |
| GET | `/api/auth/validate` | Validate the Bearer token supplied through Swagger Authorize |

## Example valid response

```json
{
  "valid": true,
  "status": "VALID",
  "subject": "mokshith",
  "message": "JWT signature and expiration are valid."
}
```

## Example invalid response

```json
{
  "valid": false,
  "status": "INVALID",
  "subject": null,
  "message": "JWT is invalid, expired, malformed, or has an incorrect signature."
}
```

## Important security note
This project uses a hard-coded demo secret only to make local lab testing easy. Do not use it in production. Store a strong secret in an environment variable or secret manager, rotate it appropriately, and never expose token-generation endpoints publicly.
