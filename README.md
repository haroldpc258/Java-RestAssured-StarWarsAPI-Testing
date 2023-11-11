# **Java-RestAssured-StarWarsAPI-Testing**

This repository contains the development of a Java - RestAssured project. The project is designed to automate the testing of the [Star Wars API](https://swapi.dev/). The testing scenarios implemented include:

- Test the endpoint `people/2/` and check the success response, the skin color to be gold, and the amount of films it appears on (should be 6).
- Request the endpoint of the second movie in which `people/2/` was present. Check the release date to be in the correct date format, and the response to include characters, planets, starships, vehicles and species, each element including more than 1 element.
- Request the endpoint of the first planet present in the last film's response. Check the gravity and the terrains matching the exact same values returned by the request.
- On the same response from the planet, grab the url element on the response, and request it. Validate the response being exactly the same from the previous one.
- Request the `/films/7/` and check the response having a 404 code.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need:

- Java Development Kit (JDK)
- RestAssured
- TestNG
