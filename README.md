# hum-temp-client
## How to use it?
### Step 1: Raspberry Pi DHT software installation (Python script)
- _[tutorials-raspberypi.com](https://tutorials-raspberrypi.com/raspberry-pi-measure-humidity-temperature-dht11-dht22/)_
```
sudo apt-get update
sudo apt-get install build-essential python-dev python-openssl git
git clone https://github.com/adafruit/Adafruit_Python_DHT.git && cd Adafruit_Python_DHT
sudo python setup.py install
cd examples
sudo ./AdafruitDHT.py 11 4
```
### Step 2: HumTempClient.jar and AdafruitDHT.py have to be placed in the same location
```
ls -l
AdafruitDHT.py HumTempClient-1.0-SNAPSHOT-jar-with-dependencies.jar
```
### Step 3: Startup with overwritten parameters
```
java -Dhtclient.sleep.millis=5000 -Dhtclient.password=######## -jar HumTempClient-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## How it works?
#### - [DEMO](https://htsystem.herokuapp.com/)