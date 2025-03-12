# 🔑 Hotel_ML_Auth_Service - User data management

## 📌 Overview
Hotel_ML_Auth_Service is a backend microservice based on **Spring Boot**, that is responsible for storing user data, roles or privileges and modifying them, It is also responsible for generating JWT Tokens and the registration and login process. It exchanges data with Hotel_ML_APIGateway_Service by sending messages through Apache Kafka brokers.

## ❗ Important information
> To launch an application using the described service, go to:
> ➡️ [Main README](https://github.com/NiczSpeed/HotelML?tab=readme-ov-file#%EF%B8%8F-how-to-run-the-entire-system)

📌 **Key features::**
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
│   │   ├── RoleService.java                                    # RoleS repository
│   │   ├── UserDetailsRepository.java                          # UserDetails repository
│   │   ├── UserRepository.java                                 # User repository
│   ├── service                                             # Business logic layer
│   │   ├── InitDataService.java                                # Logic for adding privileges, roles, and admin accounts if they don't already exist
│   │   ├── JwtGeneratorService.java                            # JWT token generation logic
│   │   ├── PrivilegeService.java                               # The logic of privileges
│   │   ├── JRoleService.java                                   # The logic of the roles
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
│── compose.yml                                         # Launching applications and dependencies