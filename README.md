# Shopping Cart API

This API allows managing products within a shopping cart. The products are categorized and have associated images. The main resources of this API are:

- **Product**: Products available for sale.
- **Category**: Categories to which the products belong.
- **Image**: Images associated with the products.

Users can perform the following actions:
- Create, update, and delete products.
- Query products by name, category, brand, and combinations of these criteria.

## Endpoints

### Create Product
- **Description**: Creates a new product in the store.
- **Method**: POST
- **URL**: /api/products/add

**Request Body**
```json
{
  "name": "Product Name",
  "brand": "Brand",
  "description": "Product Description",
  "price": 199.99,
  "inventory": 50,
  "category": {
    "name": "Electronics"
  }
}
```
**Response**
```json
{
  "message": "Product added successfully",
  "data": {
    "id": 1,
    "name": "Product Name",
    "brand": "Brand",
    "description": "Product Description",
    "price": 199.99,
    "inventory": 50,
    "category": {
      "id": 1,
      "name": "Electronics"
    },
    "images": []
  }
}
```
**Status Codes**
- 200 OK: Product created successfully.
- 500 Internal Server Error:  Error in creating the product.

### Update Product
- **Description**: Updates an existing product in the store.
- **Method**: PUT
- **URL**: /api/products/product/{productId}
- **URL Parameters**:
  - productId (Long): ID of the product to update.

**Request Body**
```json
{
  "name": "New Name",
  "brand": "New Brand",
  "description": "New Description",
  "price": 149.99,
  "inventory": 30,
  "category": {
    "name": "Home"
  }
}
```
**Response**
```json
{
  "message": "Product updated successfully",
  "data": {
    "id": 1,
    "name": "New Name",
    "brand": "New Brand",
    "description": "New Description",
    "price": 149.99,
    "inventory": 30,
    "category": {
      "id": 2,
      "name": "Home"
    },
    "images": []
  }
}
```
**Status Codes**
- **200 OK**: Product updated successfully.
- **404 Not** Found: Product not found.
- **500 Internal Server Error**: Error updating the product.

### Delete Product
- **Description**: Deletes an existing product from the store.
- **Method**: DELETE
- **URL**: /api/products/product/{productId}
- **URL Parameters**:
    - productId (Long): ID of the product to delete.

**Response**
```json
{
  "message": "Product deleted successfully",
  "data": 1
}
```
**Status Codes**
- **200 OK**: Product deleted successfully.
- **404 Not Found**: Product not found.

### Other Endpoints
- Get all products: GET /api/products/all
- Get product by ID: GET /api/products/product/{productId}
- Get product by name: GET /api/products/product/{productName}
- Get product by brand: GET /api/products/product/{productBrand}
- Get products by category and brand: GET /api/products/{category}/{brand}
- Count products by brand and name: GET /api/products/count/{brand}/{name}

### Add Image
- **Description**: Adds an image to a product.
- **Method**: POST
- **URL**: /api/products/product/{productId}/image
- **URL Parameters**:
    - productId (Long): ID of the product.

**Request Body**
```json
{
  "fileName": "image.jpg",
  "fileType": "image/jpeg",
  "url": "http://example.com/image.jpg",
  "image": "base64-string"
}
```
**Status Codes**
- **200 OK**: Image added successfully.
- **404 Not Found**: Product not found.
