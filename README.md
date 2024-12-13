![GitHub Cards Preview](https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/FasreconApp.png)
## <p align="center">Fasrecon : Your Daily Fashion Recommendation</p>
<p align="center" style="margin-bottom: 20px; line-height: 0.8;">
    <a href="https://docs.google.com/document/d/1r99cc1OClA_UJI9quSigrrBunU2F8d-2gmMkDZ_i9l8/edit?tab=t.0">Project Plan</a> &middot;
    <a href="https://docs.google.com/document/d/1d-GywBp9JO2UvWFDPD4uhWPAteJDk3USNnSMvX1oa6A/edit?tab=t.0">Project Brief</a> &middot;
    <a href="https://www.canva.com/design/DAGY_jujcDo/-B77-pRIVg3_7ZY8E4ee6g/edit">Presentation Slide</a> &middot;
    <a href="https://youtu.be/snJo7IxM5Lg">Link Video</a> 
</p>
<p align="center" style="margin-top: -10px;">©C242-PS216 Bangkit Capstone Team</p>


## Background
Choosing the right clothes can be a daily challenge. Factors like weather, occasion, and personal style all play a role in our decisions. Indoor or outdoor activities and sudden weather changes can make this process even more complicated. We want to make this easier by creating an android application that can provide smart clothing recommendations based on these variables. This application will integrate machine learning to evaluate user preferences, activity plans, and data from user input to provide personalized clothes recommendations. In order to get customized clothes suggestions, users may submit pictures of their clothing to the application, then the clothing classification feature will automatically detect the type and color of the clothes and the application will keep a list of the clothes the user owns. So, this can make it easier for users to manage the clothes they have. When there are clothes that are no longer used, users can delete them from the clothing collection list. After that, users can ask for clothing recommendations using a chatbot. The chatbot will provide recommendations in the form of text and also display images of clothing owned by the user according to the recommended type of clothing. This can help users who are confused about choosing the right clothes. This innovative application will not only help users look their best but also ensure they are dressed appropriately for any situation.



## Team Members

| ID         | Learning Path         | Name                       | University                    | LinkedIn |
|------------|-----------------------|----------------------------| ------------------------------|----------|
|M324B4KY1763| Machine Learning      | Hendri Agustono            | Universitas Tanjungpura       | <a href="https://www.linkedin.com/in/hendri-agustono/">Link</a> |
|M247B4KY2802| Machine Learning      | Muhammad Fadhil Syahputra  | Universitas Lambung Mangkurat | <a href="">Link</a> |
|M324B4KY0857| Machine Learning      | Bintang Budi Pangestu      | Universitas Tanjungpura       | <a href="https://www.linkedin.com/in/bintang-budi-pangestu-a80794206/">Link</a> |
|C529B4KY4512| Cloud Computing       | Wympi Saristo              | Politeknik Negeri Pontianak   | <a href="https://www.linkedin.com/in/wympi-saristo-b34161327/">Link</a> |
|C529B4KY0537| Cloud Computing       | Andyka Fajar Pratama       | Politeknik Negeri Pontianak   | <a href="https://www.linkedin.com/in/andyka-fajar-pratama-b65760335/">Link</a> |
|A529B4KY2792| Mobile Development    | Muhammad Dzauqi Ikhsan     | Politeknik Negeri Pontianak   | <a href="https://www.linkedin.com/in/dzauqi-ikhsan-821343328/">Link</a> |
|A324B4KY4350| Mobile Development    | Tirta Kusuma Yudha         | Universitas Tanjungpura       | <a href="https://www.linkedin.com/in/tirta-kusuma-yudha-430b2a304">Link</a> |



## User Interface
Light Theme <b>
<img width="1800px" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part1.png">
<img width="1800px" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part2.png">
<img width="1800px" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part3.png">


<b> <b> Dark Theme <b>
<img width="1800" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part1dark.png">
<img width="1800" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part2dark.png">
<img width="1800" alt="" src="https://github.com/TirtaKY25/Fasrecon_App/blob/main/Documentation/UserInterface/part3dark.png">



## Machine Learning Documentation
dataset link : <a href="https://www.kaggle.com/datasets/paramaggarwal/fashion-product-images-dataset">Dataset</a>

## Mobile Development Documentation
Figma link : <a href="https://www.figma.com/design/RfEQdT3phXEvcIwja2cyJh/Fasrecon-App-Design?node-id=0-1&node-type=canvas&t=kI2etcZKcZMnpLwp-0">Figma</a>

Application link : <a href="https://github.com/TirtaKY25/Fasrecon_App/releases/download/v1.0.0/app-debug.apk">Application</a>


## Cloud Computing Documentation
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
