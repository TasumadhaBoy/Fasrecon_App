import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
os.environ['TF_ENABLE_ON_EDNN_OPTS'] = '0'
import io
import tensorflow
import sklearn
from sklearn.preprocessing import MultiLabelBinarizer
from tensorflow import keras
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify

model = keras.models.load_model("./model/model.h5")
mlb = MultiLabelBinarizer()

# REMOVED DUPLICATE "Casual Shoes"
label = [ "Backpacks","Black","Blue","Briefs","Brown","Caps","Casual Shoes", # <- Only one instance
"Clutches","Dresses","Flats","Flip Flops","Formal Shoes","Green","Grey",
"Handbags","Heels","Innerwear Vests","Jackets","Jeans","Nail Polish","Navy Blue",
"Pink","Purple","Red","Sandals","Shirts","Shorts","Silver","Socks","Sports Shoes","Sweaters",
"Sweatshirts","Ties","Tops","Track Pants","Trousers","Tshirts","Tunics","White" ]
labels = mlb.fit_transform([label]) # Fit on a list of lists


app = Flask(__name__)

def predict_label(gambar):
    pred1 = model.predict(gambar)
    pred_binarized = []

    for pred in pred1:
        vals = []
        for val in pred:
            if val > 0.05:
                vals.append(1)
            else:
                vals.append(0)
        pred_binarized.append(vals)
    pred_binarized = np.array(pred_binarized)
    hasil = mlb.inverse_transform(pred_binarized)
    return hasil

@app.route("/predict", methods=["GET", "POST"])
def index():
    file = request.files.get('file')
    if file is None or file.filename == "":
        return jsonify({"error": "tidak ada file"})
    dummy = []
    files = file.read()
    img = Image.open(io.BytesIO(files))
    resized_img = img.resize((96,64), Image.NEAREST)
    dummy.append(np.array(resized_img))  # Convert to NumPy array directly
    dummy = np.array(dummy, dtype="float32") / 255.0 # Ensure float32
    res = predict_label(dummy)
    return jsonify(res) # Return as JSON


if __name__ == "__main__":
    app.run(debug="True")