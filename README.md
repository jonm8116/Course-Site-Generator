# Course Site Generator Project
Course Project for Stony Brook's CSE 219 course (Introduction to Software Development).

> Objective: Setup a honeypot and provide a working demonstration of its features.

### Purpose:

- [x] A basic writeup (250-500 words) on the `README.md` desribing the overall approach, resources/tools used, findings
- [x] A specific, reproducible honeypot setup, ideally automated. There are several possibilities for this:
	- A Vagrantfile or Dockerfile which provisions the honeypot as a VM or container
	- A bash script that installs and configures the honeypot for a specific OS
	- Alternatively, **detailed** notes added to the `README.md` regarding the setup, requirements, features, etc.

### Required: Demonstration

- [x] A basic writeup of the attack (what offensive tools were used, what specifically was detected by the honeypot)
- [x] An example of the data captured by the honeypot (example: IDS logs including IP, request paths, alerts triggered)

# Setup

For the codepath assignment this week, I used a YouTube video tutorial in assisting me to help
set up the honeypot. It involved me downloading the software pentbox 1.8 which comes with a software package of
additional hacking tools. The networking tools that it comes with allows a user to quickly and easily
setup a honeypot on one of their forwarded ports.

# Reproducing setup

In order to reproduce the same software environment you will need to download the pentbox software package. 
You can use these following commands (in the kali linux terminal):

`wget http://downloads.sourceforge.net/project/pentbox18realised/pentbox-1.8.tar.gz`

Then you can expand the tar.gz file with
` tar -zxvf pentbox-1.8.tar.gz` 

This will create a pentbox folder on the same directory you are currently in. Then you enter the folder's directory and
use this command to start running the software package
`./pentbox.rb `

This will start running the pentbox then you can select the networking tools option to help start running a honeypot.
You can choose to either run an auto configuration or a manual configuration (which provides you with more customization).

# Running

The honeypot ran on port 80 of the ssh address of my vagrant instance. So when I tried to access the address
from the browser it would prompt me with an access denied message. While through the terminal I would
receive a message that would give a description of the intrusion. It would provide the intruders IP address and 
the GET request. This honeypot would monitor various user requests to the network whenever it was acccessed
and provide the root user with information about each request. This allows the user to gather information
about the user in each request and gauge the activity on the network.


# Video
Link:  https://www.youtube.com/watch?v=zOXd35GbDKg
