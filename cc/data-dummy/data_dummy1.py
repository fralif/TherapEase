import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import random
from datetime import datetime
import pytz

# Path ke file konfigurasi Firebase Admin SDK yang didapatkan dari Firebase Console
cred = credentials.Certificate('serviceAccountKey.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

# Membuat dokumen untuk user 1
doc_ref_user1 = db.collection('Users').document('user1')

prediksi_acak_user1 = random.choice(['calm', 'happy', 'sad', 'angry'])
level_acak_user1 = random.randint(1, 5)
time1 = datetime.now(pytz.timezone('Asia/Jakarta'))

data_user1 = {
    'Nama Lengkap': 'Alif Fitrianto Ramadhan',
    'Alamat': 'xxxxxxxx',
    'No Hp': '08xxxxx', 
    'Prediksi': f'{prediksi_acak_user1} lvl {level_acak_user1}',
    'Timestamp': time1
}

doc_ref_user1.set(data_user1)

# Membuat dokumen untuk user 2
doc_ref_user2 = db.collection('Users').document('user2')

prediksi_acak_user2 = random.choice(['calm', 'happy', 'sad', 'angry'])
level_acak_user2 = random.randint(1, 5)
time1 = datetime.now(pytz.timezone('Asia/Jakarta'))

data_user2 = {
    'Nama Lengkap': 'Jokowi',
    'Alamat': 'xxxxxxxx',
    'No Hp': '08xxxxx', 
    'Prediksi': f'{prediksi_acak_user2} lvl {level_acak_user2}',
    'Timestamp': time1
    
}

doc_ref_user2.set(data_user2)