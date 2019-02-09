package wikibase;

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

import java.util.List;

public class WikiDataToWikiBase {

    public static void copyData(String entityId) throws Exception{
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");

        ApiConnection conWikidata = new ApiConnection(Util.getProperty("url_wikidataapi"));
        conWikidata.login(Util.getProperty("usn_wikidata"), Util.getProperty("pwd_wikidata"));


        //connecting and fetching data to insert from wikidata
        String siteIriToFetch = Util.getProperty("uri_wikidata");
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(conWikidata, siteIriToFetch);
        ItemDocument laboratoireHC = (ItemDocument) wbdf.getEntityDocument(entityId);
        System.out.println(laboratoireHC.getEntityId());
        System.out.println(laboratoireHC.getLabels());
        System.out.println(laboratoireHC.getStatementGroups());
        System.out.println(laboratoireHC.getDescriptions());

        //Example to search for an ID given the label
//        List<WbSearchEntitiesResult> entities = wbdf.searchEntities("Pierre Maret");
//        for (WbSearchEntitiesResult entity : entities) {
//            System.out.println(entity.getEntityId());
//        }

        //connecting and nserting data into our wikibase
        ApiConnection conWikiBase = new ApiConnection(Util.getProperty("url_wikibaseapi"));
        conWikiBase.login(Util.getProperty("usn_wikibase"), Util.getProperty("pwd_wikibase"));
        String siteIriToUpdate = Util.getProperty("uri_wikibase");
        WikibaseDataEditor wbde = new WikibaseDataEditor(conWikiBase, siteIriToUpdate);
        PropertyDocument propertyTravaille = (PropertyDocument) wbdf.getEntityDocument("P242");
        System.out.println(propertyTravaille.getLabels());

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statement1 = StatementBuilder
                .forSubjectAndProperty(noid, propertyTravaille.getPropertyId())
                .withValue(laboratoireHC.getItemId()).build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("Florence Garrelie", "en")
                .withLabel("Florence Garrelie", "fr")
                .withStatement(statement1).build();
        ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,
                "Statement created by our bot");
        System.out.println("Created Florence Garrelie");
        System.out.println("Created statement: Florence Garrelie travaille Laboratoire Huber Curien");
        System.out.println(itemDocument.getItemId().getId());
    }
}
