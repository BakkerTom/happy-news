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
    "uuid": "58cfcb4aa7b11b00013d4e0a",
    "source": "associated-press",
    "sourceName" : "Associated Press",
    "author": "James Vincent",
    "title": "Jeff Bezos looks a little too happy piloting a giant mechanical robot",
    "contentText": "A billionaire entrepreneur with a side-line building space rockets has been showing off piloting a 13-foot-tall robot.",
    "url": "https://www.cnbc.com/2017/03/20/jeff-bezos-looks-a-little-too-happy-piloting-a-giant-mechanical-robot.html",
    "imageUrls": ["https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2017/03/20/104351978-C7Vl-gtWkAAkj-Y.1910x1000.jpg"],
    "publishedAt": 1490012058000,
    "indexedAt" : 1490012058000,
    "positivityScore" : 7.6,
    "expirationDate" : null,
    "type" : "article",
    "tags" : []
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