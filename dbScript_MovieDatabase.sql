CREATE DATABASE MovieDatabase;
GO
USE MovieDatabase;
GO

CREATE TABLE Movie (
    ID INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(255),
	PubDate NVARCHAR(90),
    OriginalTitle NVARCHAR(255),
    Description NVARCHAR(MAX),
    Duration INT,
    Year INT,
    Poster NVARCHAR(MAX),
    Link NVARCHAR(MAX),
    Reservation NVARCHAR(MAX),
    DisplayDate NVARCHAR(90),
    Performances NVARCHAR(MAX),
    Trailer NVARCHAR(255)
);

CREATE TABLE AccountType (
    ID INT PRIMARY KEY IDENTITY,
    Type NVARCHAR(255)
);



CREATE TABLE [User] (
    ID INT PRIMARY KEY IDENTITY,
    Username NVARCHAR(255),
    PassHash NVARCHAR(Max),
	PassSalt NVARCHAR(Max),
    AccountTypeID INT FOREIGN KEY REFERENCES AccountType(ID)
);




CREATE TABLE Genre (
    ID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(255)
);

CREATE TABLE Person (
    ID INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(255)
);

CREATE TABLE Role (
    ID INT PRIMARY KEY IDENTITY,
    Role NVARCHAR(255)
);

CREATE TABLE MovieGenreRelation (
    ID INT PRIMARY KEY IDENTITY,
    MovieID INT FOREIGN KEY REFERENCES Movie(ID),
    GenreID INT FOREIGN KEY REFERENCES Genre(ID)
);

CREATE TABLE MovieCrewMember (
    ID INT PRIMARY KEY IDENTITY,
    MovieID INT FOREIGN KEY REFERENCES Movie(ID),
    PersonID INT FOREIGN KEY REFERENCES Person(ID),
    RoleID INT FOREIGN KEY REFERENCES Role(ID)
);

CREATE TABLE WatchedMovies (
	ID INT PRIMARY KEY IDENTITY,
    UserID INT FOREIGN KEY REFERENCES [User](ID),
    MovieID INT FOREIGN KEY REFERENCES Movie(ID),
  
);




------Procedure----


GO

USE MovieDatabase;
GO

--------------------------Movie------------
----- CREATE PROCEDURE
CREATE or alter PROCEDURE CreateMovie
    @Title NVARCHAR(255),
	@PubDate NVARCHAR(90),
    @OriginalTitle NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @Duration INT,
    @Year INT,
    @Poster NVARCHAR(MAX),
    @Link NVARCHAR(MAX),
    @Reservation NVARCHAR(MAX),
    @DisplayDate NVARCHAR(90),
    @Performances NVARCHAR(MAX),
    @Trailer NVARCHAR(255),
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Movie WHERE Title = @Title AND Year = @Year AND DisplayDate = @DisplayDate)
    BEGIN
        INSERT INTO Movie(Title, PubDate, OriginalTitle, Description, Duration, Year, Poster, Link, Reservation, DisplayDate, Performances, Trailer)
        VALUES (@Title,@PubDate, @OriginalTitle, @Description, @Duration, @Year, @Poster, @Link, @Reservation, @DisplayDate, @Performances, @Trailer)
        
        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM Movie WHERE Title = @Title AND Year = @Year AND DisplayDate = @DisplayDate
    END
END
GO



-- UPDATE PROCEDURE
CREATE or alter PROCEDURE UpdateMovie
    @ID INT,
	@PubDate NVARCHAR(90),
    @Title NVARCHAR(255),
    @OriginalTitle NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @Duration INT,
    @Year INT,
    @Poster NVARCHAR(MAX),
    @Link NVARCHAR(MAX),
    @Reservation NVARCHAR(MAX),
    @DisplayDate NVARCHAR(90),
    @Performances NVARCHAR(MAX),
    @Trailer NVARCHAR(255)
AS
BEGIN
    UPDATE Movie
    SET Title = @Title,
		PubDate = @PubDate,
        OriginalTitle = @OriginalTitle,
        Description = @Description,
        Duration = @Duration,
        Year = @Year,
        Poster = @Poster,
        Link = @Link,
        Reservation = @Reservation,
        DisplayDate = @DisplayDate,
        Performances = @Performances,
        Trailer = @Trailer
    WHERE ID = @ID
END
GO


-- DELETE PROCEDURE
CREATE or alter PROCEDURE DeleteMovie
    @ID INT
AS
BEGIN
    -- Delete rows in referencing tables first
    DELETE FROM MovieGenreRelation
    WHERE MovieID = @ID;

    DELETE FROM MovieCrewMember
    WHERE MovieID = @ID;

    DELETE FROM WatchedMovies
    WHERE MovieID = @ID;

    -- Then delete the movie
    DELETE FROM Movie
    WHERE ID = @ID;
END
GO


-- SELECT PROCEDURE
CREATE PROCEDURE SelectMovie
    @ID INT
AS
BEGIN
    SELECT * FROM Movie
    WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectMovies
AS
BEGIN
    SELECT * FROM Movie
END
GO
------------------------ Genre
----- CREATE PROCEDURE

CREATE or alter PROCEDURE CreateGenre
    @Name NVARCHAR(255),
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Genre WHERE Name = @Name)
    BEGIN
        INSERT INTO Genre(Name)
        VALUES (@Name)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM Genre WHERE Name = @Name
    END
END
GO

----- UPDATE PROCEDURE

CREATE PROCEDURE UpdateGenre
    @ID INT,
    @Name NVARCHAR(255)
AS
BEGIN
    UPDATE Genre
    SET Name = @Name
    WHERE ID = @ID
END
GO

----- DELETE PROCEDURE
CREATE PROCEDURE DeleteGenre
    @ID INT
AS
BEGIN
    DELETE FROM Genre WHERE ID = @ID
END
GO

----- SELECT PROCEDURE
CREATE PROCEDURE SelectGenre
    @ID INT
AS
BEGIN
    SELECT * FROM Genre WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectGenres
AS
BEGIN
    SELECT * FROM Genre
END
GO



----------------------------- Person
----- CREATE PROCEDURE
CREATE or alter PROCEDURE CreatePerson
    @Name NVARCHAR(255),
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Person WHERE Name = @Name)
    BEGIN
        INSERT INTO Person(Name)
        VALUES (@Name)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM Person WHERE Name = @Name
    END
END
GO

----- UPDATE PROCEDURE
CREATE PROCEDURE UpdatePerson
    @ID INT,
    @Name NVARCHAR(255)
AS
BEGIN
    UPDATE Person
    SET Name = @Name
    WHERE ID = @ID
END
GO
----- DELETE PROCEDURE
CREATE PROCEDURE DeletePerson
    @ID INT
AS
BEGIN
    DELETE FROM Person WHERE ID = @ID
END
GO
----- SELECT PROCEDURE
CREATE PROCEDURE SelectPerson
    @ID INT
AS
BEGIN
    SELECT * FROM Person WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectPersons
AS
BEGIN
    SELECT * FROM Person
END
GO

---------------------- Role
----- CREATE PROCEDURE
CREATE or alter PROCEDURE CreateRole
    @Role NVARCHAR(255),
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Role WHERE Role = @Role)
    BEGIN
        INSERT INTO Role(Role)
        VALUES (@Role)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM Role WHERE Role = @Role
    END
END
GO

----- UPDATE PROCEDURE
CREATE PROCEDURE UpdateRole
    @ID INT,
    @Role NVARCHAR(255)
AS
BEGIN
    UPDATE Role
    SET Role = @Role
    WHERE ID = @ID
END
GO
----- DELETE PROCEDURE
CREATE PROCEDURE DeleteRole
    @ID INT
AS
BEGIN
    DELETE FROM Role WHERE ID = @ID
END
GO
----- SELECT PROCEDURE
CREATE PROCEDURE SelectRole
    @ID INT
AS
BEGIN
    SELECT * FROM Role WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectRoles
AS
BEGIN
    SELECT * FROM Role
END
GO


----------------------------- MovieGenreRelation
----- CREATE PROCEDURE

---------------------- MovieGenreRelation
----- CREATE PROCEDURE
CREATE or alter PROCEDURE CreateMovieGenreRelation
    @MovieID INT,
    @GenreID INT,
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM MovieGenreRelation WHERE MovieID = @MovieID AND GenreID = @GenreID)
    BEGIN
        INSERT INTO MovieGenreRelation(MovieID, GenreID)
        VALUES (@MovieID, @GenreID)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM MovieGenreRelation WHERE MovieID = @MovieID AND GenreID = @GenreID
    END
END
GO

----- UPDATE PROCEDURE
CREATE PROCEDURE UpdateMovieGenreRelation
    @ID INT,
    @MovieID INT,
    @GenreID INT
AS
BEGIN
    UPDATE MovieGenreRelation
    SET MovieID = @MovieID, GenreID = @GenreID
    WHERE ID = @ID
END
GO

----- DELETE PROCEDURE
CREATE or alter PROCEDURE DeleteMovieGenreRelation
    @ID INT
AS
BEGIN
    DELETE FROM MovieGenreRelation WHERE MovieID = @ID
END
GO

----- SELECT PROCEDURE
CREATE PROCEDURE SelectMovieGenreRelation
    @ID INT
AS
BEGIN
    SELECT * FROM MovieGenreRelation WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectMovieGenreRelations
AS
BEGIN
    SELECT * FROM MovieGenreRelation
END
GO


---------------------- MovieCrewMember
----- CREATE PROCEDURE
CREATE or alter PROCEDURE CreateMovieCrewMember
    @MovieID INT,
    @PersonID INT,
    @RoleID INT,
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM MovieCrewMember WHERE MovieID = @MovieID AND PersonID = @PersonID AND RoleID = @RoleID)
    BEGIN
        INSERT INTO MovieCrewMember(MovieID, PersonID, RoleID)
        VALUES (@MovieID, @PersonID, @RoleID)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM MovieCrewMember WHERE MovieID = @MovieID AND PersonID = @PersonID AND RoleID = @RoleID
    END
END
GO

----- UPDATE PROCEDURE
CREATE PROCEDURE UpdateMovieCrewMember
    @ID INT,
    @MovieID INT,
    @PersonID INT,
    @RoleID INT
AS
BEGIN
    UPDATE MovieCrewMember
    SET MovieID = @MovieID, PersonID = @PersonID, RoleID = @RoleID
    WHERE ID = @ID
END
GO

----- DELETE PROCEDURE
CREATE or alter PROCEDURE DeleteMovieCrewMember
    @ID INT
AS
BEGIN
    DELETE FROM MovieCrewMember WHERE MovieID = @ID
END
GO

----- SELECT PROCEDURE
CREATE PROCEDURE SelectMovieCrewMember
    @ID INT
AS
BEGIN
    SELECT * FROM MovieCrewMember WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectMovieCrewMembers
AS
BEGIN
    SELECT * FROM MovieCrewMember
END
GO




-- AccountType
CREATE PROCEDURE CreateAccountType
    @Type NVARCHAR(255),
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM AccountType WHERE Type = @Type)
    BEGIN
        INSERT INTO AccountType(Type)
        VALUES (@Type)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM AccountType WHERE Type = @Type
    END
END
GO

CREATE PROCEDURE UpdateAccountType
    @ID INT,
    @NewType NVARCHAR(255)
AS
BEGIN
    UPDATE AccountType
    SET Type = @NewType
    WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectAccountType
    @ID INT
AS
BEGIN
    SELECT * FROM AccountType
    WHERE ID = @ID
END
GO

CREATE PROCEDURE DeleteAccountType
    @ID INT
AS
BEGIN
    DELETE FROM AccountType
    WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectAccountTypes
AS
BEGIN
    SELECT * FROM AccountType
END
GO

-- User
CREATE PROCEDURE CreateUser
    @Username NVARCHAR(255),
    @PassHash NVARCHAR(MAX),
    @PassSalt NVARCHAR(MAX),
    @AccountTypeID INT,
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM [User] WHERE Username = @Username)
    BEGIN
        INSERT INTO [User](Username, PassHash, PassSalt, AccountTypeID)
        VALUES (@Username, @PassHash, @PassSalt, @AccountTypeID)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM [User] WHERE Username = @Username
    END
END
GO

CREATE PROCEDURE UpdateUser
    @ID INT,
    @NewUsername NVARCHAR(255),
    @NewPassHash NVARCHAR(MAX),
    @NewPassSalt NVARCHAR(MAX),
    @NewAccountTypeID INT
AS
BEGIN
    UPDATE [User]
    SET Username = @NewUsername, PassHash = @NewPassHash, PassSalt = @NewPassSalt, AccountTypeID = @NewAccountTypeID
    WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectSalt
    @Username NVARCHAR(255)
AS
BEGIN
    SELECT PassSalt FROM [User]
    WHERE Username = @Username
END
GO


CREATE or alter PROCEDURE SelectUserWithCredentials
    @Username NVARCHAR(255),
    @PassHash NVARCHAR(MAX),
    @PassSalt NVARCHAR(MAX)

AS
BEGIN
    SELECT * FROM [User]
    WHERE Username = @Username AND PassSalt = @PassSalt AND PassHash = @PassHash
END
go


CREATE PROCEDURE SelectUser
    @ID INT
AS
BEGIN
    SELECT * FROM [User]
    WHERE ID = @ID
END
GO


CREATE PROCEDURE DeleteUser
    @ID INT
AS
BEGIN
    DELETE FROM [User]
    WHERE ID = @ID
END
GO

CREATE PROCEDURE SelectUsers
AS
BEGIN
    SELECT * FROM [User]
END
GO

-------------------------------------------------------------------------------------------------------------

CREATE or alter PROCEDURE CreateWatchedMovie
    @UserID INT,
    @MovieID INT,
    @ID INT OUTPUT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM WatchedMovies WHERE UserID = @UserID AND MovieID = @MovieID)
    BEGIN
        INSERT INTO WatchedMovies(UserID, MovieID)
        VALUES (@UserID, @MovieID)

        SET @ID = SCOPE_IDENTITY()
    END
    ELSE
    BEGIN
        SELECT @ID = ID FROM WatchedMovies WHERE UserID = @UserID AND MovieID = @MovieID
    END
END
GO

----- UPDATE PROCEDURE
CREATE PROCEDURE UpdateWatchedMovie
    @ID INT,
    @UserID INT,
    @MovieID INT
AS
BEGIN
    UPDATE WatchedMovies
    SET UserID = @UserID, MovieID = @MovieID
    WHERE ID = @ID
END
GO

----- DELETE PROCEDURE
CREATE PROCEDURE DeleteWatchedMovie
    @ID INT
AS
BEGIN
    DELETE FROM WatchedMovies WHERE ID = @ID
END
GO

----- SELECT PROCEDURE
CREATE or alter PROCEDURE SelectWatchedMovie
    @UserID INT
AS
BEGIN
    SELECT * FROM WatchedMovies WHERE UserID = @UserID
END
GO

CREATE or alter PROCEDURE SelectWatchedMovies
AS
BEGIN
    SELECT * FROM WatchedMovies
END
GO

CREATE or ALTER PROCEDURE SelectWatchedMoviesWithId
    @UserID INT
AS
BEGIN
    SELECT * FROM WatchedMovies
    WHERE UserID = @UserID
END
GO

-------------------------------------------------------------------------------

CREATE or alter PROCEDURE ClearTables
AS
BEGIN
    
    EXEC sp_MSforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all";

	DELETE FROM WatchedMovies;
    DELETE FROM MovieCrewMember;
    DELETE FROM MovieGenreRelation;
    DELETE FROM Person;
    DELETE FROM Movie;
    DELETE FROM Genre;
	--DELETE FROM [User];
    
    
    EXEC sp_MSforeachtable "ALTER TABLE ? WITH CHECK CHECK CONSTRAINT all";
END;

EXEC ClearTables

go
CREATE OR ALTER PROCEDURE CreateAdmin 
AS
BEGIN
    DECLARE @AdminCount INT;
    SELECT @AdminCount = COUNT(*) FROM [User] WHERE AccountTypeID = 1;

    IF @AdminCount = 0
    BEGIN
        DECLARE @PassSalt NVARCHAR(MAX) = 'Bupph5yMdHAztZXg7Rhtcg==';
        DECLARE @PassHash NVARCHAR(MAX) = '0BphBPhD3GOt8hGr0iY2fA==';
        INSERT INTO [User] (Username, PassHash, PassSalt, AccountTypeID)
        VALUES ('admin', @PassHash, @PassSalt, 1);
    END
END


