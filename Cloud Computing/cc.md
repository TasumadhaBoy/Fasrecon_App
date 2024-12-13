# Fasrecon API

Backend API for the Fasrecon App.

## Installation

### Installing Using PIP

```bash
pip install -r requirements.txt
```

### How To Running

```bash
python3 main.py
```

## Cloud Architecture

![Cloud Architecture](https://github.com/TirtaKY25/Fasrecon_App/blob/main/Cloud%20Computing/Cloud%20Architecture.jpg)

## Endpoint

### Image Classification

- **URL:**
  ```
  https://model-classification-660807228942.asia-southeast2.run.app/predict
  ```

### Chatbot

- **URL:**
  ```
  https://nlp-model-660807228942.asia-southeast2.run.app/predict
  ```

## Endpoint Details

### Image Classification

- **URL:** `/predict`
- **Method:** `POST`
- **Request Body:**
  - **Form Data:**
    - `images`: File (multiple files can be uploaded)
  
- **Example Response:**

  ```json
  [
      [
          "Red",
          "Tshirts"
      ],
      [
          "Blue",
          "Tshirts"
      ]
  ]
  ```

### Chatbot

- **URL:** `/predict`
- **Method:** `POST`
- **Request Body:**
  - **Raw (JSON):**
  
    ```json
    {
      "text": "I need to go on rainy day"
    }
    ```
  
- **Example Response:**
  
  ```json
  {
    "outfit_recommendations": {
        "recommended_colors": [
            "Black",
            "Navy Blue"
        ],
        "recommended_outfits": [
            "Sweatshirts",
            "Jeans"
        ]
    },
    "predicted_label": "rainy",
    "recommendation_text": "Based on the rainy weather, we recommend wearing: Sweatshirts, Jeans in colors like Black, Navy Blue."
  }
  ```
