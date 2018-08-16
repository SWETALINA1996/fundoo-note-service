package com.bridgelabz.fundoonotes.note.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.note.exceptions.LinkNotFoundException;
import com.bridgelabz.fundoonotes.note.models.UrlMetaInfo;


@Component
public class LinkInfoProvider {

    public UrlMetaInfo getLinkInfo(String link) throws LinkNotFoundException {
        Document doc = null;
        String description = null;
        String imageUrl = null;
        try {
            doc = Jsoup.connect(link).get();
            description = doc.select("meta[name=description]").get(0).attr("content");
            imageUrl = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]").attr("src");
        } catch (IOException exception) {
            throw new LinkNotFoundException("Link cannot be accessed");
        }

        UrlMetaInfo urlInfo = new UrlMetaInfo();
        urlInfo.setLink(link);
        urlInfo.setImageURL(imageUrl);
        urlInfo.setDescription(description);

        return urlInfo;
    }
    
    public List<UrlMetaInfo> getDescription(String description) throws LinkNotFoundException  {

        String regex = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        String[] array = description.split("\\s+");

        List<UrlMetaInfo> urlMetaInfoList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].matches(regex)) {
            	urlMetaInfoList.add(getLinkInfo(array[i]));
            }
        }
        return urlMetaInfoList;
    }
}

