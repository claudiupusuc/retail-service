### What is this?
Server side APIs for managing and reporting on a generic retail business.

### How to build and run?
1. Setup a mongo database running on localhost or point to a running instance inside the `application.yaml` file located in `src/main/resources/application.yml`
```text
brew tap mongodb/brew

brew install mongodb-community

brew services start mongodb-community
```
2. Use Gradle to build.
```text
gradle build
```
3. Either run from Intellij run configuration `Application.java` or run the jar that was created by Gradle inside the build/libs folder.

**`In order to be able to see the order confirmation email be sent, you also need to update the sendgrid key and fromEmail inside the application properties file.`

### Architecture
The project is structured so that most packages inside the root `app` package are features.
Each package has an interface with the operations it exposes to any caller outside. This interfaces is named `operations`.

For example, the `cart` package has an interface named `CartOperations` which documents what each exposed operation does.
Same for `product` -> `ProductOperations` and `order` -> `OrderOperations`.

Other inner packages:
* `dao` used for data access objects. Pojos that are used for business logic and persistence.
* `api` where one or more implementation of the operations are found.
* `rest` controller and objects which help expose the functionality via web service.

The project defines custom Exceptions (a few basic ones, more specific ones can also be defined) and uses a controller advice in order to build custom error responses.
These custom error responses define codes which client apps can use.

### Exposed Endpoints
* `GET /products` Searches for products by product name
* `POST /carts` Add a product to cart
* `POST /orders` Place order
* `GET /orders/{orderNumber}` Search order
* `GET /reports//top-5-today` Top 5 products sold today
* `GET /reports/least-selling-products-month` Least sold products this month
* `GET /reports/sale-amount-per-day` A day by day breakdown of sale amounts between two dates

### Features
* Persistence of products in a database.
* Searching for products.
* Creating a cart of products and adding to that cart.
* Placing an order with the products from a cart taking into consideration the product availability.
* Notify the user who placed the order via email with his order number.
* Ability to search for a placed order by the order number.
* Reporting API:
  * Top 5 best selling products today
  * Least sold products this month (Specify how many).
  * A day by day breakdown of the sale amount (between two dates).

### What is missing and would be added given extra time?
* Authentication/Authorization logic. Ability to have users of the app with different Roles (CUSTOMER, ADMIN). And being able to define access level for endpoints based on these roles.
* Adopting a queuing system for any notification channels (SMS, Email, Push).
* Implementing some cache which could be populated in off-peak time with reporting data instead of calculating those on demand.
* Define a Dockerfile to be able to build this project as an image.