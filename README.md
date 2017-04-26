# sGoogle

A Scala library for the Google API.
An asynchronous non-blocking Scala Google API Wrapper,
implemented using play-json.

## Prerequisites

Scala 2.11.+ is supported.


Create the google project to get client_id and create_secret.
https://console.developers.google.com/iam-admin/projects

## Setup

TODO

## Usage

### Examples

TODO

## Reference

[Google OpenID Connect](https://developers.google.com/identity/protocols/OpenIDConnect)

[Google Calendar API](https://developers.google.com/google-apps/calendar/v3/reference/)

## curl get token test

```
curl -H "Content-Type: application/x-www-form-urlencoded" \
--request POST \
--data 'code=&client_id=&client_secret=&redirect_uri=&grant_type=authorization_code' \
"https://www.googleapis.com/oauth2/v4/token"
```