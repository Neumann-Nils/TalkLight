![Alt text](http://fs5.directupload.net/images/160612/fe9g7swu.png "#Talk Light")
## Synopsis

This application introduces an easy way to communicate witch each other with just only **light**. We have created an Android-App wich allows you to synchronize and control several Philip Hues over an Node js server, no matter where you or your partner is located. 

## Code Example

A small snippet to perform an action locally and remotely. This is the easiest use-case thinkable. The action are defined by us.


    public void performAction(TLActionIdentifier action) {
		if (!privateMode) {
			TLNetwork.getInstance().performAction(action);
		}
		
		TLProfile profile = profilesLocal.get(action);
		bridge.applyProfile(profile);
	}

	public void performActionRemote(TLActionIdentifier action) {
		if (!silentMode) {
			TLProfile profile = profilesRemote.get(action);
			bridge.applyProfile(profile);
		}
	}

## Motivation

In these times many people have fast changing life with fast changing residences. Many people today live in a long-distance relationship and have the feeling they are not included in the everyday life of their partner. Our goal is to achieve an intuituve and easy way of communication, which helps people to stay connected and creates the feeling of beeing near to your partner.

## Installation

You have to run the Android-Application on both of your smartphones and have them connected to a rightly arranged Philips Hue. 
Furthermore you have to run the Node js Server.

For more information see the documentation of the respective owners.

## API Reference

Philips hue: [http://www.developers.meethue.com/](http://www.developers.meethue.com/)


## Contributors

This project was created at the **Inno{Hacks}** 24 hours hackathon in Karlsruhe by

**Marc-Peter Eisinger**

**Felix Lange**

**Martin Essig**

**Nils Neumann**
