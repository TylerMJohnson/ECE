# NurseMonitor

NurseMonitor was a proof of concept student project developed for Banner Health. This project consisted of serveral parts. This repo contains a portion of the main server and UI monitor.
Raspberry Pis were attached to various sensors at different stations. Privacy was to ensure the door was closed. The nurse was required to sanitize, use gloves and have proper identification. These actions were timestamped
to ensure requirements were completed in a swift manner. Raspberry Pis processed sensor input and communicated with this application via a server client model. This implementation can be found
at the src.org.ece.net.ServerTest.java and src.org.ece.net.ClientTest.java

Nurse names and their sensor data can be found in the entries folder. This looks like:
{"name":"test","time":61,"privacy":false,"sanitizer":false,"gloves":false,"identification":false,"privacytime":0,"sanitizertime":0,"glovestime":0,"identificationtime":0}

![Image of application in use](https://github.com/TylerMJohnson/NurseMonitor/blob/master/NurseMonitor/Example.png)
