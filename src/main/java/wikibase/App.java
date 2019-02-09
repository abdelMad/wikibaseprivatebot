package wikibase;

import org.openrdf.query.algebra.Str;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.util.WebResourceFetcherImpl;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import wikibase.utilities.Util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        try {
            String contents = new String(Files.readAllBytes(Paths.get(App.class.getClassLoader().getResource("imports/test.txt").getPath())));
            String[] idsList = contents.split(";");

            for (int i = 0; i < idsList.length; i++) {
                System.out.println("qid: " + idsList[i]);
                WikiDataToWikiBase.copyData(idsList[i]);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
