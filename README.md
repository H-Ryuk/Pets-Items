# Pets Items Management System
## Project Overview
The Pets Items Management System is a RESTful API built using Spring Boot that allows users to manage pet-related items, handle user accounts, and perform various actions such as adding items to a cart or wishlist, making purchases, and managing item categories. The project also includes full CRUD functionality for items, users, and categories, as well as specific features for handling orders and wishlists.
## Features
### Cart Management
> * Add items to cart: Users can add multiple items to their shopping cart.
> * View cart items by user ID: Retrieve a list of all items in a user's cart.
> * Remove item from cart: Users can remove specific items from their cart.

## Category Management
> * View all categories: Retrieve a list of all item categories.
> * View category by name: Fetch details of a category using its name.
> * Update category: Modify an existing category by its ID.

## Item Management
> * Add items with category: Create a new item along with its associated category.
> * View all items: Retrieve all available items.
> * Paginate through items: Fetch items with pagination support.
> * View item by ID or name: Fetch specific items by their unique ID or name.
> * View items by category: Fetch items that belong to a specific category.
> * Update item: Update details of an existing item.
> * Delete item: Remove an item from the system.

## Order Management
> * Add order: Users can create an order for the items in their cart.
> * View all orders: Retrieve a list of all orders.
> * View order by ID: Fetch the details of a specific order by its ID.
> * Cancel order: Cancel an order by updating its status.

## User Management
> * User registration: New users can register by providing necessary details.
> * User login: Existing users can log in and retrieve an authentication token.
> * View all users: Fetch all registered users.
> * View user orders by ID: Retrieve a specific user's order details.
> * Update user details: Update user information.
> * Delete user: Remove a user from the system.
## Wishlist Management
> * Add items to wishlist: Users can add items to their wishlist.
> * Purchase items from wishlist: Users can purchase the items in their wishlist.
> * Remove item from wishlist: Users can remove specific items from their wishlist.
> * View wishlist by user ID: Fetch all items from a user's wishlist.

## Project Structure
> * Controllers: The application follows a RESTful API structure where each entity (Cart, Category, Item, Order, User, Wishlist) is managed by its respective controller. These controllers handle HTTP requests such as POST, GET, PUT, DELETE, and PATCH.
> * Services: The business logic for handling items, categories, users, orders, and carts resides in the service layer. The controllers interact with the service layer to fetch, modify, or delete data as required.

## Technologies Used
> * Java 17
> * Spring Boot
> * Spring Data JPA
> * Hibernate Validation
> * Jakarta Validation
> * Maven
> * Lombok

## Getting Started
### Prerequisites
> * Java 17 or higher
> * Maven 3.x
> * Any IDE (IntelliJ recommended)
> * Postman or cURL for API testing

### Clone the repository
```bash
git clone https://github.com/H-Ryuk/Pets-Items-App.git
cd your-repo
```
### Set up MySQL database
##### 1. Update the application.properties file with your database configuration:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Pets_Items?createDatabaseIfNotExist=true
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```
### Build and Run the Application
##### 1. Build the project using Maven:
```bash
mvn clean install
```
##### 2. Run the Spring Boot application:
````bash
mvn spring-boot:run
````
### Access the API
The API will be available at ``http://localhost:8080/api/v1/register``.
### Postman collection
You can use the Postman for testing the API.
## API Endpoints
> Public Endpoints
> * ``POST /api/v1/user/register:`` Register a new user.
> * ``POST /api/v1/user/login:`` Authentication and retrieve a JWT token.
### Secured Endpoints (require JWT)
> Item
> * ``GET /api/v1/item:`` Retrieve all items.
> * ``POST /api/v1/item:`` Add a new item (Admin only).
> * ``GET /api/v1/item/{itemId}:`` Retrieve a specific item by ID.
> * ``GET /api/v1/item/name{itemName}:`` Retrieve a specific item by Name.
> * ``PUT /api/v1/item/{itemId}:`` Update item by ID (Admin only).
> * ``DELETE /api/v1/item/{itemId}:`` Delete item by ID (Admin only).

### Security
JWT Authentication: Users need to register and login to get a JWT token.
Role-based access control: Administrator can create, update and delete items, while regular users (customer) can purchase items or add them to wish list.
## Contribution
Feel free to submit pull requests and contribute to this project.Please ensure your code follows the project's coding standards.

    
