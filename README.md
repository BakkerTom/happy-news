# happy-news

[![Build Status](https://travis-ci.org/BakkerTom/happy-news.svg?branch=master)](https://travis-ci.org/BakkerTom/happy-news)
[![codebeat badge](https://codebeat.co/badges/503aab65-3852-4b7b-9c46-938a56e05b97)](https://codebeat.co/projects/github-com-bakkertom-happy-news-master)

<a href='https://play.google.com/store/apps/details?id=nl.fhict.happynews.android&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="150"/></a>

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

#### » `https://happynews-api.svendubbeld.nl/post?page={pagenumber}&size={size}`

Retrieves all posts in paginated format. Default page is 0 and default size is 20.

#### » `https://happynews-api.svendubbeld.nl/post/uuid/{uuid}`

Retrieves a post by it's `uuid`.

#### » `https://happynews-api.svendubbeld.nl/post/afterdate/{date}?ordered={ordered}`

Retrieves all posts after a Java `Date`. ordered is not required, and defaults to `true`.

#### Example JSON
```json
{

    "content": [
        {
            "uuid": "5907217ea7b11b00016e5a44",
            "source": "the-next-web",
            "sourceName": null,
            "author": "Arno Nijhof",
            "title": "These companies are growing extremely quickly and will be the unicorns of tomorrow",
            "contentText": "For the fourth year in a row, Adyen and TNW are celebrating European startups with the Tech5. In this competition, we measure companies on growth in revenue over the last three years. ...",
            "url": "https://thenextweb.com/insider/2017/05/01/these-companies-are-growing-extremely-quickly-and-will-be-the-unicorns-of-tomorrow/",
            "imageUrls": [
                "https://cdn2.tnwcdn.com/wp-content/blogs.dir/1/files/2017/05/image3.jpg"
            ],
            "videoUrl": null,
            "publishedAt": 1493643989000,
            "indexedAt": 1493639401003,
            "positivityScore": 0.0,
            "expirationDate": null,
            "type": "article",
            "tags": [ ]
        }
    ],
    "last": false,
    "totalElements": 1,
    "totalPages": 1,
    "numberOfElements": 1,
    "sort": null,
    "first": true,
    "size": 20,
    "number": 0

}
```


## Team
[![Sander van Andel](https://avatars1.githubusercontent.com/u/25583174?v=3&s=250)](https://github.com/SanderVanAndel) | [![Tom Bakker](https://avatars0.githubusercontent.com/u/1022998?v=3&s=250)](https://github.com/BakkerTom) | [![Tobi van Bronswijk](https://avatars2.githubusercontent.com/u/15573392?v=3&s=250)](https://github.com/TvanBronswijk) | [![Sven Dubbeld](https://avatars1.githubusercontent.com/u/4523069?v=3&s=250)](https://github.com/SvenDub) | [![Daan Tuller](https://avatars3.githubusercontent.com/u/15889244?v=3&s=250)](https://github.com/DaanTul)
---|---|---|---|---
[Sander van Andel](https://github.com/SanderVanAndel) | [Tom Bakker](https://github.com/BakkerTom) | [Tobi van Bronswijk](https://github.com/TvanBronswijk) | [Sven Dubbeld](https://github.com/SvenDub) | [Daan Tuller](https://github.com/DaanTul)


## License

MIT License © Tom Bakker

## Version

* Version 1.0-SNAPSHOT
