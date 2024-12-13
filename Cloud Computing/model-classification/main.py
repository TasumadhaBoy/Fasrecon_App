import os
import uuid
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'  
import io
import tensorflow as tf
from sklearn.preprocessing import MultiLabelBinarizer
from tensorflow import keras
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify
from firebase_admin import credentials, firestore, initialize_app
from collections import OrderedDict
from dotenv import load_dotenv  

load_dotenv()

credential_path = os.getenv("GOOGLE_APPLICATION_CREDENTIALS")
if not credential_path:
    raise ValueError("GOOGLE_APPLICATION_CREDENTIALS is not set in .env file.")

app = Flask(__name__)

cred = credentials.Certificate(credential_path)
initialize_app(cred)
db = firestore.Client()

model = keras.models.load_model("./model/model.h5")
mlb = MultiLabelBinarizer()

labels = [
    "Backpacks", "Black", "Blue", "Briefs", "Brown", "Caps", "Casual Shoes",
    "Clutches", "Dresses", "Flats", "Flip Flops", "Formal Shoes", "Green", "Grey",
    "Handbags", "Heels", "Innerwear Vests", "Jackets", "Jeans", "Nail Polish", "Navy Blue",
    "Pink", "Purple", "Red", "Sandals", "Shirts", "Shorts", "Silver", "Socks", "Sports Shoes", 
    "Sweaters", "Sweatshirts", "Ties", "Tops", "Track Pants", "Trousers", "Tshirts", "Tunics", "White"
]

mlb.fit([labels])  

def preprocess_image(file):
    """Preprocess a single image file"""
    img = Image.open(io.BytesIO(file.read()))
    resized_img = img.resize((96, 64), Image.NEAREST)
    image_array = np.array(resized_img, dtype="float32") / 255.0
    return np.expand_dims(image_array, axis=0)  # Add batch dimension

def predict_labels(image_arrays):
    """Predict labels for multiple images"""
    # Predict probabilities
    predictions = model.predict(image_arrays)
    # Binarize predictions based on a threshold
    predictions_binarized = (predictions > 0.05).astype(int)
    # Decode binarized predictions
    results = mlb.inverse_transform(predictions_binarized)
    return results

@app.route("/predict", methods=["POST"])
def index():
    if 'images' not in request.files:
        return jsonify({"error": "No images provided"}), 400

    images = request.files.getlist('images')
    
    if not images or len(images) == 0:
        return jsonify({"error": "No images provided"}), 400

    try:
        image_arrays = []
        for file in images:
            image_array = preprocess_image(file)
            image_arrays.append(image_array[0])  
        
        image_arrays = np.array(image_arrays)
        
        results = predict_labels(image_arrays)
        
        for result in results:
            prediction_ref = db.collection('predictions').document(str(uuid.uuid4()))  # Buat referensi unik
            prediction_data = OrderedDict()
            prediction_data['id'] = prediction_ref.id
            prediction_data['result'] = list(result)
            prediction_data['createdAt'] = firestore.SERVER_TIMESTAMP

            prediction_ref.set(prediction_data)
        
        return jsonify(results)
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))  
    app.run(host='0.0.0.0', port=port)
