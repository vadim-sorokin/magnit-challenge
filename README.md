# magnit-challenge

The program is runnable as a simple Java project. All settings are stored in src/resources/settings.properties file. 
Please check them and edit on demand in order to have successful application start.

Approximate time of program execution for 1 million entries is: 25 seconds

DDL script:
create schema magnit;
create table test(
    ID int primary key auto_increment unique,
    FIELD int
)
