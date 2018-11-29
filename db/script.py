import json
import requests

with open('data.json') as json_data:
    d = json.load(json_data)

    i = 3
    for user in d:
        print(user)
        print(str(i))
        print('\n')
        headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
        response = requests.put('http://127.0.0.1:5984/database_name/' + str(i), data=json.dumps(user), headers=headers)
        print(response.text)
        i = i + 1
