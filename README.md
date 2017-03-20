# happy-news

[![Build Status](https://travis-ci.org/BakkerTom/happy-news.svg?branch=master)](https://travis-ci.org/BakkerTom/happy-news)

## Table of Contents
* [Description](#description)
* [API](#api)
    * [Example JSON](#example-json)
* [Team](#team)
* [License](#license)
* [Version](#version)

## Description

Happy News is a source of positive news. A school project for Fontys Hogeschool ICT in collaboration with [Citrus Software](http://citrus.nl/).

## API

#### » `https://happynews-api.svendubbeld.nl/post?ordered={ordered}`

Retrieves all posts. ordered is not required, and defaults to `true`.

#### » `https://happynews-api.svendubbeld.nl/post/uuid/{uuid}`

Retrieves a post by it's `uuid`.

#### » `https://happynews-api.svendubbeld.nl/post/date/{date}?ordered={ordered}`

Retrieves all posts after a Java `Date`. ordered is not required, and defaults to `true`.

#### Example JSON
```json
{
  "uuid":"58cfc5132ab79c00019adeca",
  "source":"business-insider",
  "author":"Julien Rath",
  "title":"IPG CEO on YouTube advertiser boycott: 'The best way to fix it is to hold them economically accountable'",
  "description":"Speaking at Advertising Week Europe IPG CEO Michael Roth said he would freeze spends if ads continued to appear next to extremist content on YouTube and Google",
  "url":"http://www.businessinsider.com/ipg-ceo-hold-google-economically-accountable-to-fix-extremist-ads-2017-3",
  "imageUrl":"http://static5.businessinsider.com/image/58cfc081d349f91e008b5506-1190-625/ipg-ceo-on-youtube-advertiser-boycott-the-best-way-to-fix-it-is-to-hold-them-economically-accountable.jpg",
  "publishedAt":1490011207000
}
```


## Team
[![Sander van Andel](https://avatars1.githubusercontent.com/u/25583174?v=3&s=250)](https://github.com/SanderVanAndel) | [![Tom Bakker](https://avatars0.githubusercontent.com/u/1022998?v=3&s=250)](https://github.com/BakkerTom) | [![Tobi van Bronswijk](https://avatars3.githubusercontent.com/u/20115031?v=3&s=250)](https://github.com/TvanBronswijk) | [![Sven Dubbeld](https://avatars1.githubusercontent.com/u/4523069?v=3&s=250)](https://github.com/SvenDub) | [![Daan Tuller](https://avatars3.githubusercontent.com/u/15889244?v=3&s=250)](https://github.com/DaanTul)
---|---|---|---|---
[Sander van Andel](https://github.com/SanderVanAndel) | [Tom Bakker](https://github.com/BakkerTom) | [Tobi van Bronswijk](https://github.com/TvanBronswijk) | [Sven Dubbeld](https://github.com/SvenDub) | [Daan Tuller](https://github.com/DaanTul)


## License

MIT License © Tom Bakker

## Version

* Version 1.0-SNAPSHOT