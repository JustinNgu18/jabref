package org.jabref.logic.importer.fetcher;

import java.util.List;

import org.jabref.logic.importer.CitationBasedParserFetcher;
import org.jabref.logic.importer.FetcherException;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;

import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to fetch for an articles citation relations on opencitations.net's API
 */
public class OpenCitationFetcher implements CitationBasedParserFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenCitationFetcher.class);
    private static final String BASIC_URL = "https://opencitations.net/index/api/v1/metadata/";

    /**
     * Possible search methods
     */
    public enum SearchType {
        CITING("reference"),
        CITEDBY("citation");

        public final String label;

        SearchType(String label) {
            this.label = label;
        }
    }

    public OpenCitationFetcher() {

    }

    @Override
    public String getURLForEntries(String... dois) {
        return dois != null ? BASIC_URL + String.join("__", dois) : BASIC_URL;
    }

    @Override
    public List<BibEntry> searchCitedBy(BibEntry entry) throws FetcherException {
        LOGGER.debug("Search: {}", "Articles citing " + entry.getField(StandardField.DOI).orElse("'No DOI found'"));
        return performSearch(entry, SearchType.CITEDBY);
    }

    @Override
    public List<BibEntry> searchCiting(BibEntry entry) throws FetcherException {
        LOGGER.debug("Search: {}", "Articles cited by " + entry.getField(StandardField.DOI).orElse("'No DOI found'"));
        return performSearch(entry, SearchType.CITING);
    }

    @Override
    public BibEntry createNewEntry(JSONObject jsonObject) {
        LOGGER.debug("Paper found: {}", jsonObject.getString("doi"));
        return new BibEntry()
                .withField(StandardField.TITLE, jsonObject.getString("title"))
                .withField(StandardField.AUTHOR, jsonObject.getString("author"))
                .withField(StandardField.YEAR, jsonObject.getString("year"))
                .withField(StandardField.PAGES, jsonObject.getString("page"))
                .withField(StandardField.VOLUME, jsonObject.getString("volume"))
                .withField(StandardField.ISSUE, jsonObject.getString("issue"))
                .withField(StandardField.DOI, jsonObject.getString("doi"));
    }

    @Override
    public String getName() {
        return "OpenCitationFetcher";
    }
}