# Product Price Tracker API Assignment

This project implements a REST API that allows users to set up price drop alerts for products. The API simulates price tracking by fetching data from a static JSON file (`prices.json`) and notifies users (via logs in this simplified version) when their desired price condition is met.

## Tech Stack

* Java
* Spring Boot
* Maven (for dependency management)
* SLF4j and Logback (for logging)
* Jackson (for JSON processing)

## API Endpoint

### `POST /alerts`

Allows users to set a new price alert for a product.

**Request Body (JSON):**

```json
{
  "productUrl": "YOUR_PRODUCT_URL",
  "desiredPrice": YOUR_DESIRED_PRICE
}
```

* `productUrl` (String): The URL of the product to track. This URL must exist as a key in the `prices.json` file.
* `desiredPrice` (Number): The price at which the user wants to be alerted.

**Response (HTTP Status 200 OK):**

```
Alert set for product: YOUR_PRODUCT_URL with desired price: YOUR_DESIRED_PRICE.
```

**Response (HTTP Status 404 Not Found):**

```
Product URL not found.
```

(Returned if the provided `productUrl` does not exist in the `prices.json` file.)

**Response (HTTP Status 500 Internal Server Error):**

```
Error: Could not read price data.
```

(Returned if there is an issue reading the `prices.json` file.)

## Example Usage (using `curl`):

To set an alert for a Redmi 12C with a desired price of 8500.00:

```bash
curl -X POST \
     -H "Content-Type: application/json" \
     -d '{
           "productUrl": "[https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B](https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B)",
           "desiredPrice": 8500.00
         }' \
     http://localhost:8080/alerts
```

**Expected Response:**

```
Alert set for product: [https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B](https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B) with desired price: 8500.00.
```

## Example Usage (using Postman):

1.  Open Postman.
2.  Create a new request.
3.  Select the **POST** method.
4.  Enter the URL: `http://localhost:8080/alerts`.
5.  Go to the **Headers** tab and add a header with:
    * Key: `Content-Type`
    * Value: `application/json`
6.  Go to the **Body** tab, select **raw**, and choose **JSON**.
7.  Enter the JSON request body (as shown in the `curl` example above).
8.  Click **Send**.

## Functionality

1.  When a user sends a POST request to `/alerts` with a product URL and desired price:
    * The API logs the received request.
    * It reads the product prices from the `prices.json` file located in the `src/main/resources` directory.
    * It checks if the provided `productUrl` exists as a key in the `prices.json` data.
    * If the `productUrl` is found, it compares the current price from `prices.json` with the `desiredPrice`.
    * If the current price is less than or equal to the `desiredPrice`, a message is logged to the console simulating a notification.
    * A success message confirming the alert has been set is returned to the user.
    * If the `productUrl` is not found, a 404 Not Found response is returned.
    * If there is an error reading the `prices.json` file, a 500 Internal Server Error response is returned.

## Running the Application

1.  **Prerequisites:** Ensure you have Java Development Kit (JDK) and Maven installed on your system.
2.  **Clone the Repository:**
    ```bash
    git clone YOUR_GITHUB_REPOSITORY_URL
    cd YOUR_REPOSITORY_DIRECTORY
    ```
3.  **Build the Application:**
    ```bash
    mvn clean install
    ```
4.  **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will start, and the API will be accessible at `http://localhost:8080`.

## `prices.json` File

The API relies on a static JSON file named `prices.json` located in the `src/main/resources` directory. This file should contain a JSON object where the keys are product URLs and the values are their corresponding prices (as numbers).

**Example `prices.json`:**

```json
{
  "[https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B](https://www.amazon.in/Redmi-12C-Sky-Blue-64GB/dp/B0BZ8T2Q8B)": 8499.00,
  "[https://www.flipkart.com/samsung-galaxy-f13-nightsky-green-64-gb/p/itm6e0c28a3130f5](https://www.flipkart.com/samsung-galaxy-f13-nightsky-green-64-gb/p/itm6e0c28a3130f5)": 9499.00,
  // ... more product URLs and prices
  "YOUR_PRODUCT_URL": CURRENT_PRICE
}
```

**Note:** This is a simplified implementation that simulates price tracking using a static file. A real-world application would involve fetching prices from external websites or APIs and potentially storing alerts in a database. Time-based price checks and more sophisticated notification mechanisms would also be implemented. This assignment focuses on the core API design and basic price comparison.
```