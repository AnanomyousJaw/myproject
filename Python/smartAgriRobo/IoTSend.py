import httplib, urllib
# ----------------------------------------------------------------------
sleep = 10 # how many seconds to sleep between posts to the channel
key ='R0QQ7KDNYV37IP2D'  # Thingspeak channel to update
# ----------------------------------------------------------------------

def send_IoTData(field1,field2):
    try:
        params = urllib.urlencode({'field1': field1, 'field2': field2, 'key':key })
        headers = {"Content-typZZe": "application/x-www-form-urlencoded","Accept": "text/plain"}
        conn = httplib.HTTPConnection("api.thingspeak.com:80")

        conn.request("POST", "/update", params, headers)
        response = conn.getresponse()
        print response.status, response.reason
        data = response.read()
        conn.close()
    except:
        return
 
