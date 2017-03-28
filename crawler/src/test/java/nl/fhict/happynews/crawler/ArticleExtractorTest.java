package nl.fhict.happynews.crawler;

import nl.fhict.happynews.crawler.extractor.ArticleExtractor;
import nl.fhict.happynews.shared.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
public class ArticleExtractorTest {

    @Autowired
    private ArticleExtractor extractor;

    @Test
    public void test() {
        Post post = new Post();

        post.setTitle("Building Awesome CMS");
        post.setContentText("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be.");
        post.setUrl("https://trackchanges.postlight.com/building-awesome-cms-f034344d8ed");

        String result = extractor.extract(post);

        assertThat(result, is("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be." +
                "GitHub has a set of powerful commands for narrowing search results. In seeking out modern content management tools, I used queries like this:" +
                "cms OR “content management” OR admin pushed:&gt;2016–01–01 stars:&gt;50" +
                "Sorting by stars, I worked my way backwards. I was able to quickly spot relevant CMS projects. I also started to notice some trends." +
                "Modern and popular content management systems are written in PHP, JavaScript, Python, and Ruby. There are also a few content management systems written in&nbsp;.NET (C#), but they are much less popular on GitHub." +
                "Headless content management systems are gaining popularity. Simply presenting the UI for users to edit content, and relying on the end user to create the user-facing site by ingesting the API. Directus and Cloud CMS are headless CMS options." +
                "Static content management systems don’t host pages for you. Instead they help generate your CMS, using static files. Netlify CMS, Respond CMS, and Lektor are a few of the options in the static CMS space." +
                "I knew the list of all popular content management systems would be huge. I didn’t want to put that data into Markdown directly, as it would be difficult to maintain and to augment with extra data (stars on GitHub, last push date, tags, etc)." +
                "Instead, I opted to store the data in TOML, a human-friendly configuration file language. You can view all of the data that powers Awesome CMS in the data folder. Here’s WordPress’ entry in that file:" +
                "[[cms]]" +
                "name = \"WordPress\"" +
                "description = \"WordPress is a free and open-source content management system (CMS) based on PHP and MySQL.\"" +
                "url = \"https://wordpress.org\"" +
                "github_repo = \"WordPress/WordPress\"" +
                "awesome_repo = \"miziomon/awesome-wordpress\"" +
                "language = \"php\"" +
                "I process this file using JavaScript in generateReadme.js. It handles processing the TOML, fetching information from GitHub, and generating the final README.md file using the Handlebars template. I’m scraping GitHub for star counts because GitHub’s API only allows for 60 requests an hour for authenticated users. We want to make it as easy as possible for anyone to contribute. Requiring users to generate a GitHub authentication token to generate the README wasn’t an option." +
                "By storing the data in TOML at generating the README.md using JavaScript, I’ve essentially created an incredibly light-weight, GitHub backed, static CMS to power Awesome CMS.I heard you like content management systems"));
    }

    @Test
    public void testNotFound() {
        Post post = new Post();

        post.setTitle("Building Awesome CMS");
        post.setContentText("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be.");
        post.setUrl("invalid.:/");

        String result = extractor.extract(post);

        assertThat(result, is("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be."));
    }

    @Test
    public void testNoUrl() {
        Post post = new Post();

        post.setTitle("Building Awesome CMS");
        post.setContentText("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be.");

        String result = extractor.extract(post);

        assertThat(result, is("Awesome CMS is…an awesome list of awesome CMSes. It’s on GitHub, so anyone can add to it via a pull request. Here are some notes on how and why it came to be."));
    }
}
