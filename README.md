# 🔑 Hotel_ML_Auth_Service - User data management

## 📖 Table of Contents
1. [📌 Overview](#-overview)
2. [🔧 Technologies](#-technologies)
3. [📂 Structure of the Code](#-structure-of-the-code)
4. [📊 Diagrams](#-diagrams)

---
## 📌 Overview
Hotel_ML_Auth_Service is a backend microservice based on **Spring Boot**, that is responsible for storing user data, roles or privileges and modifying them, It is also responsible for generating JWT Tokens and the registration and login process. It exchanges data with Hotel_ML_APIGateway_Service by sending messages through Apache Kafka brokers.

## ❗ Important Information
> To launch an application using the described service, go to:
> ➡️ [Main README](https://github.com/NiczSpeed/HotelML?tab=readme-ov-file#%EF%B8%8F-how-to-run-the-entire-system)

📌 **Key Features:**
- ✅ Login process
- ✅ Registration process
- ✅ User Data Modification
- ✅ Admin Privilege Management
- ✅ JWT Token Generation
- ✅ AES Encryption for Stored and Brokered Data
---

## 🔧 Technologies
| Component       | Technology |
|----------------|------------|
| **JęzykLanguage**  | Java 21 |
| **Framework**  | Spring Boot 3 |
| **Build Tool**  | Gradle (Kotlin) |
| **Database** | PostgreSQL |
| **Communication** | Apache Kafka |
| **Authorization** | Spring Security |
| **ORM** | Spring Data JPA (Hibernate) |
| **Orchestration** | Docker, Docker Compose |

---

## 📂 Structure of the Code
```plaintext
/backend-service
│── \src\main\java\com\ml\hotel_ml_apigateway_service\
│   ├── configuration/                                      # Microservice configuration layer
│   │   ├── KafkaConsumerConfiguration.java                     # Configuring Apache Kafka Consumer
│   │   ├── KafkaProducerConfiguration.java                     # Apache Kafka Producer Configuration
│   │   ├── KafkaTopicsConfiguration.java                       # Configuring Apache Kafka themes
│   │   ├── ObjectMapperConfiguration.java                      # ObjectMapper configuration
│   │   ├── SecurityConfiguration.java                          # Spring Security master configuration for the application
│   ├── dto/                                                # DTO layer
│   │   ├── GrantAdminLogDto.java                               # Dto for GrantAdminLog
│   │   ├── PrivilegeDto.java                                   # Dto for Privilege entity
│   │   ├── RoleDto.java                                        # Dto for Role entity
│   │   ├── UserDto.java                                        # Dto for User entity
│   │   ├── UserResponseDetailsDto.java                         # Dto manage the response for user data
│   ├── exceptions/                                         # Additional exceptions of the microservices
│   │   ├── ErrorWhileDecodeException.java                      # Exception signaling a decoding problem
│   │   ├── ErrorWhileEncodeException.java                      # Exception signaling an encoding problem
│   │   ├── ErrorWhileSavePrivilegesException.java              # Exception signaling an error when saving privileges to the database
│   │   ├── ErrorWhileSaveRolesException.java                   # Exception signaling an error when saving roles to the database
│   │   ├── ErrorWhileSaveUserException.java                    # Exception signaling an error when saving users to the databas
│   │   ├── PrivilegeNotExistException.java                     # Exception signaling privilege assignment error
│   │   ├── RoleNotExistException.java                          # Exception signaling role assignment error
│   │   ├── UserNotFoundException.java                          # Exception signaling user assignment error
│   ├── mapper/                                             # Layer mapping of microservice entities and DTOs
│   │   ├── GrantAdminLogMapper.java                            # GrantAdminLog Mapper
│   │   ├── RoleMapper.java                                     # Role Mapper
│   │   ├── UserMapper.java                                     # User Mapper
│   ├── model/                                              # Entity classes
│   │   ├── GrantAdminLog.java                                  # Entity used for login to grant admin role
│   │   ├── MyUserDetails.java                                  # Entity used to manage users by Spring Security
│   │   ├── Privilege.java                                      # Entity used to manage privileges
│   │   ├── Role.java                                           # Entity used to manage roles
│   │   ├── User.java                                           # Entity used to manage users
│   ├── repository/                                         # The layer of connection of entities to the database
│   │   ├── GrantAdminLogRepository.java                        # GrantAdminLog repository
│   │   ├── PrivilegeRepository.java                            # Privilege repository
│   │   ├── RoleService.java                                    # Role repository
│   │   ├── UserDetailsRepository.java                          # UserDetails repository
│   │   ├── UserRepository.java                                 # User repository
│   ├── service                                             # Business logic layer
│   │   ├── InitDataService.java                                # Logic for adding privileges, roles, and admin accounts if they don't already exist
│   │   ├── JwtGeneratorService.java                            # JWT token generation logic
│   │   ├── PrivilegeService.java                               # The logic of privileges
│   │   ├── RoleService.java                                    # The logic of the roles
│   │   ├── UserDetailsService.java                             # The logic of UserDetails
│   │   ├── UserService.java                                    # User logic among others login, registration or data modification
│   ├── utils/                                              # Additional functionalities 
│   │   ├── encryptors/                                         # Encryptor layer
│   │   |   ├── LocalDateConverter.java                             # LocalDate converter
│   │   |   ├── StringConverter.java                                # String converter
|   |   |── Encryptor.java                                      # Class inheriting EncryptorUtil to provide data encryption
|   |   |── EncryptorUtil.java                                      # A class containing encryption and decryption methods
|   |── HotelMlApiGatewayServiceApplication.java            # Spring Boot main class
│── src/main/resources/application.yml                      # Application configuration
│──.env                                                 # Environment variables for the Docker container
│── Dockerfile                                          # Docker image definition
```
## 📊 Diagrams

### 🗂️ Entity-Relationship Diagram (ERD)
This diagram represents the relationships between entities in the database.

🔗 [View the full ERD](docs/ERD/Hotel_ML_Auth_Service.svg)

---

### 🏛 Class Diagrams
These diagrams illustrate the main object-oriented structure of the application, including entities, their attributes, methods, and relationships.

---

#### 💼 GrantAdminLog Business Logic Classes
This diagram ilustrates GrantAdminLog business logic classes in service

🔗 [View the GrantAdminLog business logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Business_Logic_GrantAdminLog.svg)

---

#### 👑 Privilege Business Logic Classes
This diagram ilustrates privilege business logic classes in service

🔗 [View the Privilege business logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Business_Logic_Privilege.svg)

---

#### 🎭 Role Business Logic Classes
This diagram ilustrates role business logic classes in service

🔗 [View the role business logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Business_Logic_Role.svg)

---

#### 👨🏻‍💻 User Business Logic Classes
This diagram ilustrates user business logic classes in service

The diagram presents the concepts of Producer and Listener-Responder, defining roles in a Kafka-based and multithreaded architecture.

* Producer – sends data to the appropriate services via Apache Kafka brokers.
* Listener-Responder – receives a message and directly returns a response to the sender, without additional multithreading layers.

🔗 [View the user business logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Business_Logic_User.svg)

---

#### 🔒 Security Logic Classes
This diagram ilustrates security logic classes in service

🔗 [View the security logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Security.svg)

---

#### 🔐 JWT Logic Classes
This diagram ilustrates JWT logic classes in service

🔗 [View the JWT logic classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Logic_JWT.svg)

---

#### 🛡️ Encryption Classes
This diagram illustrates encryption classes in service

🔗 [View the encryption classes](docs/Class/Hotel_ML_Auth_Service_Diagram_encryption.svg)

---

#### 🚨 Exception Classes
This diagram illustrates exception classes in service

🔗 [View the exception classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Exceptions.svg)

---

#### ⚙️ Configuration Classes
This diagram ilustrates configuration classes in service

🔗 [View the configuration classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Configuration.svg)

---

#### 🚀 Data Initialization Classes
This diagram ilustrates data initialization classes in service

🔗 [View the data initialization classes](docs/Class/Hotel_ML_Auth_Service_Diagram_Data_Initialization.svg)

---
