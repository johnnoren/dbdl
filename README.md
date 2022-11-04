# dbdl

A small java command line app for downloading videos from Dreambroker.com.

## Features

- Downloads all movies from a channel.
- Keeps track of which files have been downloaded so that they aren't downloaded again the next time the app is run.

## Getting started

Build a jar from the source code.

## Usage

Run using the command line and add the url for the channel in exactly the following format: 

```bash
java -jar dbdl.jar https://dreambroker.com/channel/rjvel23m
```

***Note:*** If the textfile with downloaded videos is deleted or moved away from the app the videos will be downloaded again. The videos themselves can be moved without problems.
