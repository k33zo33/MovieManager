Project Description: Movie Management Application

This project aims to develop an application for managing movie-related data, including movies, actors, directors, and other associated entities. The application leverages object-oriented principles and utilizes a Microsoft SQL Server database for data storage. Key features and implementation details are outlined below:
Initialization and Database Management

    Creation of initialization DDL script for table creation and setup.
    Implementation of a script to delete all data from tables.
    Database interactions are performed via Java code using stored procedures and following the Repository pattern.

User Authentication and Roles

    The application supports two user roles: Administrator and User.
    At least one Administrator account is created during initialization.
    Access to the application is restricted via a login form, with user registration available for User role.

Administrator Interface

    The Administrator has the capability to delete all data from the database and upload new data via an RSS parser.
    RSS parser functionality parses XML data from a specified URL, saving it to the database and downloading associated images to a local directory.

User Interface

    Upon login, the User is presented with forms for viewing and updating entities (CRUD operations), such as movies, actors, and directors.
    Forms are well-organized and follow the Single Responsibility Principle, with each JPanel representing a distinct entity or functionality.
    Additional entities like Actor and Director have CRUD forms, and existing entities can be connected to new ones.
    Consistent handling of image deletion when modifying or deleting associated entities.

User Interface Components

    JTable and associated AbstractTableModel are utilized extensively for displaying entities and managing data.
    JMenu is employed for navigation within the application.
    Drag and drop functionality is implemented for selected features, such as adding an Actor to a Movie.

Additional Features

    XML download functionality is implemented using the JAXB library for any entity in the application.

By adhering to the outlined specifications and leveraging best practices in object-oriented programming, this project aims to deliver a robust and user-friendly application for managing movie-related data efficiently.
