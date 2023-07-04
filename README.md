# OrderManagmentAPI
##WebServicesFinalProject[Order Management API System]
</p>
<p align="center">
  <img src="https://github.com/SalahAlDin2021/OrderManagmentAPI/assets/91832490/cd5ff2e2-bd48-411a-b756-aaff295e8ed7" alt="Order Management API Design" width="800px">
</p>

This project is a web service developed using Spring Boot. It aims to create a basic design of an order management system.

## Project Features

- Follows best practices and constraints.
- Secures all APIs with signup and authentication using Spring Security and JWT.
  - Customers can register as user accounts.
  - Users have access only to their own information, orders, and products.
  - Admin users have CRUD operations for stocks and can CREATE/UPDATE/DELETE products.
- Uses Swagger for API documentation.
- Implements HATEOAS to provide links for navigating to next statuses.
- Implements paging for products and orders.
- Supports API versioning.
- Provides EncryptionUtil for encrypting and decrypting resource IDs when necessary.
- API testing is performed using Postman, with separate collections for each resource with CRUD operations.
- Utilizes Docker for building and running the application.
- Code adheres to code conventions and emphasizes clean code practices.
  - Documentation is included within the code for better readability and understanding.
  - The application is packaged into appropriate components such as Controllers, Entities, Services, etc.
  - Class, variable, and function naming follows code convention standards.
- Follows the appropriate API architecture design.

## API Endpoints

- 'api/v1/customers`
- 'api/v1/customers/<customer_id>'
- 'api/v1/customers/<customer_id>/orders'
- 'api/v1/customers/<customer_id>/orders/<order_id>'
- 'api/v1/customers/<customer_id>/orders/<order_id>/products'
- 'api/v1/customers/<customer_id>/orders/<order_id>/products/<product_id>'
- 'api/v1/products'
- 'api/v1/products/<product_id>'
- 'api/v1/products/<product_id>/stocks'
- 'api/v1/products/<product_id>/stocks/<stock_id>'
