package nl.fhict.happynews.crawler.model.quoteapi;

/**
 * Created by Tobi on 10-Apr-17.
 */
public class QuoteEnvelope {
    private Contents contents;

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public class Contents {
        private Quote[] quotes;
        private String copyright;

        public Quote[] getQuotes() {
            return quotes;
        }

        public void setQuotes(Quote[] quotes) {
            this.quotes = quotes;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }
    }

}
