# internet-shop

# Context
- [1. Project Purpose](#1-project-purpose)<br>
- [2. Project structure](#2-project-structure)<br>
- [3. For developer](#3-for-developer) <br>
- [4. Author](#4-author)  

 ### 1. Project Purpose
this project is a simple version internet-shop where realize:
<ul>
    <li>Registration and log in forms</li>
    <li>realize two roles(Admin and User)</li>
    <li>added service shopping cart and order</li>
</ul>
This project has authentication and authorization filters, DAO and Service layers, Servlets and JSP pages.

DAO layer has two implementations: inner storage based on List and outer storage based on MySQL DB.

 ### 2. Project structure
<ul>
    <li>Java 11</li>
    <li>Maven</li>
    <li>MavenCheckstylePlugin 3.1.1</li>
    <li>javax.servlet 3.1.0</li>
    <li>javax.jstl 1.2</li>
    <li>mysql-connector-java 8.0.15</li>
    <li>log4j 1.2.17</li>
</ul>

 ### 3. For developer
    To run this project you need to install:
        -Java 11
        -Tomcat
        -MySql(optional)
 
Add this project to IDEA as Maven project<br>
Add Java SDK 11 in project structure
    
    Configure Tomcat:
        -Add artifact
        -add Java SDK 11
        
Change path to your log file src/main/resources/log4j.properties<br>
To work with MySQL you need to:
    -Use file src/main/resources/init_db.sql to create schema and all the tables required by this app in MySQL DB
    -Change username and password to match with MySQL in src/main/java/mate/academy/internetshop/util/ConnectionUtil.java class on 20 ,21 line
 
 ### 4. Author
<li><a href="https://github.com/Andrewmazyar">Andriy Maziar</a></li>
