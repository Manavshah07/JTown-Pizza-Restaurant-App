# JTown Pizza – Restaurant Web App
A full-stack restaurant web application built using Spring Boot, Thymeleaf, Spring Security, and H2 Database.
Users can browse the menu, register/login, add items to cart, place orders, and make table reservations.

## Project flow (how this application works)

1. **Landing / Browse**

   * User lands on the home page and can view menu, login, or register.

2. **User Registration & Login**

   * New users register with email and password. A verification / password-reset mechanism can be configured via mail sender.
   * Registered users log in. Spring Security handles authentication and role-based access (USER, ADMIN).

3. **Browse Menu**

   * Menu fetched from database
   * Users can view items, categories, and descriptions

4. **Checkout & Place Order**

   * User enters basic details (name, email).
   * Order stored in database with items + total price.
   * Cart clears after order placement.

5. **View Orders**

   * User and admin can see order history.
   * Includes order details and timestamp.

6. **Table Reservations**

   * Users can book a table by entering name, email, party size & date/time.
   * All reservations displayed in a list (admin view).

7. **Admin Features**

   * Manage menu (via DB).
   * View all orders
   * View all reservations

---

## Features included in this project

* Clean Spring Boot MVC architecture (Controller → Service → Repository).
* Thymeleaf server-side templating for all front-end pages.
* User registration, login, logout, and role-based authorization (USER / ADMIN).
* Menu browsing with detailed information for each food item (name, description, price, category).
* Shopping cart functionality (add/update/remove items).
* Order creation (checkout flow) and order history for users.
* Table reservation system allowing users to book a date, time, and party size.
* Admin access to view all orders and reservations from users.
* Demo data seeding for menu items and users (admin + normal user).
* H2 in-memory database support for easy local development.
* Maven build lifecycle and packaging (WAR/JAR).

---
## Quick setup & run (local development)

> These steps assume you already cloned the repository into a local folder.

1. **Clone the repo**

```bash
git clone https://github.com/mdtalalwasim/Ecommerce_Store.git
cd Ecommerce_Store
```

2. **Create a MySQL database**

Open MySQL and create a database (example name `ecommerce_store`):

```sql
CREATE DATABASE ecommerce_store;
```

3. **Configure application properties**

Open `src/main/resources/application.properties` (or `application.yml`) and set your DB credentials and other environment-specific settings, for example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_store
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
# mail and other properties (optional)
# spring.mail.host=
# spring.mail.username=
# spring.mail.password=
```

4. **Build and run**

Run with Maven (embedded container):

```bash
mvn clean package
# then either
mvn spring-boot:run
# or run the generated jar/war
java -jar target/<artifact-name>.jar
```

If you prefer WAR deployment, copy the generated WAR from `target/` into your Tomcat `webapps/` directory and start Tomcat.

5. **Access the app**

Open your browser at `http://localhost:8080` (or whichever port is configured).

---



