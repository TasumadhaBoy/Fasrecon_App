import os
import io
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
os.environ['TF_ENABLE_ON_EDNN_OPTS'] = '0'
import numpy as np
import pickle
import nltk
from tensorflow import keras
from flask import Flask, request, jsonify
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import load_model
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

# Ensure NLTK data is downloaded
nltk.download('punkt')
nltk.download('stopwords')
nltk.download('wordnet')

# Load the model
model = load_model('./my_model2.h5')

# Load the tokenizer from the saved file
with open('./tokenizer.pkl', 'rb') as handle:
    tokenizer = pickle.load(handle)

# Label to index and index to label mapping
label_index = {'rainy': 1, 'hot': 2, 'snowy': 3, 'cold': 4, 'sunny': 5}
index_label = {1: 'rainy', 2: 'hot', 3: 'snowy', 4: 'cold', 5: 'sunny'}

# Initialize NLTK tools
lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words('english'))

# Text preprocessing function
def preprocess_text(text):
    tokens = word_tokenize(text.lower())  # Tokenize and convert to lowercase
    tokens = [word for word in tokens if word.isalnum()]  # Remove non-alphanumeric tokens
    tokens = [word for word in tokens if word not in stop_words]  # Remove stopwords
    tokens = [lemmatizer.lemmatize(word) for word in tokens]  # Lemmatize tokens
    return " ".join(tokens)

# Flask setup
app = Flask(__name__)

# Outfit recommendations based on weather
outfit_recommendations = {
    'rainy': {
        'outfits': ['Jackets', 'Sweaters', 'Sweatshirts','Jeans','Boots','Track Pants'],
        'colors': ['Black', 'Grey', 'Navy Blue']
    },
    'hot': {
        'outfits': ['Tshirts', 'Shorts', 'Sandals', 'Caps','Innerwear Vests','Jeans','Tunics','Flip Flops','Sports Shoes'],
        'colors': ['White', 'Light Blue', 'Pink']
    },
    'snowy': {
        'outfits': ['Jackets', 'Sweaters', 'Boots','Jeans','Socks'],
        'colors': ['White', 'Silver', 'Grey']
    },
    'cold': {
        'outfits': ['Sweaters', 'Sweatshirts', 'Jackets', 'Track Pants','Jeans','Socks'],
        'colors': ['Brown', 'Navy Blue', 'Black']
    },
    'sunny': {
        'outfits': ['Tshirts', 'Tops', 'Sunglasses', 'Casual Shoes', 'Dresses', 'Clutches', 'Handbags', 'Sandals','caps','Jeans','Tunics','Flip Flops','Flats','Trousers'],
        'colors': ['Yellow', 'Orange', 'White', 'Blue', 'Pink']
    }
}

# def get_outfit_recommendation(weather_label):
#     if weather_label in outfit_recommendations:
#         outfits = outfit_recommendations[weather_label]['outfits']
#         colors = outfit_recommendations[weather_label]['colors']
#         return {
#             'recommended_outfits': outfits,
#             'recommended_colors': colors
#         }
#     return {'recommended_outfits': [], 'recommended_colors': []}

import random

def get_outfit_recommendation(weather_label):
    if weather_label in outfit_recommendations:
        outfits = outfit_recommendations[weather_label]['outfits']
        colors = outfit_recommendations[weather_label]['colors']
        
        # Select two random items from each list (outfits and colors)
        recommended_outfits = random.sample(outfits, 2) if len(outfits) >= 2 else outfits
        recommended_colors = random.sample(colors, 2) if len(colors) >= 2 else colors
        
        return {
            'recommended_outfits': recommended_outfits,
            'recommended_colors': recommended_colors
        }
    
    return {'recommended_outfits': [], 'recommended_colors': []}


def predict_label(text):
    # Preprocess text and pad sequences
    processed_text = preprocess_text(text)
    seq = tokenizer.texts_to_sequences([processed_text])
    padded_seq = pad_sequences(seq, padding='post', maxlen=100)  # Adjust maxlen as needed

    # Get prediction from the model
    pred = model.predict(padded_seq)
    pred_index = np.argmax(pred, axis=1)[0]  # Get index of max predicted value
    predicted_label = index_label[pred_index + 1]  # Map index to label (adjust by 1)
    return predicted_label

@app.route("/predict", methods=["POST"])
def index():
    user_input = request.json.get("text")
    if not user_input:
        return jsonify({"error": "No text provided"}), 400

    # Predict label
    prediction = predict_label(user_input)

    # Get outfit recommendations
    recommendations = get_outfit_recommendation(prediction)

    # Generate recommendation text
    recommended_outfits = recommendations['recommended_outfits']
    recommended_colors = recommendations['recommended_colors']
    recommendation_text = (
        f"Based on the {prediction} weather, we recommend wearing: "
        f"{', '.join(recommended_outfits)} in colors like {', '.join(recommended_colors)}."
    )

    return jsonify({
        "predicted_label": prediction,
        "outfit_recommendations": recommendations,
        "recommendation_text": recommendation_text
    })

if __name__ == "__main__":
    app.run(debug=True)



