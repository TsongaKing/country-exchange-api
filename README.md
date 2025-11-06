# Country Exchange API

The **Country Exchange API** is a Spring Boot application that provides a RESTful interface for retrieving, storing, and managing country information and currency exchange rates. This API allows developers to query country data, refresh it from external APIs, and perform basic CRUD operations.

## Project Overview

This project was built to demonstrate the use of **Spring Boot**, **Spring Data JPA**, and **WebClient** for consuming external APIs and storing structured data in a MySQL database. It includes features such as:

- Fetching country details including name, capital, region, population, and flag.
- Storing currency codes and retrieving exchange rates from an external API.
- Calculating an estimated GDP for each country based on population and exchange rate.
- Refreshing data from external sources on demand.
- Full CRUD support for country data.
- Filtering and sorting countries by region, currency, or GDP.

## Features

- **Fetch External Data**: Pulls country information from [REST Countries API](https://restcountries.com/) and currency exchange rates from [Exchange Rate API](https://open.er-api.com/).  
- **CRUD Operations**: Add, retrieve, update, and delete country records.  
- **Filtering and Sorting**: Filter countries by region or currency and sort by GDP.  
- **Refresh Endpoint**: Automatically updates all country data and recalculates GDP.  

## API Endpoints

| Method | Endpoint              | Description                                |
|--------|----------------------|--------------------------------------------|
| GET    | `/countries`          | List all countries                          |
| GET    | `/countries/{name}`   | Get details of a specific country          |
| POST   | `/countries/refresh`  | Refresh country data from external APIs    |
| DELETE | `/countries/{name}`   | Delete a country by name                   |
| GET    | `/countries/status`   | View total number of countries and last refresh timestamp |

## Technology Stack

- Java 22
- Spring Boot 3.2
- Spring Data JPA
- MySQL 8
- WebClient (for external API calls)
- Maven

## Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/TsongaKing/country-exchange-api.git
cd country-exchange-api
