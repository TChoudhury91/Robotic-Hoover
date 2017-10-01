# Robotic-Hoover

- Since it is a spring boot application, just run RoboticHooverApplication. Once the service is up and running,
  the rest end point is http://localhost:8080/robotic-hoover/clean, followed by the json input you wish to send

- The application is packaged with an in memory db, h2. To access it please go to http://localhost:8080/h2

- I have also included tests, which i used to do TDD. Using a spock testing framework written in Groovy


# Assumptions made

- A patch with a coordinate on the edge of boundary, will not be included as part of the cleaning process, since the
  coordinate denotes the bottom left corner. So this coordinate will be a part of the wall, and the patch will be
  outside of wall
- Instructions in lower and upper case will be allowed
