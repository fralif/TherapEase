import io
import tensorflow as tf
from tensorflow import keras
import numpy as np

from flask import Flask, request, jsonify

model = keras.models.load_model("TherapEaseModel.h5")

def preprocess_data(data):
    data_str = data.read().decode('utf-8')
    lines = data_str.split('\n')
    preprocessed_data = np.array([line.split(',') for line in lines], dtype=np.float32)
    
    return preprocessed_data

def predict(x):
    predictions = model.predict(x)
    predictions = tf.nn.softmax(predictions)
    pred0 = predictions[0]
    label0 = np.argmax(pred0)
    return label0

app = Flask(__name__)

@app.route("/", methods=["POST"])
def index():
    if "file" not in request.files:
        return jsonify({"error": "no file"})

    file = request.files["file"]
    if file.filename == "":
        return jsonify({"error": "no file"})

    try:
        data = preprocess_data(file)
        prediction = predict(data)
        data = {"prediction": int(prediction)}
        return jsonify(data)
    except Exception as e:
        return jsonify({"error": str(e)})

if __name__ == "__main__":
    app.run(debug=True)
