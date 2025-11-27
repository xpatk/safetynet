# SafetyNet Alerts

SafetyNet Alerts is a Spring Boot application designed to manage and provide emergency services information. The system tracks persons, fire stations, and medical records to facilitate emergency response operations.

## Features

- **Person Management**: CRUD operations for person records
- **Fire Station Management**: Track fire station coverage areas and addresses
- **Medical Records**: Store and retrieve medical information for individuals
- **Emergency Alerts**:
    - Child alerts for specific addresses
    - Fire alerts with household information
    - Flood alerts by fire station
    - Phone alerts for fire station coverage areas
- **Community Information**: Retrieve email lists by city
- **Person Information**: Get detailed person info including medical records

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.3
- **Build Tool**: Maven
- **Logging**: Log4j2
- **Dependencies**:
    - Spring Web
    - Spring Actuator
    - Spring DevTools
    - Spring ActiveMQ
    - Lombok
    - Jackson (JSON processing)
    - SpringDoc OpenAPI (API documentation)
    - JaCoCo (code coverage)

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd safetynet
```

2. Build the project:
```bash
./mvnw clean install
```

## Running the Application

### Using Maven
```bash
./mvnw spring-boot:run
```

### Using Java
```bash
java -jar target/safetynet-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:9001`

## API Documentation

Once the application is running, access the API documentation at:
- Swagger UI: `http://localhost:9001/swagger-ui.html`
- OpenAPI JSON: `http://localhost:9001/v3/api-docs`

## API Endpoints

### Person Management
- `GET /person` - Get all persons
- `POST /person` - Create a new person
- `PUT /person` - Update a person
- `DELETE /person` - Delete a person

### Fire Station Management
- `GET /firestation` - Get all fire stations
- `POST /firestation` - Create a fire station mapping
- `PUT /firestation` - Update a fire station mapping
- `DELETE /firestation` - Delete a fire station mapping

### Medical Record Management
- `GET /medicalRecord` - Get all medical records
- `POST /medicalRecord` - Create a medical record
- `PUT /medicalRecord` - Update a medical record
- `DELETE /medicalRecord` - Delete a medical record

### Alert Endpoints
- `GET /firestation?stationNumber={station}` - Get persons covered by a fire station
- `GET /childAlert?address={address}` - Get children at an address
- `GET /phoneAlert?firestation={station}` - Get phone numbers for fire station coverage
- `GET /fire?address={address}` - Get fire alert information for an address
- `GET /flood/stations?stations={station_list}` - Get flood information for multiple stations
- `GET /personInfo?firstName={firstName}&lastName={lastName}` - Get detailed person information
- `GET /communityEmail?city={city}` - Get all email addresses in a city

## Testing

Run tests with Maven:
```bash
./mvnw test
```

### Code Coverage

Generate JaCoCo coverage report:
```bash
./mvnw clean test
```

View the coverage report at: `target/jacoco-report/index.html`

### Surefire Reports

Test reports are generated automatically and can be found at: `target/surefire-reports/`

## Project Structure

```
safetynet/
├── src/
│   ├── main/
│   │   ├── java/com/safetynet/safetynet/
│   │   │   ├── controller/         # REST controllers
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   ├── model/              # Domain models
│   │   │   ├── repository/         # Data access layer
│   │   │   ├── service/            # Business logic
│   │   │   └── SafetynetApplication.java
│   │   └── resources/
│   │       ├── data.json           # Initial data
│   │       └── application.properties
│   └── test/                       # Unit and integration tests
├── pom.xml
└── README.md
```

## Configuration

Application configuration can be found in `src/main/resources/application.properties`.

## Data Source

The application loads initial data from `src/main/resources/data.json` containing:
- Persons
- Fire stations
- Medical records

## Health Check

Spring Actuator provides health check endpoints:
- `http://localhost:9001/actuator/health`

## License

This project is licensed under the terms specified in the pom.xml file.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request
