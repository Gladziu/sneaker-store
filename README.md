# Sneaker Store
This project is a dynamic web application made in Spring Boot with a basic front-end. The design pattern I used is model-view-controller (MVC) and code structured is by function.


## Functionalities of the application
- Registration
- Adding to and removing from the shopping basket
- Order processing
- Order history

#### Admin capabilities:
- Adding new items
- Editing and deleting items
- Viewing registered users list

## Technologies

#### Code:

![Alt text](https://camo.githubusercontent.com/142c1ca57c4a85ddae844e196b62ffd9095552d94e559f68907d2f6031ece170/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6a6176615f31372d6f72616e67653f7374796c653d666f722d7468652d6261646765266c6f676f3d6f70656e6a646b266c6f676f436f6c6f723d7768697465)
![Alt text](https://camo.githubusercontent.com/cec4f3deeda1cde8d7e0729e689a9946a7286fc2a79be3e8b32fafa4b9f0396a/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f426f6f745f332d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d737072696e67266c6f676f436f6c6f723d7768697465)
![Alt text](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Alt text](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Alt text](https://camo.githubusercontent.com/c3b871d02afde0384d676dfb0872461bca6d18199375067e04e0d67ff0f9bfae/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6d6176656e2d4337314133363f7374796c653d666f722d7468652d6261646765266c6f676f3d6170616368656d6176656e266c6f676f436f6c6f723d7768697465)
#### Front-end:
![Alt text](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Alt text](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![Alt text](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
#### Tests:
![Alt text](https://camo.githubusercontent.com/6cf47d9ca3b8d62efb942ad8e9c9335f5bd5196ec76150d42fcc1a65f8486ddf/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a756e6974352d3235413136323f7374796c653d666f722d7468652d6261646765266c6f676f3d6a756e697435266c6f676f436f6c6f723d7768697465)
![Alt text](https://camo.githubusercontent.com/d38819e2d4efdc0a84acb94de6e2c94a02997234c5a72e72b1c250bb5a980e6f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d6f636b69746f2d3738413634313f7374796c653d666f722d7468652d6261646765)

## App deployed on Google Cloud
**Click** [here](https://valid-shine-410811.lm.r.appspot.com/sneaker-store/home)

Already registered user:
    ```
    guest@example.com
    ```
    ```
    Password123
    ```

To login as admin:
    ```
    admin@admin.com
    ```
    ```
    Administrator
    ```

## How to build the project on your own
1. Clone repository
    ```
    git clone https://github.com/Gladziu/sneaker-store.git
    ```
2. Go to cloned repository
    ```
    cd sneaker-store 
    ```
3. Install dependencies
    ```
    mvn clean install
    ```
4. Create MySQL database schema
    ```
    mysql -u root -p
    create database sneakerstore;
    ```
5. Run the project
    ```
   mvn spring-boot:run
   ```
6. Preview
    ```
    http://localhost:8080
    ```
**Note that**
To create administrator profile, register with email: 
    ``` 
    admin@admin.com
    ```

