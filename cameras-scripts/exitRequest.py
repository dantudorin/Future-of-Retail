import requests
import glob
import os
import cv2
import sys

API_ENDPOINT = "http://localhost:1001/gate/customer-exit"

path = ""
for i in range(len(sys.argv)):
    if i + 1 < len(sys.argv):
        path += str(sys.argv[i+1]) + ' '
path = path[:len(path) -1]
path += '*'

list_of_files = glob.glob(path)
latest_file = max(list_of_files, key=os.path.getctime)
img= cv2.imread(latest_file,0)
cv2.imshow("img", img )
cv2.waitKey(1000)

multiple_files = [
    ('photos', (latest_file, open(latest_file, 'rb').read(),'multipart',{'Expires': '0'}))
]
text_data = {
    'collectionName': "MegaImage",
    'customerId': 1
}
with open(latest_file, 'rb') as img:
    name_img= os.path.basename(latest_file)
    with requests.Session() as s:
        r = s.put(API_ENDPOINT, data=text_data,files=multiple_files)
        print(r.status_code)