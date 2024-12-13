### Cloud Architecture
<img width="1800px" alt="Cloud Architecture" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Cloud%20Computing/Cloud%20Architecture.jpg">

### Endpoint
- **Image Image Classification**
    - https://model-classification-660807228942.asia-southeast2.run.app/predict
- **Chatbot**
    - https://nlp-model-660807228942.asia-southeast2.run.app/predict

### Image Classification

- **URL**
  - `/predict`

- **Method**
  - `POST`

- **Request Body**
  - **form-data**
    - `images` : file (multiple files can be uploaded)

- **Example Response**
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

### Chatbot

- **URL**
  - `/predict`

- **Method**
  - `POST`

- **Request Body**
  - **raw**
    - ```json
      {
        "text" : "I need to go on rainy day"
      }

- **Example Response**
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
