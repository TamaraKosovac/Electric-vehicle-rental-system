package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.erent.model.Post;
import org.unibl.etf.ip.erent.model.Promotion;
import org.unibl.etf.ip.erent.repository.PostRepository;
import org.unibl.etf.ip.erent.repository.PromotionRepository;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RSSController {

    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;

    @GetMapping(value = "/api/rss", produces = MediaType.APPLICATION_XML_VALUE)
    public String getRssFeed() {

        List<Post> posts = postRepository.findAll();
        List<Promotion> promotions = promotionRepository.findAll();

        DateTimeFormatter rssFormat = DateTimeFormatter.RFC_1123_DATE_TIME;

        StringBuilder rss = new StringBuilder();

        rss.append("""
                <?xml version="1.0" encoding="UTF-8"?>
                <rss version="2.0">
                  <channel>
                    <title>ETFBL IP - RSS Feed (Objave i Promocije)</title>
                    <link>http://localhost:8080/api/rss</link>
                    <description>Kombinovani RSS feed marketinških objava i promocija</description>
                """);

        for (Promotion p : promotions) {
            String pubDate = p.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .format(rssFormat);

            rss.append(String.format("""
                    <item>
                      <title><![CDATA[%s]]></title>
                      <description><![CDATA[%s]]></description>
                      <guid>promotion-%d</guid>
                      <pubDate>%s</pubDate>
                    </item>
                    """,
                    p.getTitle(),
                    p.getDescription(),
                    p.getId(),
                    pubDate
            ));
        }

        for (Post post : posts) {
            String pubDate = post.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .format(rssFormat);

            rss.append(String.format("""
                    <item>
                      <title><![CDATA[%s]]></title>
                      <description><![CDATA[%s]]></description>
                      <guid>post-%d</guid>
                      <pubDate>%s</pubDate>
                    </item>
                    """,
                    post.getTitle(),
                    post.getContent(),
                    post.getId(),
                    pubDate
            ));
        }

        rss.append("""
                  </channel>
                </rss>
                """);

        return rss.toString();
    }
}