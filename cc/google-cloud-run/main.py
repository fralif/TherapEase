import io
import tensorflow as tf
from tensorflow import keras
import numpy as np

from flask import Flask, request, jsonify

anx_model = keras.models.load_model("TherapEaseModel.h5")
dep_model = keras.models.load_model("TherapEaseModel.h5")

def preprocessing(data):
    data_str = data.read().decode('utf-8')
    parsed_data = json.loads(data_str)

    age = parsed_data[0]["question1"]
    sex = parsed_data[0]["question2"]
    weight = parsed_data[0]["question3"]
    height = parsed_data[0]["question4"]
    physical = parsed_data[0]["question5"]
    marital = parsed_data[0]["question6"]
    education = parsed_data[0]["question7"]
    school = parsed_data[0]["question8"]
    life_satisfaction = parsed_data[0]["question9"]
    sad = parsed_data[0]["question10"]
    nervous = parsed_data[0]["question11"]
    restless = parsed_data[0]["question12"]
    hopeless = parsed_data[0]["question13"]
    effort = parsed_data[0]["question14"]
    worthless = parsed_data[0]["question15"]
    social = parsed_data[0]["question16"]
    work_lastweek = parsed_data[0]["question17"]
    work_whynot = parsed_data[0]["question18"]
    work_time = parsed_data[0]["question19"]
    support = parsed_data[0]["question20"]
    support12m = parsed_data[0]["question21"]
    famcare = parsed_data[0]["question22"]
    anx_ever = parsed_data[0]["question23"]
    dep_ever = parsed_data[0]["question24"]

    input = []

    if age > 17 and age < 30 :
        age = [1,0,0,0]
    elif age < 45 :
        age = [0,1,0,0]
    elif age < 60 :
        age = [0,0,1,0]
    elif age >= 60 :
        age = [0,0,0,1]
    ohe = age

    if sex == 0:
        sex = 1
    else:
        sex = 0
    input.append(sex)

    bmi = weight/((height/100)**2)
    if bmi < 18.5 :
        bmi = [1,0,0,0]
    elif bmi < 25 :
        bmi = [0,1,0,0]
    elif bmi < 30 :
        bmi = [0,0,1,0]
    elif age >= 30 :
        bmi = [0,0,0,1]
    ohe = ohe + bmi

    physical = (physical - 1)/4
    input.append(physical)

    if marital == 0:
        marital = 0
    else:
        marital = 1
    input.append(marital)

    education = tf.one_hot([1,2,3,education], depth=4)[3:, 1:]
    education = education.numpy().tolist()[0]
    ohe = ohe + education

    if school == 0:
        school = 1
    else:
        school = 0
    input.append(school)

    life_satisfaction = life_satisfaction/10
    input.append(life_satisfaction)

    k6 = sad + nervous + restless + hopeless + effort + worthless

    sad = (sad - 1)/4
    input.append(sad)

    nervous = (nervous - 1)/4
    input.append(nervous)

    restless = (restless - 1)/4
    input.append(restless)

    hopeless = (hopeless - 1)/4
    input.append(hopeless)

    effort = (effort - 1)/4
    input.append(effort)

    worthless = (worthless - 1)/4
    input.append(worthless)

    if k6 < 13:
        k6 = 0
    else:
        k6 = 1
    input.append(k6)

    social = (social - 1)/3
    input.append(social)

    if work_whynot == 0 :
        work_whynot = [1,0]
    elif work_whynot == 1 :
        work_whynot = [0,1]
    else:
        work_whynot = [0,0]
    ohe = ohe + work_whynot

    if work_time > 35:
        work_time35 = [1,0]
    else:
        work_time35 = [0,1]

    if work_time == None or work_time == 0:
        work_time = 0
        work_time35 = [0,0]
    elif work_time > 95:
        work_time = 1
    else:
        work_time = work_time/95
    input.append(work_time)

    ohe = ohe + work_time35

    support = (support - 1)/4
    input.append(support)

    support12m = (support12m - 1)/2
    input.append(support12m)

    if famcare == 0:
        famcare = 1
    else:
        famcare = 0
    input.append(famcare)

    if anx_ever == 0:
        anx_ever = 1
    else:
        anx_ever = 0
    input.append(anx_ever)

    if dep_ever == 0:
        dep_ever = 1
    else:
        dep_ever = 0
    input.append(dep_ever)

    input = input + ohe

    return input

def anxpredict(x):
    predictions = anx_model.predict(x)
    if predictions > 1:
        return 100
    elif predictions < 0:
        return 0
    else:
        return predictions*100
    
def deppredict(x):
    predictions = dep_model.predict(x)
    if predictions > 1:
        return 100
    elif predictions < 0:
        return 0
    else:
        return predictions*100

app = Flask(__name__)

@app.route("/", methods=["POST"])
def index():
    if "file" not in request.files:
        return jsonify({"error": "no file"})

    file = request.files["file"]
    if file.filename == "":
        return jsonify({"error": "no file"})

    try:
        data = preprocessing(file)
        anxprediction = anxpredict(data)
        depprediction = deppredict(data)
        data = [{"anxiety level": int(anxprediction)},{"depression level": int(depprediction)}]
        return jsonify(data)
    except Exception as e:
        return jsonify({"error": str(e)})

if __name__ == "__main__":
    app.run(debug=True)
