# Tourist Record Update API with Custom Token Validation

Spring Boot + MySQL + Swagger project. It updates a tourist's visited destinations and contact details only when a valid custom token is supplied in the PUT request header.

## 1. Requirements
- JDK 17
- Maven 3.6+
- MySQL Server (Workbench is optional)
- VS Code / IntelliJ / Spring Tool Suite

## 2. Create the database
Open MySQL Workbench or MySQL command line and run:
```sql
CREATE DATABASE tourist_db;
```
The application creates the `tourists` table automatically.

## 3. Configure MySQL password
Open `src/main/resources/application.properties` and replace:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```
with your actual MySQL root password. Keep the token value `HOTEL-TOURIST-2026` for the test, or change it and use the same value in Swagger.

## 4. Run the application
Extract the ZIP. Open the extracted `TouristRecordTokenValidation` folder in VS Code / STS.
Open a terminal in that folder and run:
```bash
mvn clean spring-boot:run
```
Wait until the console shows that the application started on port 8080.

## 5. Open Swagger
Open this URL in Chrome:
http://localhost:8080/swagger-ui/index.html

## 6. Create a tourist record first
The PUT operation updates an existing record, so create one record before testing.
In Swagger, expand **POST /api/tourists** → Try it out and use:
```json
{
  "name": "Ravi Kumar",
  "email": "ravi@gmail.com",
  "phone": "9876543210",
  "visitedDestinations": "Hyderabad"
}
```
Click Execute. A successful response returns the record and its generated `id` (usually 1).

## 7. Test valid token (expected 200 OK)
Expand **PUT /api/tourists/{id}** → Try it out.
- Enter the generated id, e.g. `1`.
- In the request body, enter:
```json
{
  "name": "Ravi Kumar",
  "email": "ravi.new@gmail.com",
  "phone": "9123456780",
  "visitedDestinations": "Hyderabad, Goa, Ooty"
}
```
- In the header section, add header name `X-API-TOKEN` and value `HOTEL-TOURIST-2026`.
- Click Execute.
Expected: **200 OK**, with the updated tourist details in the response.

If Swagger UI does not show a header input automatically, use the Swagger **Authorize** control only if a security scheme is configured; otherwise use Postman/curl for the header. The curl example below always works.

## 8. Test invalid token (expected 500 Internal Server Error)
Send the same PUT request but set:
`X-API-TOKEN: WRONG-TOKEN`
Expected: **500 Internal Server Error** and response:
```json
{"error":"Invalid or missing request token"}
```
No database update takes place.

## 9. Verify the updated database record
Run in MySQL:
```sql
USE tourist_db;
SELECT * FROM tourists;
```

## 10. Test with curl (optional)
Valid token:
```bash
curl -i -X PUT http://localhost:8080/api/tourists/1 \
  -H "Content-Type: application/json" \
  -H "X-API-TOKEN: HOTEL-TOURIST-2026" \
  -d '{"name":"Ravi Kumar","email":"ravi.new@gmail.com","phone":"9123456780","visitedDestinations":"Hyderabad, Goa, Ooty"}'
```
Invalid token:
```bash
curl -i -X PUT http://localhost:8080/api/tourists/1 \
  -H "Content-Type: application/json" \
  -H "X-API-TOKEN: WRONG-TOKEN" \
  -d '{"name":"Ravi Kumar","email":"ravi.new@gmail.com","phone":"9123456780","visitedDestinations":"Hyderabad, Goa, Ooty"}'
```

## Important note about the assignment
This project deliberately returns HTTP **500** for an invalid token because that is what the lab task specifies. In real applications, invalid authentication tokens should normally return **401 Unauthorized** or **403 Forbidden**, not 500.

## API summary
| Method | Endpoint | Purpose | Token |
|---|---|---|---|
| POST | `/api/tourists` | Create a record for initial testing | Not required |
| GET | `/api/tourists/{id}` | Fetch a tourist record | Not required |
| PUT | `/api/tourists/{id}` | Update tourist details | `X-API-TOKEN` required |
