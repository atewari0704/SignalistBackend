# 📊 SignalList Backend API

A Spring Boot REST API for managing stock watchlists and price alerts. Built with Java 21, Spring Boot 4.0, and MongoDB.

> ⚠️ **Security Notice:** This application uses environment variables for configuration. Make sure to create your own `.env` file from `.env.example` and **never commit sensitive credentials** to version control.

## 🎯 Overview

SignalList is a backend service that allows users to:
- **Track stocks** by maintaining personalized watchlists
- **Set price alerts** for stocks with customizable conditions (above/below target prices)
- **Manage alerts** with real-time status tracking
To deliver a seamless user experience, this backend is designed to integrate with the SignalList frontend application.

You can find the frontend repository at [SignalList Frontend](https://github.com/atewari0704/Signalist).

Perfect for traders and investors who want to monitor their favorite stocks and get notified when prices hit specific targets.

## 🏗️ Architecture

### Tech Stack
- **Java 21** - Modern Java features and performance
- **Spring Boot 4.0** - Application framework
- **MongoDB** - NoSQL database for flexible data storage
- **Lombok** - Reduces boilerplate code
- **Maven** - Dependency management and build tool
- **dotenv-java** - Environment variable management

### Project Structure
```
src/main/java/com/signallist/
├── SignallistApplication.java    # Application entry point
├── controller/                   # REST API endpoints
│   ├── AlertsController.java     # Alert management endpoints
│   └── WatchlistController.java  # Watchlist management endpoints
├── model/                        # Domain models
│   ├── Alert.java                # Alert document structure
│   ├── User.java                 # User document structure
│   └── Watchlist.java            # Watchlist document structure
├── repository/                   # MongoDB repositories
│   ├── AlertsRepository.java
│   ├── UserRepository.java
│   └── WatchlistRepository.java
└── service/                      # Business logic layer
    ├── AlertsService.java
    └── WatchlistService.java
```

## 📦 Data Models

### User
Represents a registered user in the system.
```java
{
  "id": "string",
  "name": "string",
  "email": "string",
  "emailVerified": boolean,
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Watchlist
Each user has a watchlist containing multiple stock items.
```java
{
  "id": "string",
  "userId": "string",
  "items": [
    {
      "symbol": "AAPL",
      "company": "Apple Inc.",
      "addedAt": "2026-01-14T10:30:00Z"
    }
  ]
}
```

### Alert
Price alerts set by users for specific stocks.
```java
{
  "id": "string",
  "userId": "string",
  "alerts": [
    {
      "id": "string",
      "symbol": "AAPL",
      "targetPrice": 277.25,
      "condition": "ABOVE",  // or "BELOW"
      "status": "ACTIVE",
      "createdAt": "2026-01-14T10:30:00Z"
    }
  ]
}
```

## 🔌 API Endpoints

### Watchlist Endpoints

#### Get User's Watchlist
```http
GET /watchlist/{userId}
```
Returns array of stock symbols in the user's watchlist.

**Response:** `["AAPL", "GOOGL", "MSFT"]`

---

#### Add Stock to Watchlist
```http
PUT /watchlist/addStock
Content-Type: application/json

{
  "userId": "user@example.com",
  "symbol": "AAPL",
  "company": "Apple Inc."
}
```
**Response:** `true` if successful, `false` otherwise

---

#### Remove Stock from Watchlist
```http
DELETE /watchlist/{userId}/{symbol}
```
**Response:** `true` if successful, `false` otherwise

---

#### Check if Stock is in Watchlist
```http
GET /watchlist/{userId}/{symbol}
```
**Response:** `true` if stock exists in watchlist, `false` otherwise

---

#### Health Check
```http
GET /watchlist/health
```
**Response:** `"Watchlist Service is healthy"`

---

### Alert Endpoints

#### Get User's Alerts
```http
GET /alerts/{userId}
```
Returns array of all alert items for the user.

**Response:**
```json
[
  {
    "id": "67...",
    "symbol": "AAPL",
    "targetPrice": 277.25,
    "condition": "ABOVE",
    "status": "ACTIVE",
    "createdAt": "2026-01-14T10:30:00Z"
  }
]
```

---

#### Add New Alert
```http
POST /alerts/addAlert
Content-Type: application/json

{
  "userId": "user@example.com",
  "symbol": "AAPL",
  "targetPrice": 277.25,
  "condition": "ABOVE"
}
```
**Response:** Returns the created alert object

---

#### Delete Alert
```http
DELETE /alerts/{userId}/{alertId}
```
**Response:** `true` if successful, `false` otherwise

---

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB instance)

### Configuration

1. **Create a `.env` file** in the project root:
```bash
# Copy the example file
cp .env.example .env

# Then edit .env with your actual MongoDB credentials
```

Your `.env` file should contain:
```env
MONGODB_USERNAME=your_mongodb_username
MONGODB_PASSWORD=your_mongodb_password
MONGODB_CLUSTER=your-cluster.mongodb.net
MONGODB_DATABASE=your_database_name
```

⚠️ **Important:** Never commit your `.env` file to version control!

2. **MongoDB Connection**
The application automatically loads environment variables from `.env` and uses them to connect to MongoDB Atlas.

Connection string format:
```
mongodb+srv://${MONGODB_USERNAME}:${MONGODB_PASSWORD}@${MONGODB_CLUSTER}/${MONGODB_DATABASE}
```

### Running the Application

1. **Clone the repository**
```bash
git clone <repository-url>
cd signalListBackend
```

2. **Install dependencies**
```bash
./mvnw clean install
```

3. **Run the application**
```bash
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080` (default port).

### Building for Production
```bash
./mvnw clean package
java -jar target/signallist-0.0.1-SNAPSHOT.jar
```

## 🔧 Key Features

### Environment Variable Management
The application uses `dotenv-java` to load environment variables from a `.env` file, keeping sensitive configuration out of source control.

### MongoDB Auto-Indexing
Automatic index creation is enabled for any `@Indexed` annotations in your models, optimizing query performance.

### Upsert Operations
The service layer uses MongoDB's upsert functionality to automatically create user documents if they don't exist when adding items.

### Flexible Queries
- Case-insensitive stock symbol matching
- Support for both String and ObjectId formats for alert IDs
- Efficient array manipulation with `$push` and `$pull` operators

## 🛠️ Development

### Testing
```bash
./mvnw test
```

### Code Style
The project uses Lombok to reduce boilerplate:
- `@Data` - Generates getters, setters, toString, equals, and hashCode
- `@AllArgsConstructor` - Generates constructor with all fields
- `@NoArgsConstructor` - Generates no-args constructor

## 📝 Notes

- **User ID**: Throughout the API, `userId` typically corresponds to the user's email address
- **MongoDB Collections**: 
  - `user` - Stores user information
  - `watchlists` - Stores user watchlists
  - `alerts` - Stores price alerts
- **Alert Conditions**: Only "ABOVE" and "BELOW" are currently supported
- **Alert Status**: New alerts are automatically set to "ACTIVE"

## 🔒 Security Considerations

- Environment variables are used for sensitive configuration
- `.env` file is excluded from version control via `.gitignore`
- MongoDB connection uses secure SSL connection (mongodb+srv://)

## 📚 Future Enhancements

Potential areas for expansion:
- User authentication and authorization
- WebSocket support for real-time alert notifications
- Alert triggering mechanism (background job to check prices)
- Email/SMS notifications when alerts are triggered
- Historical price data tracking
- Portfolio management features
- Support for more alert conditions (percentage changes, volume triggers, etc.)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is currently unlicensed. Please add appropriate license information as needed.

---

**Built with ❤️ using Spring Boot and MongoDB**

